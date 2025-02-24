/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ICitaDAO;
import DAO.IMedicoDAO;
import DAO.IPacienteDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import DTO.MedicoDTO;
import DTO.citaEmergenciaNuevaDTO;
import DTO.citaEmergenciaViejaDTO;
import Entidades.Cita;
import Entidades.CitaEmergencia;
import Entidades.EstadosCita;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.CitaEmergenciaMapper;
import Mappers.CitaMapper;
import Mappers.MedicoMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author sonic
 */
public class CitaBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    private final ICitaDAO citaDAO;
    private final IPacienteDAO pacienteDAO;
    private final IMedicoDAO medicoDAO;

    private final CitaMapper mapper = new CitaMapper();
    private final CitaEmergenciaMapper emergenciaMapper = new CitaEmergenciaMapper();

    private final MedicoMapper medicoMapper = new MedicoMapper();

    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
        this.pacienteDAO = new PacienteDAO(conexion);
        this.medicoDAO = new MedicoDAO(conexion);
    }

    public CitaViejaDTO agendarCita(CitaNuevaDTO citaNuevaDTO) throws NegocioException, SQLException {
        try {
            // Validación básica de fecha
            if (citaNuevaDTO.getFechaHora() == null || citaNuevaDTO.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("Fecha y hora inválidas");
            }

            Cita cita = mapper.toEntityNuevo(citaNuevaDTO);

            // Validar existencia del paciente
            if (pacienteDAO.consultarPacientePorID(cita.getPaciente().getIdPaciente()) == null) {
                throw new NegocioException("Paciente no registrado");
            }

            Cita citaNueva = citaDAO.agendarCita(cita);
            return mapper.toViejoDTO(citaNueva);

        } catch (PersistenciaClinicaException | SQLException e) {
            throw new NegocioException(e.getMessage()); // Mensaje claro del SP
        }
    }

    public void cancelarCita(int idCita) throws NegocioException {
        try {
            Cita cita = citaDAO.consultarCitaPorID(idCita);

            // Validar que no esté ya cancelada
            if (cita.getEstado().getDescripcion().equalsIgnoreCase("Cancelada")) {
                throw new NegocioException("La cita ya está cancelada");
            }

            // Validar tiempo mínimo de cancelación (24 horas antes)
            if (LocalDateTime.now().isAfter(cita.getFechaHora().minusHours(24))) {
                throw new NegocioException("No se puede cancelar con menos de 24 horas de anticipación");
            }

            // Ejecutar cancelación
            if (!citaDAO.cancelarCita(idCita)) {
                throw new NegocioException("Error al procesar la cancelación");
            }

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error técnico al cancelar: " + ex.getMessage());
        }
    }

    public List<CitaViejaDTO> consultarCitasMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            // 1. Validar datos básicos del médico
            if (medicoDTO == null || medicoDTO.getIdMedico() == 0) {
                throw new NegocioException("Datos del médico inválidos");
            }

            // 2. Convertir DTO a entidad
            Medico medico = medicoMapper.toEntity(medicoDTO); // Asumiendo que tienes un MédicoMapper

            // 3. Verificar existencia del médico
            Medico medicoExistente = medicoDAO.consultarMedicoPorID(medico.getIdMedico());
            if (medicoExistente == null) {
                throw new NegocioException("Médico no registrado en el sistema");
            }

            // 4. Obtener citas del médico
            List<Cita> citas = citaDAO.consultarCitasMedico(medicoExistente);

            // 5. Convertir a DTOs
            return citas.stream()
                    .map(mapper::toViejoDTO)
                    .collect(Collectors.toList());

        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar citas del médico", ex);
            throw new NegocioException("Error al obtener las citas: " + ex.getMessage());
        }
    }
    
    public CitaViejaDTO agendarCitaEmergencia(CitaNuevaDTO citanuevaDTO) throws NegocioException, PersistenciaClinicaException {
        try {
            // 1. Convertir el DTO a la entidad de dominio
            Cita cita = new Cita();
            cita.setPaciente(citanuevaDTO.getPaciente());
            cita.setMedico(citanuevaDTO.getMedico());
            cita.setEstado(citanuevaDTO.getEstado());
            cita.setFechaHora(citanuevaDTO.getFechaHora());
            cita.setTipoCita(citanuevaDTO.getTipoCita()); 
            // Ojo: si es emergencia, setTipoCita(Cita.TipoCita.EMERGENCIA)

            // Para emergencias, crea también el objeto CitaEmergencia
            // (aunque la SP genera folio y fechaExpiración, puedes setear datos iniciales si hace falta)
            if (citanuevaDTO.getTipoCita() == Cita.TipoCita.EMERGENCIA) {
                CitaEmergencia emergencia = new CitaEmergencia();
                // Si el DTO trae algo que quieras conservar
                // emergencia.setFolio(citaNuevaDTO.getEmergencia().getFolio()); // Normalmente se genera en SP
                cita.setEmergencia(emergencia);
            }

            // 2. Invocar el DAO para agendar la cita de emergencia
            //    Este método ya vimos que retorna la entidad 'Cita' con id, folio, expiración, etc.
            Cita citaAgendada = citaDAO.agendarCitaEmergencia(cita);

            // 3. Convertir la entidad resultante en un CitaViejaDTO
            
            CitaViejaDTO citaviejaDTO = mapper.toViejoDTO(cita);


            // 4. Retornar el DTO final
            return citaviejaDTO;

        } catch (SQLException e) {
            // En caso de error, encapsulamos la excepción en nuestra excepción de negocio
            throw new NegocioException("Error al agendar la cita de emergencia: " + e.getMessage(), e);
        }
    }
    
    
    public CitaViejaDTO consultarCitaPorsuID(int idCita) throws NegocioException, PersistenciaClinicaException {
        Cita citaencontrada = citaDAO.consultarCitaPorID(1);
        CitaViejaDTO citaviejaDTOnueva = mapper.toViejoDTO(citaencontrada);
        return citaviejaDTOnueva;
    }

//    // Método 1: Actualizar estado de una cita
//    public boolean insertarEstadoCita(int idCita, String descripcion) throws NegocioException {
//        try {
//            return citaDAO.insertarEstadoCita(idCita, descripcion);
//        } catch (PersistenciaClinicaException ex) {
//            throw new NegocioException("Error al actualizar estado: " + ex.getMessage());
//        }
//    }
//
//    
}

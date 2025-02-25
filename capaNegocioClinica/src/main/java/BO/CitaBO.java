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
import DTO.PacienteNuevoDTO;
import Entidades.Cita;
import Entidades.CitaEmergencia;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.CitaEmergenciaMapper;
import Mappers.CitaMapper;
import Mappers.MedicoMapper;
import Mappers.PacienteMapper;
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
    private final PacienteMapper pacienteMapper = new PacienteMapper();

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
            Medico medico = medicoMapper.toEntityDTO(medicoDTO); // Asumiendo que tienes un MédicoMapper

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

    public CitaViejaDTO agendarCitaEmergencia(CitaNuevaDTO citanuevaDTO) throws NegocioException, PersistenciaClinicaException, SQLException {
        try {
            Cita cita = mapper.toEntityNuevo(citanuevaDTO);

            if (pacienteDAO.consultarPacientePorID(cita.getPaciente().getIdPaciente()) == null) {
                throw new NegocioException("Paciente no registrado");

            }

            if (citanuevaDTO.getTipoCita() == Cita.TipoCita.EMERGENCIA) {
                CitaEmergencia emergencia = new CitaEmergencia();

                cita.setEmergencia(emergencia);
            }

            Cita citaAgendada = citaDAO.agendarCitaEmergencia(cita);

            return mapper.toViejoDTO(citaAgendada);

        } catch (SQLException e) {
            throw new NegocioException("Error al agendar la cita de emergencia: " + e.getMessage(), e);
        }
    }

    //public CitaViejaDTO consultarCitaPorsuID(int idCita) throws NegocioException, PersistenciaClinicaException {
        
    //}

    public List<CitaViejaDTO> consultarCitasProximasPaciente(PacienteNuevoDTO pacientenuevoDTO) throws NegocioException {
        try {
            // Convertir DTO a entidad
            Paciente paciente = pacienteMapper.toEntityNuevo(pacientenuevoDTO);

            // Llamar al DAO
            List<Cita> citas = citaDAO.consultarCitasProximasPorPaciente(paciente);

            // Convertir a DTO
            return citas.stream()
                    .map(mapper::toViejoDTO)
                    .collect(Collectors.toList());

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al obtener citas próximas: " + ex.getMessage());
        }
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

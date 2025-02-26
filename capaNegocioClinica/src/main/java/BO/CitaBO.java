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
import DTO.PacienteViejoDTO;
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

    public boolean cancelarCita(int idCita) throws NegocioException {
        try {
        // 1. Validación básica del ID
        if (idCita <= 0) {
            throw new NegocioException("ID de cita inválido");
        }

        // 2. Obtener cita actual para verificar estado
        Cita cita = citaDAO.consultarCitaPorID(idCita);
        
        // 3. Validar que no esté ya cancelada
        if ("Cancelada".equalsIgnoreCase(cita.getEstado().getDescripcion())) {
            throw new NegocioException("La cita ya está cancelada");
        }

        // 4. Validar fecha de la cita (ejemplo: no permitir cancelar citas pasadas)
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new NegocioException("No se pueden cancelar citas pasadas");
        }

        // 5. Ejecutar cancelación
        boolean resultado = citaDAO.cancelarCita(idCita);
        
        if (!resultado) {
            throw new NegocioException("No se pudo completar la cancelación");
        }

    } catch (PersistenciaClinicaException ex) {
        throw new NegocioException("Error en persistencia: " + ex.getMessage());
    }
        return false;
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

    public Cita consultarCitaPorID(int idCita) throws NegocioException {
        try {
            Cita cita = citaDAO.consultarCitaPorID(idCita);

            return cita;
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

    public CitaViejaDTO consultarCitaPorsuID(int idCita) throws NegocioException, PersistenciaClinicaException {
        try {
        // 1. Validación básica del ID
        if (idCita <= 0) {
            throw new NegocioException("El ID de cita debe ser un número positivo");
        }

        // 2. Obtener cita desde el DAO
        Cita cita = citaDAO.consultarCitaPorID(idCita);

        // 3. Mapear a DTO con datos extendidos
        CitaViejaDTO dto = mapper.toViejoDTO(cita);
        
        return dto;

    } catch (PersistenciaClinicaException ex) {
        logger.log(Level.SEVERE, "Fallo al consultar cita ID: " + idCita, ex);
        throw new NegocioException(ex.getMessage());
    }    
    }

    
    public List<CitaViejaDTO> consultarCitasProximasPaciente(PacienteViejoDTO paciente) throws NegocioException {
        try {
            // Convertir DTO a entidad
            Paciente entidadPaciente = pacienteMapper.toEntityViejo(paciente);

            // Llamar al DAO
            List<Cita> citas = citaDAO.consultarCitasProximasPorPaciente(entidadPaciente);

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

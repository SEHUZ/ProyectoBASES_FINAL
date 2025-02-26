/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

// IMPORTS 
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
/**
 * Clase que maneja la lógica de negocio de las citas médicas.
 */
public class CitaBO {

    // Logger para registrar errores y eventos
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    // Instancias de los DAO y mappers necesarios para la lógica de negocio
    private final ICitaDAO citaDAO;
    private final IPacienteDAO pacienteDAO;
    private final IMedicoDAO medicoDAO;

    private final CitaMapper mapper = new CitaMapper();
    private final CitaEmergenciaMapper emergenciaMapper = new CitaEmergenciaMapper();
    private final PacienteMapper pacienteMapper = new PacienteMapper();

    private final MedicoMapper medicoMapper = new MedicoMapper();

    // Constructor que inicializa los DAOs necesarios para interactuar con la base de datos
    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
        this.pacienteDAO = new PacienteDAO(conexion);
        this.medicoDAO = new MedicoDAO(conexion);
    }

    // Método para agendar una nueva cita
    public CitaViejaDTO agendarCita(CitaNuevaDTO citaNuevaDTO) throws NegocioException, SQLException {
        try {
            // Validación básica de la fecha y hora de la cita
            if (citaNuevaDTO.getFechaHora() == null || citaNuevaDTO.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("Fecha y hora inválidas");
            }

            // Mapeo de DTO a entidad Cita
            Cita cita = mapper.toEntityNuevo(citaNuevaDTO);

            // Validación de la existencia del paciente
            if (pacienteDAO.consultarPacientePorID(cita.getPaciente().getIdPaciente()) == null) {
                throw new NegocioException("Paciente no registrado");
            }

            // Agendar la cita en la base de datos
            Cita citaNueva = citaDAO.agendarCita(cita);
            return mapper.toViejoDTO(citaNueva); // Convertir la cita a DTO y retornarlo

        } catch (PersistenciaClinicaException | SQLException e) {
            throw new NegocioException(e.getMessage()); // Excepción en caso de error de persistencia o SQL
        }
    }

    // Método para cancelar una cita
    public boolean cancelarCita(int idCita) throws NegocioException {
        try {
            // Validación del ID de la cita
            if (idCita <= 0) {
                throw new NegocioException("ID de cita inválido");
            }

            // Obtener la cita para verificar su estado
            Cita cita = citaDAO.consultarCitaPorID(idCita);

            // Validación de que la cita no esté ya cancelada
            if ("Cancelada".equalsIgnoreCase(cita.getEstado().getDescripcion())) {
                throw new NegocioException("La cita ya está cancelada");
            }

            // Validación de que no se pueda cancelar una cita pasada
            if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("No se pueden cancelar citas pasadas");
            }

            // Intentar cancelar la cita
            boolean resultado = citaDAO.cancelarCita(idCita);
            
            if (!resultado) {
                throw new NegocioException("No se pudo completar la cancelación");
            }

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error en persistencia: " + ex.getMessage()); // Manejo de excepciones
        }
        return false;
    }

    // Método para consultar las citas de un médico
    public List<CitaViejaDTO> consultarCitasMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            // Validación básica de los datos del médico
            if (medicoDTO == null || medicoDTO.getIdMedico() == 0) {
                throw new NegocioException("Datos del médico inválidos");
            }

            // Convertir DTO a entidad Medico
            Medico medico = medicoMapper.toEntityDTO(medicoDTO);

            // Verificar existencia del médico
            Medico medicoExistente = medicoDAO.consultarMedicoPorID(medico.getIdMedico());
            if (medicoExistente == null) {
                throw new NegocioException("Médico no registrado en el sistema");
            }

            // Obtener citas del médico
            List<Cita> citas = citaDAO.consultarCitasMedico(medicoExistente);

            // Convertir las citas a DTOs y devolver la lista
            return citas.stream()
                    .map(mapper::toViejoDTO)
                    .collect(Collectors.toList());

        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar citas del médico", ex);
            throw new NegocioException("Error al obtener las citas: " + ex.getMessage());
        }
    }

    // Método para consultar una cita por su ID
    public Cita consultarCitaPorID(int idCita) throws NegocioException {
        try {
            // Consultar la cita en el DAO
            Cita cita = citaDAO.consultarCitaPorID(idCita);

            return cita;
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar citas del médico", ex);
            throw new NegocioException("Error al obtener las citas: " + ex.getMessage());
        }
    }

    // Método para agendar una cita de emergencia
    public CitaViejaDTO agendarCitaEmergencia(CitaNuevaDTO citanuevaDTO) throws NegocioException, PersistenciaClinicaException, SQLException {
        try {
            // Mapeo de DTO a entidad Cita
            Cita cita = mapper.toEntityNuevo(citanuevaDTO);

            // Validación de existencia del paciente
            if (pacienteDAO.consultarPacientePorID(cita.getPaciente().getIdPaciente()) == null) {
                throw new NegocioException("Paciente no registrado");
            }

            // Si la cita es de emergencia, se marca como emergencia
            if (citanuevaDTO.getTipoCita() == Cita.TipoCita.EMERGENCIA) {
                CitaEmergencia emergencia = new CitaEmergencia();
                cita.setEmergencia(emergencia); // Asignar la emergencia a la cita
            }

            // Agendar la cita de emergencia
            Cita citaAgendada = citaDAO.agendarCitaEmergencia(cita);

            return mapper.toViejoDTO(citaAgendada); // Retornar el DTO de la cita agendada

        } catch (SQLException e) {
            throw new NegocioException("Error al agendar la cita de emergencia: " + e.getMessage(), e); // Manejo de excepciones
        }
    }

    // Método para consultar una cita por su ID (versión extendida)
    public CitaViejaDTO consultarCitaPorsuID(int idCita) throws NegocioException, PersistenciaClinicaException {
        try {
            // Validación básica del ID
            if (idCita <= 0) {
                throw new NegocioException("El ID de cita debe ser un número positivo");
            }

            // Consultar la cita en el DAO
            Cita cita = citaDAO.consultarCitaPorID(idCita);

            // Convertir la cita a DTO con datos extendidos
            CitaViejaDTO dto = mapper.toViejoDTO(cita);
            
            return dto;

        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Fallo al consultar cita ID: " + idCita, ex);
            throw new NegocioException(ex.getMessage()); // Manejo de excepciones
        }
    }

    // Método para consultar las citas próximas de un paciente
    public List<CitaViejaDTO> consultarCitasProximasPaciente(PacienteViejoDTO paciente) throws NegocioException {
        try {
            // Convertir el DTO del paciente a entidad
            Paciente entidadPaciente = pacienteMapper.toEntityViejo(paciente);

            // Obtener las citas próximas del paciente
            List<Cita> citas = citaDAO.consultarCitasProximasPorPaciente(entidadPaciente);

            // Convertir las citas a DTOs y devolver la lista
            return citas.stream()
                    .map(mapper::toViejoDTO)
                    .collect(Collectors.toList());

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al obtener citas próximas: " + ex.getMessage()); // Manejo de excepciones
        }
    }

    // Método para cambiar el estado de una cita
    public boolean cambiarEstadoCita(String estado, Cita cita) throws NegocioException, PersistenciaClinicaException {
        try {
            // Consultar la cita en el DAO
            Cita citaActualizar = citaDAO.consultarCitaPorID(cita.getIdCita());

            // Actualizar el estado de la cita
            boolean i = citaDAO.actualizarEstadoCita(cita.getIdCita(), estado);

            // Retornar el resultado de la actualización
            if (i) {
                return i;
            }
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al obtener citas próximas: " + ex.getMessage());
        }
        return false;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ConsultaDAO;
import DAO.ICitaDAO;
import DAO.IConsultaDAO;
import DAO.IPacienteDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DTO.ConsultaNuevaDTO;
import DTO.ConsultaViejaDTO;
import DTO.MedicoNuevoDTO;
import Entidades.Cita;
import Entidades.CitaEmergencia;
import Entidades.Consulta;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import Mappers.ConsultaMapper;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 *
 * @author sonic
 */
public class ConsultaBO {

    // Definición de los objetos necesarios para la lógica de negocio.
    private final ICitaDAO citaDAO;  // DAO para acceder a los datos de las citas.
    private final IConsultaDAO consultaDAO;  // DAO para acceder a los datos de las consultas.
    private final ConsultaMapper consultaMapper = new ConsultaMapper();  // Mapeador para convertir entre entidades y DTOs de consulta.
    private final IPacienteDAO pacienteDAO;  // DAO para acceder a los datos de los pacientes.

    // Constructor que inicializa los DAOs necesarios.
    public ConsultaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);  // Inicializa el DAO de citas.
        this.consultaDAO = new ConsultaDAO(conexion);  // Inicializa el DAO de consultas.
        this.pacienteDAO = new PacienteDAO(conexion);  // Inicializa el DAO de pacientes.
    }

    // Método para insertar una nueva consulta en el sistema.
    public ConsultaViejaDTO insertarConsulta(ConsultaNuevaDTO consultanuevaDTO) throws SQLException, NegocioException, PersistenciaClinicaException {
        try {
            // Se desactiva la validación de fecha por ahora (comentado).
            // Validar que la consulta nueva no sea nula.
            if (consultanuevaDTO == null) {
                throw new NegocioException("Los datos de la cita son requeridos");  // Excepción si el DTO es nulo.
            }

            // Convertir el DTO de la nueva consulta a la entidad Consulta.
            Consulta consulta = consultaMapper.toEntityNuevo(consultanuevaDTO);

            // Verificar que la cita asociada a la consulta sea válida.
            if (consulta.getCita() == null || consulta.getCita().getIdCita() <= 0) {
                throw new NegocioException("La cita asociada a la consulta es inválida.");  // Excepción si la cita no es válida.
            }

            // Verificar que la cita realmente exista en el sistema.
            if (citaDAO.consultarCitaPorID(consulta.getCita().getIdCita()) == null) {
                throw new NegocioException("La cita asociada no existe");  // Excepción si no se encuentra la cita.
            }

            // Insertar la consulta en la base de datos.
            Consulta consultaInsertada = consultaDAO.insertarConsulta(consulta);

            // Convertir la consulta insertada a un DTO para devolver al cliente.
            ConsultaViejaDTO consultaViejaDTO = consultaMapper.toViejoDTO(consultaInsertada);

            return consultaViejaDTO;  // Devolver el DTO con la consulta insertada.

        } catch (SQLException e) {
            throw new NegocioException("Error al insertar consulta: " + e.getMessage(), e);  // Manejo de errores de base de datos.
        }
    }

    // Método para obtener el historial de consultas de un paciente.
    public List<ConsultaViejaDTO> historialConsultasPaciente(Paciente paciente) throws NegocioException {
        try {
            // Validar que el ID del paciente sea válido.
            if (paciente.getIdPaciente() == 0) {
                throw new NegocioException("Datos del paciente inválidos o incompletos");  // Excepción si los datos del paciente son inválidos.
            }

            // Verificar si el paciente está registrado en el sistema.
            if (paciente == null) {
                throw new NegocioException("Paciente no registrado en el sistema");  // Excepción si no se encuentra el paciente.
            }

            // Obtener el historial de consultas del paciente desde la base de datos.
            List<Consulta> consultas = consultaDAO.obtenerHistorialConsultasPaciente(paciente.getIdPaciente());

            // Convertir las consultas obtenidas a DTOs para devolverlas al cliente.
            return consultas.stream()
                    .map(consultaMapper::toViejoDTO)  // Convertir cada consulta a su correspondiente DTO.
                    .collect(Collectors.toList());  // Recopilar todas las consultas en una lista.

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error en capa de datos: " + ex.getMessage());  // Manejo de errores relacionados con la persistencia.
        }
    }
}

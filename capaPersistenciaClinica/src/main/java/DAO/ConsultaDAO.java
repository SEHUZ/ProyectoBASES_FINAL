/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.Consulta;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConsultaDAO que implementa la interfaz IConsultaDAO. Esta clase se
 * encarga de realizar operaciones de persistencia relacionadas con las
 * consultas en la base de datos.
 *
 * @author Daniel M
 */
public class ConsultaDAO implements IConsultaDAO {

    IConexionBD conexion;

    /**
     * Constructor de ConsultaDAO que inicializa la conexión a la base de datos.
     *
     * @param conexion objeto que implementa IConexionBD para la conexión a la
     * base de datos.
     */
    public ConsultaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Inserta una nueva consulta en la base de datos.
     *
     * @param consulta objeto Consulta a insertar.
     * @return Consulta con el ID generado.
     * @throws SQLException si ocurre un error de SQL.
     * @throws PersistenciaClinicaException si hay un error de persistencia.
     */
    @Override
    public Consulta insertarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException {
        String query = "INSERT INTO Consultas (diagnostico, estado, fechaHora, tratamiento, idCita) VALUES (?, ?, ?, ?, ?)";
        consulta.setEstado("Atendida");
        try (PreparedStatement pst = conexion.crearConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, consulta.getDiagnostico());
            pst.setString(2, consulta.getEstado());
            pst.setTimestamp(3, Timestamp.valueOf(consulta.getFechaHora()));
            pst.setString(4, consulta.getTratamiento());
            pst.setInt(5, consulta.getCita().getIdCita());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                // Obtener el ID generado automáticamente
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        consulta.setIdConsulta(rs.getInt(1));  // Asignamos el ID generado
                    }
                }
                return consulta;
            } else {
                throw new PersistenciaClinicaException("No se insertó la consulta");
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al insertar consulta", e);
        }
    }

    /**
     * Obtiene todas las consultas de un paciente específico.
     *
     * @param idPaciente ID del paciente cuyas consultas se desean obtener.
     * @return Lista de objetos Consulta.
     * @throws SQLException si ocurre un error de SQL.
     * @throws PersistenciaClinicaException si hay un error de persistencia.
     */
    @Override
    // Método para obtener todas las consultas de un paciente específico
    public List<Consulta> obtenerConsultasPorPaciente(int idPaciente) throws SQLException, PersistenciaClinicaException {
        List<Consulta> consultas = new ArrayList<>();
        String query = "SELECT * FROM Consultas WHERE idCita IN (SELECT idCita FROM Citas WHERE idPaciente = ?)";

        try (Connection conn = conexion.crearConexion(); // Usamos el método crearConexion
                 PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, idPaciente);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setEstado(rs.getString("estado"));
                    consulta.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
                    consulta.setTratamiento(rs.getString("tratamiento"));

                    // Obtener la cita asociada usando el método consultarCitaPorID
                    int idCita = rs.getInt("idCita");
                    CitaDAO citaDAO = new CitaDAO(conexion); // Usamos la conexión que ya existe
                    Cita cita = citaDAO.consultarCitaPorID(idCita);  // Aquí usamos el método para obtener la cita
                    consulta.setCita(cita);  // Asignamos la cita a la consulta

                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consultas para el paciente", e);
        }
        return consultas;
    }

    /**
     * Cancela una consulta actualizando su estado.
     *
     * @param idConsulta ID de la consulta a cancelar.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de SQL.
     * @throws PersistenciaClinicaException si hay un error de persistencia.
     */
    @Override
    // Método para cancelar una consulta (actualizando el estado)
    public boolean cancelarConsulta(int idConsulta) throws SQLException, PersistenciaClinicaException {
        String query = "UPDATE Consultas SET estado = 'Cancelada' WHERE idConsulta = ?";

        try (PreparedStatement pst = conexion.crearConexion().prepareStatement(query)) {
            pst.setInt(1, idConsulta);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al cancelar consulta", e);
        }
    }

    /**
     * Obtiene una consulta por su ID.
     *
     * @param idConsulta ID de la consulta a obtener.
     * @return objeto Consulta correspondiente o null si no se encuentra.
     * @throws SQLException si ocurre un error de SQL.
     * @throws PersistenciaClinicaException si hay un error de persistencia.
     */
    // Método para obtener una consulta por ID
    @Override
    public Consulta obtenerConsultaPorId(int idConsulta) throws SQLException, PersistenciaClinicaException {
        String sql = "SELECT * FROM Consultas WHERE idConsulta = ?";
        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));

                    // Verificamos si el valor de "fechaHora" es nulo
                    Timestamp ts = rs.getTimestamp("fechaHora");
                    if (ts != null) {
                        consulta.setFechaHora(ts.toLocalDateTime());
                    } else {
                        consulta.setFechaHora(null);
                    }

                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setTratamiento(rs.getString("tratamiento"));

                    // Asociamos la cita usando el método consultarCitaPorID de CitaDAO
                    CitaDAO citaDAO = new CitaDAO(conexion);  // Usamos la conexión que ya existe
                    int idCita = rs.getInt("idCita");
                    Cita cita = citaDAO.consultarCitaPorID(idCita);
                    consulta.setCita(cita);  // Asignamos la cita a la consulta

                    return consulta;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consulta por ID", e);
        }
        return null;  // Si no se encuentra la consulta
    }

    /**
     * Obtiene consultas de un paciente filtradas por especialidad y fechas.
     *
     * @param idPaciente ID del paciente cuyas consultas se desean obtener.
     * @param especialidad Especialidad del médico.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de objetos Consulta.
     * @throws SQLException si ocurre un error de SQL.
     * @throws PersistenciaClinicaException si hay un error de persistencia.
     */
    @Override
    public List<Consulta> obtenerConsultasPorEspecialidadYFechas(int idPaciente, String especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException, PersistenciaClinicaException {
        List<Consulta> consultas = new ArrayList<>();

        // Query para obtener consultas de un paciente filtradas por especialidad y fechas
        String query = "SELECT c.* "
                + "FROM Consultas c "
                + "JOIN Citas ci ON c.idCita = ci.idCita "
                + "JOIN Medicos m ON ci.idMedico = m.idMedico "
                + "WHERE ci.idPaciente = ? "
                + "AND m.especialidad = ? "
                + "AND c.fechaHora BETWEEN ? AND ?";

        try (Connection conn = conexion.crearConexion(); // Usamos la conexión
                 PreparedStatement pst = conn.prepareStatement(query)) {

            // Establecemos los parámetros para la consulta
            pst.setInt(1, idPaciente); // ID del paciente
            pst.setString(2, especialidad); // Especialidad del médico
            pst.setTimestamp(3, Timestamp.valueOf(fechaInicio)); // Fecha de inicio del rango
            pst.setTimestamp(4, Timestamp.valueOf(fechaFin)); // Fecha de fin del rango

            // Ejecutamos la consulta
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Crear objeto Consulta y asignar valores
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setEstado(rs.getString("estado"));
                    consulta.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
                    consulta.setTratamiento(rs.getString("tratamiento"));

                    // Obtener la cita asociada usando el ID de la cita
                    int idCita = rs.getInt("idCita");
                    CitaDAO citaDAO = new CitaDAO(conexion);
                    Cita cita = citaDAO.consultarCitaPorID(idCita);
                    consulta.setCita(cita);

                    // Agregar consulta a la lista
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consultas por especialidad y fechas", e);
        }

        return consultas;
    }

    /**
     * Verifica si existe una consulta asociada a una cita dada.
     *
     * @param idCita El ID de la cita que se va a verificar.
     * @return true si existe al menos una consulta para la cita; false en caso
     * contrario.
     * @throws PersistenciaClinicaException Si ocurre un error al acceder a la
     * base de datos.
     */
    @Override
    public boolean existeConsultaParaCita(int idCita) throws PersistenciaClinicaException {
        String query = "SELECT COUNT(*) FROM Consultas WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement pst = conn.prepareStatement(query)) {

            // Establecer el parámetro de la consulta
            pst.setInt(1, idCita);

            // Ejecutar la consulta
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1); // Obtener el conteo de registros
                    return count > 0; // Si hay al menos un registro, existe una consulta
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al verificar existencia de consulta para la cita: " + e.getMessage());
        }

        return false; // Por defecto, no existe consulta
    }

    /**
     * Obtiene el historial de consultas para un paciente específico.
     *
     * @param idPaciente El ID del paciente cuyo historial se va a recuperar.
     * @return Una lista de objetos Consulta que representan el historial de
     * consultas del paciente.
     * @throws PersistenciaClinicaException Si ocurre un error al acceder a la
     * base de datos o si el paciente no es encontrado.
     */
    @Override
    public List<Consulta> obtenerHistorialConsultasPaciente(int idPaciente) throws PersistenciaClinicaException {
        List<Consulta> consultas = new ArrayList<>();
        String procedimientoHistorial = "{CALL ObtenerHistorialConsultasPorPaciente(?)}";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimientoHistorial)) {

            // Establecer parámetro
            cs.setInt(1, idPaciente);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Mapeo de la consulta
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setEstado(rs.getString("estado"));
                    consulta.setFechaHora(rs.getTimestamp("fechaHoraConsulta").toLocalDateTime());
                    consulta.setTratamiento(rs.getString("tratamiento"));

                    Cita cita = new Cita();
                    cita.setFechaHora(rs.getTimestamp("fechaHoraCita").toLocalDateTime());

                    Medico medico = new Medico();
                    medico.setNombres(rs.getString("nombreMedico"));
                    medico.setApellidoPaterno(rs.getString("apellidoMedico"));
                    medico.setEspecialidad(rs.getString("especialidad"));

                    cita.setMedico(medico);
                    consulta.setCita(cita);

                    consultas.add(consulta);
                }
            }

        } catch (SQLException e) {
            // Manejo específico para paciente no encontrado
            if (e.getSQLState().equals("45000")) {
                throw new PersistenciaClinicaException("Error: " + e.getMessage(), e);
            }
            throw new PersistenciaClinicaException("Error al obtener historial de consultas", e);
        }

        return consultas;
    }
}

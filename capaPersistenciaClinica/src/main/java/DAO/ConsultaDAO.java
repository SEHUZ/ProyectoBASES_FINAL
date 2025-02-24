/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.Consulta;
import Exception.PersistenciaClinicaException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ConsultaDAO implements IConsultaDAO {

   IConexionBD conexion;

    public ConsultaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    @Override
    public Consulta insertarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException {
    String query = "INSERT INTO Consultas (diagnostico, estado, fechaHora, tratamiento, idCita) VALUES (?, ?, ?, ?, ?)";

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

    @Override
    // Método para obtener todas las consultas de un paciente específico
    public List<Consulta> obtenerConsultasPorPaciente(int idPaciente) throws SQLException, PersistenciaClinicaException {
        List<Consulta> consultas = new ArrayList<>();
        String query = "SELECT * FROM Consultas WHERE idCita IN (SELECT idCita FROM Citas WHERE idPaciente = ?)";

        try (Connection conn = conexion.crearConexion();  // Usamos el método crearConexion
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
    
    @Override
    // Método para actualizar una consulta
    public boolean actualizarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException {
        String query = "UPDATE Consultas SET diagnostico = ?, estado = ?, fechaHora = ?, tratamiento = ? WHERE idConsulta = ?";

        try (PreparedStatement pst = conexion.crearConexion().prepareStatement(query)) {
            pst.setString(1, consulta.getDiagnostico());
            pst.setString(2, consulta.getEstado());
            pst.setTimestamp(3, Timestamp.valueOf(consulta.getFechaHora()));
            pst.setString(4, consulta.getTratamiento());
            pst.setInt(5, consulta.getIdConsulta());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al actualizar consulta", e);
        }
    }

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

    // Método para obtener una consulta por ID
    public Consulta obtenerConsultaPorId(int idConsulta) throws SQLException, PersistenciaClinicaException {
        String sql = "SELECT * FROM Consultas WHERE idConsulta = ?";
        try (Connection conn = conexion.crearConexion();  // Usamos el método crearConexion
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    consulta.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
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
    
    @Override
    public List<Consulta> obtenerConsultasPorEspecialidadYFechas(int idPaciente, String especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException, PersistenciaClinicaException {
    List<Consulta> consultas = new ArrayList<>();
    
    // Query para obtener consultas de un paciente filtradas por especialidad y fechas
    String query = "SELECT c.* " +
                   "FROM Consultas c " +
                   "JOIN Citas ci ON c.idCita = ci.idCita " +
                   "JOIN Medicos m ON ci.idMedico = m.idMedico " +
                   "WHERE ci.idPaciente = ? " +
                   "AND m.especialidad = ? " +
                   "AND c.fechaHora BETWEEN ? AND ?";

    try (Connection conn = conexion.crearConexion();  // Usamos la conexión
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
    @Override
     public boolean existeConsultaParaCita(int idCita) throws PersistenciaClinicaException {
        String query = "SELECT COUNT(*) FROM Consultas WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement pst = conn.prepareStatement(query)) {

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

    
    
    
}

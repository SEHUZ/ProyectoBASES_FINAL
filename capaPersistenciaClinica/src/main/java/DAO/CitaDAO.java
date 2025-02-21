/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.Paciente;
import Entidades.Medico;
import Entidades.EstadoCita;
import Exception.PersistenciaClinicaException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CitaDAO {

    private IConexionBD conexion;
    private Connection connection;  // Agregar esta línea para declarar la conexión

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;
        try {
            this.connection = conexion.crearConexion();  // Inicializar la conexión aquí
        } catch (PersistenciaClinicaException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar una nueva cita
    public boolean insertarCita(Cita cita) {
        String sql = "INSERT INTO Citas (idPaciente, idMedico, idEstado, fechaHora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cita.getPaciente().getIdPaciente());
            stmt.setInt(2, cita.getMedico().getIdMedico());
            stmt.setInt(3, cita.getEstado().getIdEstado());
            stmt.setObject(4, cita.getFechaHora());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener todas las citas
    public List<Cita> obtenerTodasLasCitas() throws PersistenciaClinicaException {
        List<Cita> listaCitas = new ArrayList<>();
        String sql = "SELECT c.idCita, c.fechaHora, " +
                     "p.idPaciente, p.nombres AS nombrePaciente, " +
                     "m.idMedico, m.nombres AS nombreMedico, " +
                     "e.idEstado, e.descripcion " +
                     "FROM Citas c " +
                     "JOIN Pacientes p ON c.idPaciente = p.idPaciente " +
                     "JOIN Medicos m ON c.idMedico = m.idMedico " +
                     "JOIN EstadoCita e ON c.idEstado = e.idEstado";

        try (PreparedStatement stmt = connection.prepareStatement(sql);  // Cambiado a 'conexion'
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            // Construcción completa de Paciente
            Paciente paciente = new Paciente();
            paciente.setIdPaciente(rs.getInt("idPaciente"));
            paciente.setNombres(rs.getString("nombrePaciente"));
            
            // Construcción completa de Médico
            Medico medico = new Medico();
            medico.setIdMedico(rs.getInt("idMedico"));
            medico.setNombres(rs.getString("nombreMedico"));
            
            EstadoCita estado = new EstadoCita(
                rs.getInt("idEstado"),
                rs.getString("descripcion")
            );

            // Usar LocalDateTime correctamente
            LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();
            
            Cita cita = new Cita(
                rs.getInt("idCita"),
                paciente,
                medico,
                estado,
                fechaHora 
            );
            
            listaCitas.add(cita);
        }
    } catch (SQLException e) {
        throw new PersistenciaClinicaException("Error al obtener citas: " + e.getMessage(), e);
    }
    return listaCitas;
    }

    // Método para actualizar el estado de una cita
    public boolean actualizarEstadoCita(int idCita, int nuevoEstado) {
        String sql = "UPDATE Citas SET idEstado = ? WHERE idCita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, idCita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar una cita
    public boolean eliminarCita(int idCita) {
        String sql = "DELETE FROM Citas WHERE idCita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}



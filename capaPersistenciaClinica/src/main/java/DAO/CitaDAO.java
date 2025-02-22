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
import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.Paciente;
import Entidades.Medico;
import Entidades.EstadosCita;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class CitaDAO implements ICitaDAO {

    IConexionBD conexion;

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;

    }

    // Método para insertar una cita general en la tabla Citas
    @Override
    public Cita insertarCita(Cita cita) throws PersistenciaClinicaException {
        String consultaSQL = "INSERT INTO Citas (idPaciente, idMedico, idEstado, fechaHora) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cita.getPaciente().getIdPaciente());
            stmt.setInt(2, cita.getMedico().getIdMedico());
            stmt.setInt(3, cita.getEstado().getIdEstado());
            stmt.setObject(4, cita.getFechaHora());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idCita = generatedKeys.getInt(1);
                        cita.setIdCita(idCita); // Asignamos el ID generado a la cita
                        return cita;
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al insertar la cita: " + e.getMessage(), e);
        }

        return null;  // Retorna null en caso de fallo
    }

    // Método para agendar una cita normal
    @Override
    public Cita agendarCitaNormal(Cita cita) throws PersistenciaClinicaException {
        Cita nuevaCita = insertarCita(cita);  // Inserta la cita en la tabla principal
        if (nuevaCita != null) {
            String consultaSQL = "INSERT INTO CitasNormales (idCita) VALUES (?)";
            try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(consultaSQL)) {
                stmt.setInt(1, nuevaCita.getIdCita());
                if (stmt.executeUpdate() > 0) {
                    return nuevaCita; // Retorna la cita ya creada con su ID
                }
            } catch (SQLException e) {
                throw new PersistenciaClinicaException("Error al agendar cita normal: " + e.getMessage(), e);
            }
        }
        return null;  // Retorna null si falla la inserción
    }

    // Método para agendar una cita de emergencia
    @Override
    public Cita agendarCitaEmergencia(Cita cita, String folio, LocalDate fechaExpiracion) throws PersistenciaClinicaException {
        Cita nuevaCita = insertarCita(cita);  // Inserta la cita en la tabla principal
        if (nuevaCita != null) {
            String consultaSQL = "INSERT INTO CitasEmergencias (idCita, folio, fechaExpiracion) VALUES (?, ?, ?)";
            try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(consultaSQL)) {
                stmt.setInt(1, nuevaCita.getIdCita());
                stmt.setString(2, folio);
                stmt.setObject(3, fechaExpiracion);
                if (stmt.executeUpdate() > 0) {
                    return nuevaCita; // Retorna la cita ya creada con su ID
                }
            } catch (SQLException e) {
                throw new PersistenciaClinicaException("Error al agendar cita de emergencia: " + e.getMessage(), e);
            }
        }
        return null;  // Retorna null si falla la inserción
    }

    // Método para obtener todas las citas
    @Override
    public List<Cita> obtenerTodasLasCitas() throws PersistenciaClinicaException {
        
        return null;
    }

    // Método para actualizar el estado de una cita
    @Override
    public boolean actualizarEstadoCita(int idCita, int nuevoEstado) throws PersistenciaClinicaException {
        String consultaSQL = "UPDATE Citas SET idEstado = ? WHERE idCita = ?";
        try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(consultaSQL)) {
            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, idCita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar una cita
    @Override
    public boolean eliminarCita(int idCita) throws PersistenciaClinicaException {
        String consultaSQL = "DELETE FROM Citas WHERE idCita = ?";
        try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(consultaSQL)) {
            stmt.setInt(1, idCita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException {
        List<Cita> citas = new ArrayList<>();
    String procedimiento = "{CALL ObtenerCitasPorMedico(?)}";

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(procedimiento)) {

        cs.setInt(1, medico.getIdMedico());

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                // Datos cita
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("idCita"));
                cita.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());

                // Datos paciente
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombres(rs.getString("nombrePaciente"));
                paciente.setApellidoPaterno(rs.getString("apellidoPaternoPaciente"));
                paciente.setApellidoMaterno(rs.getString("apellidoMaternoPaciente"));
                cita.setPaciente(paciente);

                // Datos medico
                Medico medicoCita = new Medico();
                medicoCita.setIdMedico(rs.getInt("idMedico"));
                medicoCita.setNombres(rs.getString("nombreMedico"));
                medicoCita.setApellidoPaterno(rs.getString("apellidoPaternoMedico"));
                medicoCita.setApellidoMaterno(rs.getString("apellidoMaternoMedico"));
                cita.setMedico(medicoCita);

                // Datos estadoCita
                EstadosCita estado = new EstadosCita();
                estado.setIdEstado(rs.getInt("idEstado"));
                estado.setDescripcion(rs.getString("estadoCita"));
                cita.setEstado(estado);

                citas.add(cita);
            }
        }

    } catch (SQLException ex) {
        throw new PersistenciaClinicaException("Error al obtener citas del médico: " + ex.getMessage());
    }

    return citas;
    }
    
    @Override
    public EstadosCita consultarEstadoCita(int idCita) throws PersistenciaClinicaException {
        String consultarEstadoSQL = "SELECT * FROM estadosCita WHERE idCita = ?";
    
    try (Connection con = conexion.crearConexion();
         PreparedStatement ps = con.prepareStatement(consultarEstadoSQL)) {
        
        ps.setInt(1, idCita);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int idEstado = rs.getInt("idEstado");
                String descripcion = rs.getString("descripcion");
                return new EstadosCita(idEstado, descripcion);
            } else {
                throw new PersistenciaClinicaException("No se encontró la cita con ID: " + idCita);
            }
        }
        
    } catch (SQLException ex) {
        throw new PersistenciaClinicaException("Error al consultar estado de la cita: " + ex.getMessage());
    }
    }
    
    

}

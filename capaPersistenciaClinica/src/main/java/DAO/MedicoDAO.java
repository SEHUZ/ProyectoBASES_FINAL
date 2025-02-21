/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.DireccionPaciente;
import Entidades.EstadoCita;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoDAO implements IMedicoDAO{
    private final Connection conexion;

    public MedicoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean DarBajaTemporal(Medico medico) throws PersistenciaClinicaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean DarAlta(Medico medico) throws PersistenciaClinicaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List <Cita> ConsultarCitas(Medico medico) throws PersistenciaClinicaException {
        List<Cita> citas = new ArrayList<>();
    
    String sql = "SELECT c.idCita, c.fechaHora, "
            + "p.idPaciente, p.nombres AS paciente_nombres, p.apellidoPaterno, p.apellidoMaterno, "
            + "p.fechaNacimiento, p.email, p.telefono, "
            + "u_p.user AS paciente_user, u_p.contrasenia AS paciente_contrasenia, "
            + "d_p.calle, d_p.numero, d_p.codigoPostal, "
            + "ec.idEstado, ec.descripcion AS estado_descripcion "
            + "FROM Citas c "
            + "INNER JOIN Pacientes p ON c.idPaciente = p.idPaciente "
            + "INNER JOIN Usuarios u_p ON p.idUsuario = u_p.idUsuario "
            + "INNER JOIN DireccionesPacientes d_p ON p.idPaciente = d_p.idPaciente "
            + "INNER JOIN EstadosCita ec ON c.idEstado = ec.idEstado "
            + "WHERE c.idMedico = ?";

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        
        stmt.setInt(1, medico.getIdMedico());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Construir Usuario del Paciente
            Usuario usuarioPaciente = new Usuario(
                rs.getString("paciente_user"),
                rs.getString("paciente_contrasenia"),
                "paciente" // El rol viene de la tabla pero simplificamos
            );

            // Construir Dirección
            DireccionPaciente direccion = new DireccionPaciente(
                rs.getString("calle"),
                rs.getString("numero"),
                rs.getString("codigoPostal")
            );

            // Construir Paciente
            Paciente paciente = new Paciente(
                rs.getInt("idPaciente"),
                direccion,
                usuarioPaciente,
                rs.getString("paciente_nombres"),
                rs.getString("apellidoPaterno"),
                rs.getString("apellidoMaterno"),
                rs.getDate("fechaNacimiento").toLocalDate(),
                rs.getString("email"),
                rs.getString("telefono")
            );

            // Construir Estado de Cita
            EstadoCita estado = new EstadoCita(
                rs.getInt("idEstado"),
                rs.getString("estado_descripcion")
            );

            // Construir Cita
            LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();
            Cita cita = new Cita(
                rs.getInt("idCita"),
                paciente,
                medico,  // médico recibido como parámetro
                estado,
                fechaHora
            );
            
            citas.add(cita);
        }
        
    } catch (SQLException ex) {
        throw new PersistenciaClinicaException("Error al consultar citas: " + ex.getMessage());
    }
    
    return citas;
    }

    @Override
    public boolean ConsultarHistorialPaciente(Paciente paciente) throws PersistenciaClinicaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

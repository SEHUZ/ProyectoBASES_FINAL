/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoDAO implements IMedicoDAO {

    IConexionBD conexion;

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());
    
    @Override
    public boolean ActualizarEstado(Medico medico) throws PersistenciaClinicaException {
        String updateEstadoSQL = "UPDATE Medicos SET activo = ? WHERE idMedico = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(updateEstadoSQL, Statement.RETURN_GENERATED_KEYS)) {
            boolean nuevoEstado = !medico.isActivo();

            ps.setBoolean(1, nuevoEstado);
            ps.setInt(2, medico.getIdMedico());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                medico.setActivo(nuevoEstado);
                return true;
            }
            return false;

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al actualizar estado del médico: " + ex.getMessage());
        }
    }

    @Override
    public Medico consultarMedicoPorEspecialidad(Medico medico) throws PersistenciaClinicaException {
        String consultaMedicoEspecialidadSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "WHERE m.especialidad = ? AND m.activo = TRUE "
                + "LIMIT 1";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaMedicoEspecialidadSQL)) {
            if (medico.getEspecialidad() == null || medico.getEspecialidad().isEmpty()) {
                throw new PersistenciaClinicaException("La especialidad no puede estar vacía");
            }

            ps.setString(1, medico.getEspecialidad());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medicoEncontrado = new Medico();
                    medicoEncontrado.setIdMedico(rs.getInt("idMedico"));
                    medicoEncontrado.setNombres(rs.getString("nombres"));
                    medicoEncontrado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medicoEncontrado.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    medicoEncontrado.setCedula(rs.getString("cedula"));
                    medicoEncontrado.setEspecialidad(rs.getString("especialidad"));
                    medicoEncontrado.setActivo(rs.getBoolean("activo"));

                    return medico;
                } else {
                    throw new PersistenciaClinicaException(
                            "No se encontraron médicos activos con la especialidad: " + medico.getEspecialidad()
                    );
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar médico por especialidad: " + ex.getMessage());
        }
    }

    @Override
    public Medico consultarMedicoPorID(Medico medico) throws PersistenciaClinicaException {
        String consultaMedicoIDSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "WHERE m.idMedico = ? AND m.activo = TRUE "
                + "LIMIT 1";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaMedicoIDSQL)) {
            if (medico.getIdMedico() <= 0) {
                throw new PersistenciaClinicaException("El ID del médico no es válido");
            }

            ps.setInt(1, medico.getIdMedico());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medicoEncontrado = new Medico();
                    medicoEncontrado.setIdMedico(rs.getInt("idMedico"));
                    medicoEncontrado.setNombres(rs.getString("nombres"));
                    medicoEncontrado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medicoEncontrado.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    medicoEncontrado.setCedula(rs.getString("cedula"));
                    medicoEncontrado.setEspecialidad(rs.getString("especialidad"));
                    medicoEncontrado.setActivo(rs.getBoolean("activo"));

                    return medico;
                } else {
                    throw new PersistenciaClinicaException(
                            "No se encontraron médicos activos con la especialidad: " + medico.getEspecialidad()
                    );
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar médico por ID: " + ex.getMessage());
        }
    }

    @Override
    public Medico consultarMedicoPorUsuario(String user) throws PersistenciaClinicaException {
        Medico medico = null;

        String consultaMedicoSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "JOIN Usuarios u ON m.idUsuario = u.idUsuario "
                + "WHERE u.user = ? AND m.activo = TRUE LIMIT 1";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaMedicoSQL)) {

            ps.setString(1, user);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico();
                    medico.setIdMedico(rs.getInt("idMedico"));
                    medico.setNombres(rs.getString("nombres"));
                    medico.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medico.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    medico.setCedula(rs.getString("cedula"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    medico.setActivo(rs.getBoolean("activo"));
                   
                    logger.info("Medico encontrado: " + medico);
                } else {
                    logger.warning("No se encontró el paciente con usuario: " + user);
                    throw new PersistenciaClinicaException("No se encontró un médico activo con el usuario: " + user);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar médico por usuario: " + ex.getMessage());
        }
        
        return medico;
    }

}

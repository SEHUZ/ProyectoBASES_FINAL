/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.DireccionPaciente;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion;

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    @Override
    public Paciente registrarPaciente(Paciente paciente, Usuario usuario, DireccionPaciente direccion) throws PersistenciaClinicaException, SQLException {
        String sentenciaUsuarioSQL = "INSERT INTO usuarios (User, contrasenia, rol) VALUES (?, ?, ?)";
        String sentenciaPacienteSQL = "INSERT INTO pacientes (idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sentenciaDireccionSQL = "INSERT INTO direccionesPacientes (idPaciente, Calle, Numero, codigoPostal) VALUES (?, ?, ?, ?)";
        
        Connection con = null;
        try {
        con = conexion.crearConexion();
        con.setAutoCommit(false);  // Iniciar transacción

        //Insertar usuario
        int idUsuario;
        try (PreparedStatement psUsuario = con.prepareStatement(sentenciaUsuarioSQL, Statement.RETURN_GENERATED_KEYS)) {

            psUsuario.setString(1, usuario.getUser());
            psUsuario.setString(2, usuario.getContrasenia());
            psUsuario.setString(3, usuario.getRol());

            int filasAfectadas = psUsuario.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("Falló el registro del usuario, ninguna fila ha sido afectada.");
            }

            try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idUsuario = (generatedKeys.getInt(1));
                } else {
                    throw new PersistenciaClinicaException("Falló el registro del paciente, no se obtuvo ID.");
                }
            }
        }
        
        //Insertar Paciente
        int idPaciente;
        try (PreparedStatement psPaciente = con.prepareStatement (sentenciaPacienteSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            psPaciente.setInt(1, idUsuario);
            psPaciente.setString(2, paciente.getNombres());
            psPaciente.setString(3, paciente.getApellidoPaterno());
            psPaciente.setString(4, paciente.getApellidoMaterno());
            psPaciente.setObject(5, paciente.getFechaNacimiento());
            psPaciente.setString(6, paciente.getTelefono());
            psPaciente.setString(7, paciente.getEmail());
            
            int filasAfectadas = psPaciente.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("Fallo el registro del paciente, ninguna fila ha sido afectada.");
            }
            
            try (ResultSet generatedKeys = psPaciente.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idPaciente = (generatedKeys.getInt(1));
                    paciente.setIdPaciente(idPaciente);
                }else {
                    throw new PersistenciaClinicaException("Fallo en el registro del Usuario, no se obtuvo ID");
                }
            } 
        }
        
        //Insertar Direccion
        try (PreparedStatement psDireccion = con.prepareStatement (sentenciaDireccionSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            psDireccion.setInt(1, idPaciente);
            psDireccion.setString(2, direccion.getCalle());
            psDireccion.setString(3, direccion.getNumero());
            psDireccion.setString(4, direccion.getCodigoPostal());
            
            psDireccion.executeUpdate();
        }
        
        con.commit();  // Confirmar transacción
        logger.info("Paciente registrado exitosamente. ID: " + idPaciente);
        return paciente;
        
        } catch (PersistenciaClinicaException ex) {
        try {
            if (con != null) con.rollback();  // Rollback en caso de error
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al hacer rollback", e);
        }
        logger.log(Level.SEVERE, "Error en el registro del paciente", ex);
        throw new PersistenciaClinicaException("Error al registrar al paciente: " + ex.getMessage(), ex);
    } finally {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cerrar conexión", e);
        }
    }
    }

    @Override
    public Paciente consultarPacientePorID(int id) throws PersistenciaClinicaException {
        Paciente paciente = null;
        String consultaPorIDSQL = "SELECT idPaciente, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono FROM pacientes WHERE idPaciente = ?";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement psConsulta = con.prepareStatement(consultaPorIDSQL)) {
            psConsulta.setInt(1, id);

            try (ResultSet rs = psConsulta.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setNombres(rs.getString("nombres"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    paciente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                    paciente.setTelefono(rs.getString("telefono"));

                    logger.info("Paciente encontrado: " + paciente);
                } else {
                    logger.warning("No se encontro el paciente con ID: " + id);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al consultar el paciente con ID: " + id, ex);
            throw new PersistenciaClinicaException("Error al consultar el paciente con ID: " + id, ex);
        }
        
        return paciente;
    }
    
    @Override
    public Paciente actualizarPaciente(Paciente paciente) throws PersistenciaClinicaException {
        // Primero, consulta el paciente actual
    Paciente pacienteActual = consultarPacientePorID(paciente.getIdPaciente());
    if (pacienteActual == null) {
        throw new PersistenciaClinicaException("Paciente no encontrado para actualización.");
    }
    
    // Verificar cada campo y mantener el valor actual si el nuevo es nulo o vacío
    if (paciente.getNombres() == null || paciente.getNombres().trim().isEmpty()) {
        paciente.setNombres(pacienteActual.getNombres());
    }
    if (paciente.getApellidoPaterno() == null || paciente.getApellidoPaterno().trim().isEmpty()) {
        paciente.setApellidoPaterno(pacienteActual.getApellidoPaterno());
    }
    if (paciente.getApellidoMaterno() == null || paciente.getApellidoMaterno().trim().isEmpty()) {
        paciente.setApellidoMaterno(pacienteActual.getApellidoMaterno());
    }
    if (paciente.getFechaNacimiento() == null) {
        paciente.setFechaNacimiento(pacienteActual.getFechaNacimiento());
    }
    if (paciente.getTelefono() == null || paciente.getTelefono().trim().isEmpty()) {
        paciente.setTelefono(pacienteActual.getTelefono());
    }
    if (paciente.getEmail() == null || paciente.getEmail().trim().isEmpty()) {
        paciente.setEmail(pacienteActual.getEmail());
    }
    
    // Proceder con la actualización usando los valores ya validados
    String sentenciaSQL = "UPDATE pacientes SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, telefono = ?, email = ? WHERE idPaciente = ?";
    Connection con = null;
    try {
        con = conexion.crearConexion();
        con.setAutoCommit(false); // Iniciar transacción
        try (PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {
            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setObject(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getTelefono());
            ps.setString(6, paciente.getEmail());
            ps.setInt(7, paciente.getIdPaciente());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaClinicaException("No se actualizó el paciente, verifique el ID: " + paciente.getIdPaciente());
            }
        }
        con.commit();
        logger.info("Paciente actualizado exitosamente. ID: " + paciente.getIdPaciente());
        return paciente;
    } catch (SQLException ex) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al hacer rollback", e);
            }
        }
        logger.log(Level.SEVERE, "Error al actualizar el paciente con ID: " + paciente.getIdPaciente(), ex);
        throw new PersistenciaClinicaException("Error al actualizar el paciente: " + ex.getMessage(), ex);
    } finally {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar la conexión", e);
            }
        }
    }
    }

    
}

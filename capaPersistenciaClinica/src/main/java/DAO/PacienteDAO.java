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
        String sentenciaPacienteSQL = "INSERT INTO pacientes (idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    String consultaPacienteSQL = "SELECT idPaciente, idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, email FROM pacientes WHERE idPaciente = ?";
    String consultaDireccionSQL = "SELECT calle, numero, codigoPostal FROM direccionespacientes WHERE idPaciente = ?";
    String consultaUsuarioSQL = "SELECT idUsuario, user, contrasenia, rol FROM usuarios WHERE idUsuario = ?";

    try (Connection con = this.conexion.crearConexion()) {
        // 1. Obtener datos del paciente
        try (PreparedStatement psPaciente = con.prepareStatement(consultaPacienteSQL)) {
            psPaciente.setInt(1, id);
            try (ResultSet rsPaciente = psPaciente.executeQuery()) {
                if (rsPaciente.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rsPaciente.getInt("idPaciente"));
                    int idUsuario = rsPaciente.getInt("idUsuario");
                    paciente.setNombres(rsPaciente.getString("nombres"));
                    paciente.setApellidoPaterno(rsPaciente.getString("apellidoPaterno"));
                    paciente.setApellidoMaterno(rsPaciente.getString("apellidoMaterno"));
                    paciente.setFechaNacimiento(rsPaciente.getDate("fechaNacimiento").toLocalDate());
                    paciente.setTelefono(rsPaciente.getString("telefono"));
                    paciente.setEmail(rsPaciente.getString("email"));

                    // 2. Obtener la dirección asociada
                    try (PreparedStatement psDireccion = con.prepareStatement(consultaDireccionSQL)) {
                        psDireccion.setInt(1, id);
                        try (ResultSet rsDireccion = psDireccion.executeQuery()) {
                            if (rsDireccion.next()) {
                                DireccionPaciente direccion = new DireccionPaciente();
                                direccion.setCalle(rsDireccion.getString("calle"));
                                direccion.setNumero(rsDireccion.getString("numero"));
                                direccion.setCodigoPostal(rsDireccion.getString("codigoPostal"));
                                paciente.setDireccion(direccion);
                            }
                        }
                    }

                    // 3. Obtener la información del usuario asociado
                    try (PreparedStatement psUsuario = con.prepareStatement(consultaUsuarioSQL)) {
                        psUsuario.setInt(1, idUsuario);
                        try (ResultSet rsUsuario = psUsuario.executeQuery()) {
                            if (rsUsuario.next()) {
                                Usuario usuario = new Usuario();
                                usuario.setIdUsuario(rsUsuario.getInt("idUsuario"));
                                usuario.setUser(rsUsuario.getString("user"));
                                usuario.setContrasenia(rsUsuario.getString("contrasenia"));
                                usuario.setRol(rsUsuario.getString("rol"));
                                paciente.setUsuario(usuario);
                            }
                        }
                    }

                    logger.info("Paciente encontrado: " + paciente);
                } else {
                    logger.warning("No se encontró el paciente con ID: " + id);
                }
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
    if (paciente.getUsuario().getContrasenia() == null || paciente.getUsuario().getContrasenia().trim().isEmpty()) {
        paciente.getUsuario().setContrasenia(pacienteActual.getUsuario().getContrasenia());
    }
    if (paciente.getUsuario().getUser() == null || paciente.getUsuario().getUser().trim().isEmpty()) {
        paciente.getUsuario().setUser(pacienteActual.getUsuario().getUser());
    }
    if (paciente.getDireccion().getCalle() == null || paciente.getDireccion().getCalle().trim().isEmpty()) {
        paciente.getDireccion().setCalle(pacienteActual.getDireccion().getCalle());
    }
    if (paciente.getDireccion().getCodigoPostal() == null || paciente.getDireccion().getCodigoPostal().trim().isEmpty()) {
        paciente.getDireccion().setCodigoPostal(pacienteActual.getDireccion().getCodigoPostal());
    }
    if (paciente.getDireccion().getNumero() == null || paciente.getDireccion().getNumero().trim().isEmpty()) {
        paciente.getDireccion().setNumero(pacienteActual.getDireccion().getNumero());
    }
    
    // Proceder con la actualización usando los valores ya validados
    Connection con = null;
    try {
        con = conexion.crearConexion();
        con.setAutoCommit(false); // Iniciar transacción

        // 1. Actualizar datos del paciente
        String updatePacienteSQL = "UPDATE pacientes SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, telefono = ?, email = ? WHERE idPaciente = ?";
        try (PreparedStatement psPaciente = con.prepareStatement(updatePacienteSQL)) {
            psPaciente.setString(1, paciente.getNombres());
            psPaciente.setString(2, paciente.getApellidoPaterno());
            psPaciente.setString(3, paciente.getApellidoMaterno());
            psPaciente.setObject(4, paciente.getFechaNacimiento());
            psPaciente.setString(5, paciente.getTelefono());
            psPaciente.setString(6, paciente.getEmail());
            psPaciente.setInt(7, paciente.getIdPaciente());

            int filasPaciente = psPaciente.executeUpdate();
            if (filasPaciente == 0) {
                throw new PersistenciaClinicaException("No se actualizó el paciente, verifique el ID: " + paciente.getIdPaciente());
            }
        }

        // 2. Actualizar datos de la dirección, si existe
        if (paciente.getDireccion() != null) {
            String updateDireccionSQL = "UPDATE direccionespacientes SET calle = ?, numero = ?, codigoPostal = ? WHERE idPaciente = ?";
            try (PreparedStatement psDireccion = con.prepareStatement(updateDireccionSQL)) {
                psDireccion.setString(1, paciente.getDireccion().getCalle());
                psDireccion.setString(2, paciente.getDireccion().getNumero());
                psDireccion.setString(3, paciente.getDireccion().getCodigoPostal());
                psDireccion.setInt(4, paciente.getIdPaciente());

                int filasDireccion = psDireccion.executeUpdate();
                if (filasDireccion == 0) {
                    logger.warning("No se actualizó la dirección para el paciente con ID: " + paciente.getIdPaciente());
                }
            }
        }

        // 3. Actualizar datos del usuario, si existe
        if (paciente.getUsuario() != null) {
            String updateUsuarioSQL = "UPDATE usuarios SET user = ?, contrasenia = ?, rol = ? WHERE idUsuario = ?";
            try (PreparedStatement psUsuario = con.prepareStatement(updateUsuarioSQL)) {
                psUsuario.setString(1, paciente.getUsuario().getUser());
                psUsuario.setString(2, paciente.getUsuario().getContrasenia());
                psUsuario.setString(3, paciente.getUsuario().getRol());
                psUsuario.setInt(4, paciente.getUsuario().getIdUsuario());

                int filasUsuario = psUsuario.executeUpdate();
                if (filasUsuario == 0) {
                    logger.warning("No se actualizó el usuario para el paciente con ID: " + paciente.getIdPaciente());
                }
            }
        }

        con.commit();  // Confirmar transacción
        logger.info("Paciente actualizado exitosamente: " + paciente);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.DireccionPaciente;
import Entidades.EstadosCita;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que implementa la interfaz IPacienteDAO para manejar las operaciones de
 * persistencia relacionadas con los pacientes en la base de datos.
 *
 * @author Daniel M
 */
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion; // Interfaz para la conexión a la base de datos

    /**
     * Constructor de la clase PacienteDAO.
     *
     * @param conexion La conexión a la base de datos que se utilizará para las
     * operaciones.
     */
    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    /**
     * Registra un nuevo paciente en la base de datos, incluyendo su usuario,
     * datos personales y dirección.
     *
     * @param paciente El objeto Paciente que contiene la información a
     * registrar.
     * @return El paciente registrado, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error durante el
     * registro del paciente.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     */
    @Override
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaClinicaException, SQLException {
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

                psUsuario.setString(1, paciente.getUsuario().getUser());
                psUsuario.setString(2, paciente.getUsuario().getContrasenia());
                psUsuario.setString(3, paciente.getUsuario().getRol());

                int filasAfectadas = psUsuario.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new PersistenciaClinicaException("Falló el registro del usuario del paciente, ninguna fila ha sido afectada.");
                }

                try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuario = generatedKeys.getInt(1);
                        paciente.getUsuario().setIdUsuario(idUsuario);
                    } else {
                        throw new PersistenciaClinicaException("Falló el registro del usuario del paciente, no se obtuvo ID.");
                    }
                }
            }

            //Insertar Paciente
            int idPaciente;
            try (PreparedStatement psPaciente = con.prepareStatement(sentenciaPacienteSQL, Statement.RETURN_GENERATED_KEYS)) {

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
                    } else {
                        throw new PersistenciaClinicaException("Fallo en el registro del Usuario, no se obtuvo ID");
                    }
                }
            }

            //Insertar Direccion
            try (PreparedStatement psDireccion = con.prepareStatement(sentenciaDireccionSQL, Statement.RETURN_GENERATED_KEYS)) {

                psDireccion.setInt(1, idPaciente);
                psDireccion.setString(2, paciente.getDireccion().getCalle());
                psDireccion.setString(3, paciente.getDireccion().getNumero());
                psDireccion.setString(4, paciente.getDireccion().getCodigoPostal());

                int filasAfectadas = psDireccion.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new PersistenciaClinicaException("Fallo el registro de la dirección del paciente, ninguna fila ha sido afectada.");
                }

                try (ResultSet generatedKeys = psDireccion.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        paciente.getDireccion().setIdDireccion(generatedKeys.getInt(1));
                    } else {
                        throw new PersistenciaClinicaException("Fallo en el registro de la dirección del paciente, no se obtuvo ID");
                    }
                }
            }

            con.commit();  // Confirmar transacción
            logger.info("Paciente registrado exitosamente. ID: " + idPaciente);
            return paciente;

        } catch (PersistenciaClinicaException ex) {
            try {
                if (con != null) {
                    con.rollback();  // Rollback en caso de error
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al hacer rollback", e);
            }
            logger.log(Level.SEVERE, "Error en el registro del paciente", ex);
            throw new PersistenciaClinicaException("Error al registrar al paciente: " + ex.getMessage(), ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar conexión", e);
            }
        }
    }

    /**
     * Actualiza la información de un paciente en la base de datos.
     *
     * Este método primero consulta el paciente actual y verifica cada campo del
     * objeto Paciente proporcionado. Si un campo es nulo o vacío, se mantiene
     * el valor actual. Luego, se procede a actualizar los datos en la base de
     * datos.
     *
     * @param paciente El objeto Paciente que contiene la información a
     * actualizar.
     * @return El paciente actualizado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * actualización o si el paciente no se encuentra.
     */
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
        } else if (paciente.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new PersistenciaClinicaException("La fecha de nacimiento debe ser valida.");
        }
        if (paciente.getTelefono() == null || paciente.getTelefono().trim().isEmpty()) {
            paciente.setTelefono(pacienteActual.getTelefono());
        } else if (paciente.getTelefono().length() != 10) {
            throw new PersistenciaClinicaException("Numero de telefono con formato incorrecto.");
        }

        if (paciente.getEmail() == null || paciente.getEmail().trim().isEmpty()) {
            paciente.setEmail(pacienteActual.getEmail());
        } else if (!validarCorreo(paciente.getEmail())) {
            throw new PersistenciaClinicaException("Correo con formato incorrecto.");
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
            String updatePacienteSQL = "UPDATE pacientes SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, email = ?, telefono = ? WHERE idPaciente = ?";
            try (PreparedStatement psPaciente = con.prepareStatement(updatePacienteSQL)) {
                psPaciente.setString(1, paciente.getNombres());
                psPaciente.setString(2, paciente.getApellidoPaterno());
                psPaciente.setString(3, paciente.getApellidoMaterno());
                psPaciente.setObject(4, paciente.getFechaNacimiento());
                psPaciente.setString(5, paciente.getEmail());
                psPaciente.setString(6, paciente.getTelefono());
                psPaciente.setInt(7, paciente.getIdPaciente());

                int filasPaciente = psPaciente.executeUpdate();
                if (filasPaciente == 0) {
                    throw new PersistenciaClinicaException("No se actualizó el paciente.");
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

    /**
     * Consulta un paciente en la base de datos por su ID.
     *
     * Este método obtiene la información del paciente, su dirección y su
     * usuario asociado.
     *
     * @param id El ID del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al ID proporcionado, o null si
     * no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
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

    /**
     * Consulta un paciente en la base de datos por su correo electrónico.
     *
     * Este método obtiene la información del paciente, su dirección y su
     * usuario asociado utilizando el correo proporcionado.
     *
     * @param correo El correo electrónico del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al correo proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    @Override
    public Paciente consultarPacientePorCorreo(String correo) throws PersistenciaClinicaException {
        Paciente paciente = null;
        String consultaPacienteSQL = "SELECT idPaciente, idUsuario, email, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono FROM pacientes WHERE email = ?";
        String consultaDireccionSQL = "SELECT idPaciente, calle, numero, codigoPostal FROM direccionespacientes WHERE idPaciente = ?";
        String consultaUsuarioSQL = "SELECT idUsuario, user, contrasenia, rol FROM usuarios WHERE idUsuario = ?";

        try (Connection con = this.conexion.crearConexion()) {

            // 1. Obtener datos del paciente
            try (PreparedStatement psPaciente = con.prepareStatement(consultaPacienteSQL)) {
                psPaciente.setString(1, correo);
                try (ResultSet rsPaciente = psPaciente.executeQuery()) {
                    if (rsPaciente.next()) {
                        paciente = new Paciente();
                        paciente.setIdPaciente(rsPaciente.getInt("idPaciente"));
                        int idUsuario = rsPaciente.getInt("idUsuario");
                        paciente.setEmail(rsPaciente.getString("email"));
                        paciente.setNombres(rsPaciente.getString("nombres"));
                        paciente.setApellidoPaterno(rsPaciente.getString("apellidoPaterno"));
                        paciente.setApellidoMaterno(rsPaciente.getString("apellidoMaterno"));
                        paciente.setFechaNacimiento(rsPaciente.getDate("fechaNacimiento").toLocalDate());
                        paciente.setTelefono(rsPaciente.getString("telefono"));

                        // 2. Obtener la dirección asociada
                        try (PreparedStatement psDireccion = con.prepareStatement(consultaDireccionSQL)) {
                            psDireccion.setInt(1, paciente.getIdPaciente());
                            try (ResultSet rsDireccion = psDireccion.executeQuery()) {
                                if (rsDireccion.next()) {
                                    DireccionPaciente direccion = new DireccionPaciente();
                                    direccion.setIdPaciente(rsDireccion.getInt("idPaciente"));
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
                        logger.warning("No se encontró el paciente con correo: " + correo);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al consultar el paciente con correo: " + correo, ex);
            throw new PersistenciaClinicaException("Error al consultar el paciente con correo: " + correo, ex);
        }

        return paciente;
    }

    /**
     * Consulta un paciente en la base de datos por su número de teléfono.
     *
     * Este método obtiene la información del paciente, su dirección y su
     * usuario asociado utilizando el número de teléfono proporcionado.
     *
     * @param telefono El número de teléfono del paciente que se desea
     * consultar.
     * @return El objeto Paciente correspondiente al teléfono proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    @Override
    public Paciente consultarPacientePorTelefono(String telefono) throws PersistenciaClinicaException {
        Paciente paciente = null;
        String consultaPacienteSQL = "SELECT idPaciente, idUsuario, email, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono FROM pacientes WHERE telefono = ?";
        String consultaDireccionSQL = "SELECT idPaciente, calle, numero, codigoPostal FROM direccionespacientes WHERE idPaciente = ?";
        String consultaUsuarioSQL = "SELECT idUsuario, user, contrasenia, rol FROM usuarios WHERE idUsuario = ?";

        try (Connection con = this.conexion.crearConexion()) {

            // 1. Obtener datos del paciente
            try (PreparedStatement psPaciente = con.prepareStatement(consultaPacienteSQL)) {
                psPaciente.setString(1, telefono);
                try (ResultSet rsPaciente = psPaciente.executeQuery()) {
                    if (rsPaciente.next()) {
                        paciente = new Paciente();
                        paciente.setIdPaciente(rsPaciente.getInt("idPaciente"));
                        int idUsuario = rsPaciente.getInt("idUsuario");
                        paciente.setEmail(rsPaciente.getString("email"));
                        paciente.setNombres(rsPaciente.getString("nombres"));
                        paciente.setApellidoPaterno(rsPaciente.getString("apellidoPaterno"));
                        paciente.setApellidoMaterno(rsPaciente.getString("apellidoMaterno"));
                        paciente.setFechaNacimiento(rsPaciente.getDate("fechaNacimiento").toLocalDate());
                        paciente.setTelefono(rsPaciente.getString("telefono"));

                        // 2. Obtener la dirección asociada
                        try (PreparedStatement psDireccion = con.prepareStatement(consultaDireccionSQL)) {
                            psDireccion.setInt(1, paciente.getIdPaciente());
                            try (ResultSet rsDireccion = psDireccion.executeQuery()) {
                                if (rsDireccion.next()) {
                                    DireccionPaciente direccion = new DireccionPaciente();
                                    direccion.setIdPaciente(rsDireccion.getInt("idPaciente"));
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
                        logger.warning("No se encontró el paciente con telefono: " + telefono);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al consultar el paciente con telefono: " + telefono, ex);
            throw new PersistenciaClinicaException("Error al consultar el paciente con telefono: " + telefono, ex);
        }

        return paciente;
    }

    /**
     * Consulta un paciente en la base de datos por su nombre de usuario.
     *
     * Este método obtiene la información del paciente, su dirección y su
     * usuario asociado utilizando el nombre de usuario proporcionado.
     *
     * @param user El nombre de usuario del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al usuario proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    @Override
    public Paciente consultarPacientePorUsuario(String user) throws PersistenciaClinicaException {
        Paciente paciente = null;
        String consultaPacienteSQL = "SELECT idPaciente, idUsuario, email, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono FROM pacientes WHERE idUsuario = (SELECT idUsuario FROM usuarios WHERE user = ?)";
        String consultaDireccionSQL = "SELECT idPaciente, calle, numero, codigoPostal FROM direccionespacientes WHERE idPaciente = ?";
        String consultaUsuarioSQL = "SELECT idUsuario, user, contrasenia, rol FROM usuarios WHERE user = ?";

        try (Connection con = this.conexion.crearConexion()) {

            // 1. Obtener datos del paciente
            try (PreparedStatement psPaciente = con.prepareStatement(consultaPacienteSQL)) {
                psPaciente.setString(1, user);
                try (ResultSet rsPaciente = psPaciente.executeQuery()) {
                    if (rsPaciente.next()) {
                        paciente = new Paciente();
                        paciente.setIdPaciente(rsPaciente.getInt("idPaciente"));
                        int idUsuario = rsPaciente.getInt("idUsuario");
                        paciente.setEmail(rsPaciente.getString("email"));
                        paciente.setNombres(rsPaciente.getString("nombres"));
                        paciente.setApellidoPaterno(rsPaciente.getString("apellidoPaterno"));
                        paciente.setApellidoMaterno(rsPaciente.getString("apellidoMaterno"));
                        paciente.setFechaNacimiento(rsPaciente.getDate("fechaNacimiento").toLocalDate());
                        paciente.setTelefono(rsPaciente.getString("telefono"));

                        // 2. Obtener la dirección asociada
                        try (PreparedStatement psDireccion = con.prepareStatement(consultaDireccionSQL)) {
                            psDireccion.setInt(1, paciente.getIdPaciente());
                            try (ResultSet rsDireccion = psDireccion.executeQuery()) {
                                if (rsDireccion.next()) {
                                    DireccionPaciente direccion = new DireccionPaciente();
                                    direccion.setIdPaciente(rsDireccion.getInt("idPaciente"));
                                    direccion.setCalle(rsDireccion.getString("calle"));
                                    direccion.setNumero(rsDireccion.getString("numero"));
                                    direccion.setCodigoPostal(rsDireccion.getString("codigoPostal"));
                                    paciente.setDireccion(direccion);
                                }
                            }
                        }

                        // 3. Obtener la información del usuario asociado
                        try (PreparedStatement psUsuario = con.prepareStatement(consultaUsuarioSQL)) {
                            psUsuario.setString(1, user);
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
                        logger.warning("No se encontró el paciente con usuario: " + user);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al consultar el paciente con usuario: " + user, ex);
            throw new PersistenciaClinicaException("Error al consultar el paciente con usuario: " + user, ex);
        }

        return paciente;
    }

    /**
     * Obtiene una lista de citas próximas para un paciente específico.
     *
     * Este método llama a un procedimiento almacenado en la base de datos que
     * devuelve las citas próximas asociadas al ID del paciente proporcionado.
     *
     * @param idPaciente El ID del paciente para el cual se desean obtener las
     * citas.
     * @return Una lista de objetos Cita que representan las citas próximas del
     * paciente.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta a la base de datos.
     */
    @Override
    public List<Cita> obtenerCitasProximasPorPaciente(int idPaciente) throws PersistenciaClinicaException {
        List<Cita> citasProximas = new ArrayList<>();
        String procedimiento = "{CALL ObtenerCitasProximasPorPaciente(?)}";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimiento)) {

            cs.setInt(1, idPaciente);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();

                    cita.setIdCita(rs.getInt("idCita"));
                    cita.setFechaHora(rs.getTimestamp("fechaHoraCita").toLocalDateTime());

                    // Crear objeto Medico
                    Medico medico = new Medico();
                    medico.setNombres(rs.getString("nombreMedico"));
                    medico.setApellidoPaterno(rs.getString("apellidoMedico"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    cita.setMedico(medico);

                    // Crear objeto EstadosCita
                    EstadosCita estado = new EstadosCita();
                    estado.setDescripcion(rs.getString("estadoCita"));
                    cita.setEstado(estado);

                    String tipoStr = rs.getString("tipoCita"); //Se recibe como tipo string
                    Cita.TipoCita tipo = Cita.TipoCita.valueOf(tipoStr.toUpperCase()); //Se debe convertir a el valor del Enum
                    cita.setTipoCita(tipo);

                    citasProximas.add(cita);
                }
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al obtener citas próximas", ex);
            throw new PersistenciaClinicaException("Error al obtener citas próximas: " + ex.getMessage());
        }

        return citasProximas;
    }

    /**
     * Valida el formato de un correo electrónico.
     *
     * Este método utiliza una expresión regular para verificar si el correo
     * electrónico proporcionado cumple con un formato válido.
     *
     * @param correo El correo electrónico que se desea validar.
     * @return true si el correo tiene un formato válido, false en caso
     * contrario.
     */
    public static boolean validarCorreo(String correo) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }
}

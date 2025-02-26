/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.HorarioMedico;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que maneja la persistencia de datos relacionados con médicos en la base
 * de datos. Implementa la interfaz IMedicoDAO.
 *
 * @author Daniel M
 */
public class MedicoDAO implements IMedicoDAO {

    private static final int DURACION = 30;

    IConexionBD conexion; // Interfaz para la conexión a la base de datos.

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion; // Constructor que inicializa la conexión a la base de datos.
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName()); // Logger para registrar eventos.

    /**
     * Registra un nuevo médico en la base de datos, incluyendo el registro de
     * un usuario asociado.
     *
     * @param medico El objeto Medico que contiene los datos del médico a
     * registrar.
     * @return El objeto Medico registrado, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error en la
     * persistencia.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public Medico registrarMedico(Medico medico) throws PersistenciaClinicaException, SQLException {
        String sentenciaUsuarioSQL = "INSERT INTO usuarios (User, contrasenia, rol) VALUES (?, ?, ?)";
        String sentenciaMedicoSQL = "INSERT INTO medicos (idUsuario, nombres, apellidoPaterno, apellidoMaterno, cedula, especialidad, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = conexion.crearConexion();
            con.setAutoCommit(false);  // Iniciar transacción

            // Insertar usuario
            int idUsuario;
            try (PreparedStatement psUsuario = con.prepareStatement(sentenciaUsuarioSQL, Statement.RETURN_GENERATED_KEYS)) {

                psUsuario.setString(1, medico.getUsuario().getUser());
                psUsuario.setString(2, medico.getUsuario().getContrasenia());
                psUsuario.setString(3, medico.getUsuario().getRol());

                int filasAfectadas = psUsuario.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new PersistenciaClinicaException("Falló el registro del usuario del medico, ninguna fila ha sido afectada.");
                }

                try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuario = generatedKeys.getInt(1);
                        medico.getUsuario().setIdUsuario(idUsuario);
                    } else {
                        throw new PersistenciaClinicaException("Falló el registro del usuario del medico, no se obtuvo ID.");
                    }
                }
            }

            // Insertar Médico
            int idMedico;
            try (PreparedStatement psMedico = con.prepareStatement(sentenciaMedicoSQL, Statement.RETURN_GENERATED_KEYS)) {

                psMedico.setInt(1, idUsuario);
                psMedico.setString(2, medico.getNombres());
                psMedico.setString(3, medico.getApellidoPaterno());
                psMedico.setString(4, medico.getApellidoMaterno());
                psMedico.setString(5, medico.getCedula());
                psMedico.setString(6, medico.getEspecialidad());
                psMedico.setBoolean(7, medico.isActivo());

                int filasAfectadas = psMedico.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new PersistenciaClinicaException("Fallo el registro del medico, ninguna fila ha sido afectada.");
                }

                try (ResultSet generatedKeys = psMedico.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idMedico = (generatedKeys.getInt(1));
                        medico.setIdMedico(idMedico);
                    } else {
                        throw new PersistenciaClinicaException("Fallo en el registro del Usuario, no se obtuvo ID");
                    }
                }
            }

            con.commit();  // Confirmar transacción
            logger.info("Medico registrado exitosamente. ID: " + idMedico);
            return medico;
        } catch (PersistenciaClinicaException ex) {
            try {
                if (con != null) {
                    con.rollback();  // Rollback en caso de error
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al hacer rollback", e);
            }
            logger.log(Level.SEVERE, "Error en el registro del medico", ex);
            throw new PersistenciaClinicaException("Error al registrar al medico: " + ex.getMessage(), ex);
        } finally {
            try {
                if (con != null) {
                    con.close(); // Cerrar conexión al finalizar
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar conexión", e);
            }
        }
    }

    /**
     * Actualiza el estado activo de un médico en la base de datos.
     *
     * @param medico El objeto Medico cuyo estado se va a actualizar.
     * @return true si se actualizó correctamente; false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error en la
     * persistencia.
     */
    @Override
    public boolean ActualizarEstado(Medico medico) throws PersistenciaClinicaException {
        String updateEstadoSQL = "UPDATE Medicos SET activo = ? WHERE idMedico = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(updateEstadoSQL, Statement.RETURN_GENERATED_KEYS)) {
            boolean nuevoEstado = medico.isActivo();

            ps.setBoolean(1, nuevoEstado);
            ps.setInt(2, medico.getIdMedico());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                medico.setActivo(nuevoEstado);
                return true; // Actualización exitosa
            }
            return false; // No se realizó ninguna actualización

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al actualizar estado del médico: " + ex.getMessage());
        }
    }

    /**
     * Consulta una lista de médicos que pertenecen a una especialidad
     * específica.
     *
     * @param Especialidad La especialidad por la cual se desea consultar los
     * médicos.
     * @return Una lista de médicos que coinciden con la especialidad
     * proporcionada.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta a la base de datos.
     */
    @Override
    public List<Medico> consultarMedicoPorEspecialidad(String Especialidad) throws PersistenciaClinicaException {
        List<Medico> medicos = new ArrayList<>();
        String consultaSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "WHERE m.especialidad = ? AND m.activo = TRUE";
        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {
            ps.setString(1, Especialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getInt("idMedico"));
                    medico.setNombres(rs.getString("nombres"));
                    medico.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medico.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    medico.setCedula(rs.getString("cedula"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    medico.setActivo(rs.getBoolean("activo"));
                    medicos.add(medico);  // Asegúrate de agregar el objeto Medico, no un String
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar medicos por especialidad: " + ex.getMessage(), ex);
        }
        return medicos;
    }

    /**
     * Consulta un médico específico por su ID.
     *
     * @param idMedico El ID del médico que se desea consultar.
     * @return El médico correspondiente al ID proporcionado.
     * @throws PersistenciaClinicaException Si el ID no es válido o si ocurre un
     * error durante la consulta.
     */
    @Override
    public Medico consultarMedicoPorID(int idMedico) throws PersistenciaClinicaException {
        String consultaMedicoIDSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "WHERE m.idMedico = ? AND m.activo = TRUE "
                + "LIMIT 1";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaMedicoIDSQL)) {
            if (idMedico <= 0) {
                throw new PersistenciaClinicaException("El ID del médico no es válido");
            }

            ps.setInt(1, idMedico);

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

                    return medicoEncontrado;
                } else {
                    throw new PersistenciaClinicaException("No se encontraron médicos");
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar médico por ID: " + ex.getMessage());
        }
    }

    /**
     * Consulta un médico por su especialidad, devolviendo solo un médico.
     *
     * @param Especialidad La especialidad por la cual se desea consultar un
     * médico.
     * @return Un médico que coincide con la especialidad proporcionada, o null
     * si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta a la base de datos.
     */
    @Override
    public Medico consultarUnMedicoPorEspecialidad(String Especialidad) throws PersistenciaClinicaException {
        Medico medico = null;
        String consultaSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "WHERE m.especialidad = ? AND m.activo = TRUE";
        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {
            ps.setString(1, Especialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medico = new Medico();
                    medico.setIdMedico(rs.getInt("idMedico"));
                    medico.setNombres(rs.getString("nombres"));
                    medico.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medico.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    medico.setCedula(rs.getString("cedula"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    medico.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar medicos por especialidad: " + ex.getMessage(), ex);
        }
        return medico;
    }

    /**
     * Consulta un médico por su nombre de usuario.
     *
     * @param user El nombre de usuario del médico que se desea consultar.
     * @return El médico correspondiente al usuario proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta o si no se encuentra el médico.
     */
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

    /**
     * Consulta un médico que está inactivo y está asociado a un usuario
     * específico.
     *
     * @param user El nombre de usuario del médico que se desea consultar.
     * @return El médico correspondiente al usuario proporcionado, si está
     * inactivo.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta o si no se encuentra el médico.
     */
    @Override
    public Medico consultarMedicoParaAlta(String user) throws PersistenciaClinicaException {
        Medico medico = null;

        String consultaMedicoSQL = "SELECT m.idMedico, m.idUsuario, m.nombres, m.apellidoPaterno, "
                + "m.apellidoMaterno, m.cedula, m.especialidad, m.activo "
                + "FROM Medicos m "
                + "JOIN Usuarios u ON m.idUsuario = u.idUsuario "
                + "WHERE u.user = ? AND m.activo = FALSE LIMIT 1";

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
                    logger.warning("No se encontró el medico con usuario: " + user);
                    throw new PersistenciaClinicaException("No se encontró un médico inactivo con el usuario: " + user);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar médico por usuario: " + ex.getMessage());
        }

        return medico;
    }

    /**
     * Obtiene los horarios de un médico específico.
     *
     * @param medico El médico del cual se desean obtener los horarios.
     * @return Una lista de horarios del médico proporcionado.
     * @throws PersistenciaClinicaException Si el médico es nulo o no válido, o
     * si ocurre un error durante la consulta.
     */
    @Override
    public List<HorarioMedico> obtenerHorariosMedico(Medico medico) throws PersistenciaClinicaException {
        if (medico == null || medico.getIdMedico() <= 0) {
            throw new PersistenciaClinicaException("Médico no válido");
        }
        int idMedico = medico.getIdMedico();
        String sql = "SELECT diaSemana, horaEntrada, horaSalida FROM HorariosMedicos WHERE idMedico = ?";
        List<HorarioMedico> horarios = new ArrayList<>();

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Convertir datos de la base de datos
                    DayOfWeek dia = DayOfWeek.valueOf(rs.getString("diaSemana").toUpperCase());
                    LocalTime entrada = rs.getTime("horaEntrada").toLocalTime();
                    LocalTime salida = rs.getTime("horaSalida").toLocalTime();

                    Medico medicoEncontrado = consultarMedicoPorID(idMedico);
                    horarios.add(new HorarioMedico(medicoEncontrado, entrada, salida, dia));
                }
            }
            return horarios;

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al obtener horarios: " + ex.getMessage());
        }
    }

}

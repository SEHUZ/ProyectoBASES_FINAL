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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoDAO implements IMedicoDAO {

    private static final int DURACION = 30;

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

    @Override
    public boolean verificarDisponibilidad(int idMedico, LocalDateTime fechaHoraCita) throws PersistenciaClinicaException {
        String disponibilidadSQL = "SELECT \n"
                + "    (h.horaEntrada <= TIME(?) \n"
                + "    AND h.horaSalida >= ADDTIME(TIME(?), ?) \n"
                + "    AND NOT EXISTS (...)\n"
                + ") AS disponible\n"
                + "FROM HorariosMedicos h\n"
                + "WHERE h.idMedico = ?\n"
                + "AND h.diaSemana = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(disponibilidadSQL)) {
            
            LocalTime horaInicio = fechaHoraCita.toLocalTime();
            LocalTime horaFin = horaInicio.plusMinutes(DURACION);

            ps.setTime(1, Time.valueOf(horaInicio));
            ps.setTime(2, Time.valueOf(horaFin));
            ps.setInt(3, idMedico);
            ps.setTimestamp(4, Timestamp.valueOf(fechaHoraCita));
            ps.setTimestamp(5, Timestamp.valueOf(fechaHoraCita.plusMinutes(DURACION)));
            ps.setInt(6, idMedico);
            ps.setString(7, fechaHoraCita.getDayOfWeek().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("disponible") > 0;
                }
                return false;
            }

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error verificando disponibilidad: " + ex.getMessage());
        }
    }

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

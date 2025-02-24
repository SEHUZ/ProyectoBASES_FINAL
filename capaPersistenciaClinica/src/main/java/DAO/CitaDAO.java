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
import Entidades.CitaEmergencia;
import Entidades.CitaNormal;
import Entidades.Paciente;
import Entidades.Medico;
import Entidades.EstadosCita;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;

public class CitaDAO implements ICitaDAO {

    IConexionBD conexion;

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;

    }

    // Método para insertar una cita general en la tabla Citas
    @Override
    public Cita agendarCita(Cita cita) throws PersistenciaClinicaException, SQLException {
        String sql = "{call AgendarCita(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion(); CallableStatement cstmt = conn.prepareCall(sql)) {

            // 1. Parámetros IN (índices 1-4)
            cstmt.setInt(1, cita.getPaciente().getIdPaciente());       // p_idPaciente
            cstmt.setInt(2, cita.getMedico().getIdMedico());           // p_idMedico
            cstmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora())); // p_fechaHora
            cstmt.setString(4, cita.getTipoCita().name());             // p_tipo

            // 2. Registrar parámetros OUT (índices 5 y 6)
            cstmt.registerOutParameter(5, Types.INTEGER);    // p_idCita
            cstmt.registerOutParameter(6, Types.VARCHAR);    // p_folio

            // Ejecutar el procedimiento
            cstmt.execute();

            // 3. Obtener valores de los parámetros OUT
            int idCita = cstmt.getInt(5);
            String folio = cstmt.getString(6);

            // Asignar valores a la entidad Cita
            cita.setIdCita(idCita);

            if (cita.getTipoCita() == Cita.TipoCita.EMERGENCIA) {
                CitaEmergencia emergencia = new CitaEmergencia();
                emergencia.setFolio(folio);
                cita.setEmergencia(emergencia);
            }

            return cita;

        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error en SP: " + e.getMessage());
        }
    }

    @Override
    public List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException {
        List<Cita> citas = new ArrayList<>();
        String procedimiento = "{CALL ObtenerCitasPorMedico(?)}";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimiento)) {

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

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultarEstadoSQL)) {

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

    @Override
    public boolean actualizarEstadoCita(int idCita, String nuevoEstado) throws PersistenciaClinicaException {
        String actualizarEstadoSQL = "UPDATE Citas SET idEstado = "
                + "(SELECT idEstado FROM EstadosCita WHERE descripcion = ?) "
                + "WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(actualizarEstadoSQL)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idCita);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenciaClinicaException("No se encontró la cita o el estado es inválido");
            }

            return true;

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al actualizar estado: " + ex.getMessage());
        }
    }

    @Override
    public boolean cancelarCita(int idCita) throws PersistenciaClinicaException {
        String sql = "UPDATE Citas SET idEstado = "
                + "(SELECT idEstado FROM EstadosCita WHERE descripcion = 'Cancelada') "
                + "WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new PersistenciaClinicaException("No se encontró la cita con ID: " + idCita);
            }

            return true;

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al cancelar cita: " + ex.getMessage());
        }
    }

    @Override
    public Cita consultarCitaPorID(int idCita) throws PersistenciaClinicaException {
        String sql = "SELECT c.*, e.descripcion AS estado, ce.folio, "
                + "m.nombres AS medico_nombres, m.apellidoPaterno AS medico_apellido, "
                + "p.nombres AS paciente_nombres "
                + "FROM Citas c "
                + "JOIN EstadosCita e ON c.idEstado = e.idEstado "
                + "JOIN Medicos m ON c.idMedico = m.idMedico "
                + "JOIN Pacientes p ON c.idPaciente = p.idPaciente "
                + "LEFT JOIN CitasEmergencias ce ON c.idCita = ce.idCita "
                + "WHERE c.idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCita);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("idCita"));
                    cita.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());

                    String tipoStr = rs.getString("tipoCita");
                    cita.setTipoCita(Cita.TipoCita.valueOf(tipoStr.toUpperCase()));

                    // Mapear Estado
                    EstadosCita estado = new EstadosCita();
                    estado.setDescripcion(rs.getString("estado"));
                    cita.setEstado(estado);

                    // Mapear Médico
                    Medico medico = new Medico();
                    medico.setNombres(rs.getString("medico_nombres"));
                    medico.setApellidoPaterno(rs.getString("medico_apellido"));
                    cita.setMedico(medico);

                    // Mapear Paciente
                    Paciente paciente = new Paciente();
                    paciente.setNombres(rs.getString("paciente_nombres"));
                    cita.setPaciente(paciente);

                    // Mapear Emergencia si existe
                    String folio = rs.getString("folio");
                    if (folio != null) {
                        CitaEmergencia emergencia = new CitaEmergencia();
                        emergencia.setFolio(folio);
                        cita.setEmergencia(emergencia);
                    }

                    return cita;

                } else {
                    throw new PersistenciaClinicaException("Cita no encontrada con ID: " + idCita);
                }
            }

        } catch (SQLException ex) {
            throw new PersistenciaClinicaException("Error al consultar cita: " + ex.getMessage());
        }
    }

    @Override
    public boolean insertarEstadoCita(int idCita, String estado) throws PersistenciaClinicaException {
        String sql = "UPDATE citas SET estado = ? WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idCita);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false en caso de error
        }
    }

    @Override
    public Cita agendarCitaEmergencia(Cita cita) throws PersistenciaClinicaException, SQLException {
        String sql = "{CALL AgendarCitaEmergencia(?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion(); CallableStatement cstmt = conn.prepareCall(sql)) {

            // 1. Parámetro IN
            cstmt.setInt(1, cita.getPaciente().getIdPaciente());  // p_idPaciente

            // 2. Registrar parámetros OUT
            cstmt.registerOutParameter(2, Types.VARCHAR);   // p_folio
            cstmt.registerOutParameter(3, Types.INTEGER);   // p_idCita
            cstmt.registerOutParameter(4, Types.TIMESTAMP); // p_fechaExpiracion

            // 3. Ejecutar el SP
            cstmt.execute();

            // 4. Obtener los valores OUT
            String folio = cstmt.getString(2);
            int idCita = cstmt.getInt(3);
            Timestamp fechaExpiracion = cstmt.getTimestamp(4);

            // 5. Asignar valores a la entidad Cita
            cita.setIdCita(idCita);
            cita.setTipoCita(Cita.TipoCita.EMERGENCIA);

            // 6. Crear el objeto CitaEmergencia y asignarlo a la cita
            CitaEmergencia emergencia = new CitaEmergencia();
            emergencia.setFolio(folio);

            if (fechaExpiracion != null) {
                emergencia.setFechaExpiracion(fechaExpiracion.toLocalDateTime());
            }

            // En tu entidad, si quieres enlazar bidireccionalmente:
            // emergencia.setCita(cita);
            cita.setEmergencia(emergencia);

            return cita;

        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al agendar cita de emergencia: " + e.getMessage());
        }
    }

    @Override
    public List<Cita> consultarCitasProximasPorPaciente(Paciente paciente) throws PersistenciaClinicaException {
        List<Cita> citas = new ArrayList<>();
        String procedimiento = "{CALL ObtenerCitasProximasPorPaciente(?)}";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimiento)) {

            // Establecer parámetro
            cs.setInt(1, paciente.getIdPaciente());

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();

                    // Datos básicos de la cita
                    cita.setIdCita(rs.getInt("idCita"));
                    cita.setFechaHora(rs.getTimestamp("fechaHoraCita").toLocalDateTime());

                    // Tipo de cita String a Enum
                    String tipoCitaStr = rs.getString("tipoCita");
                    Cita.TipoCita tipoCita = Cita.TipoCita.valueOf(tipoCitaStr);
                    cita.setTipoCita(tipoCita);

                    // Médico
                    Medico medico = new Medico();
                    medico.setNombres(rs.getString("nombreMedico"));
                    medico.setApellidoPaterno(rs.getString("apellidoMedico"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    cita.setMedico(medico);

                    // Estado de la cita
                    EstadosCita estado = new EstadosCita();
                    estado.setDescripcion(rs.getString("estadoCita"));
                    cita.setEstado(estado);

                    // CitaEmergencia o CitaNormal según el tipo
                    if (tipoCita == Cita.TipoCita.EMERGENCIA) {
                        CitaEmergencia emergencia = new CitaEmergencia();
                        emergencia.setFolio(rs.getString("folioEmergencia"));
                        emergencia.setFechaExpiracion(rs.getTimestamp("fechaExpiracionEmergencia") != null ? rs.getTimestamp("fechaExpiracionEmergencia").toLocalDateTime(): null);
                        cita.setEmergencia(emergencia);
                    } else if (tipoCita == Cita.TipoCita.PROGRAMADA) {
                        CitaNormal normal = new CitaNormal();
                        cita.setNormal(normal);
                    }

                    citas.add(cita);
                }
            }

        } catch (SQLException ex) {
            // Manejar error específico de "paciente no existe"
            if (ex.getErrorCode() == 1644) { // Código de error para SIGNAL SQLSTATE '45000'
                throw new PersistenciaClinicaException("El paciente no existe");
            }
            throw new PersistenciaClinicaException("Error al consultar citas próximas: " + ex.getMessage());
        }

        return citas;
    }

}

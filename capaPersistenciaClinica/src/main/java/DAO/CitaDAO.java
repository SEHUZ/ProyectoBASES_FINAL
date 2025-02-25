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

            cstmt.setInt(1, cita.getPaciente().getIdPaciente());
            cstmt.setInt(2, cita.getMedico().getIdMedico());
            cstmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            cstmt.setString(4, cita.getTipoCita().name());

            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.registerOutParameter(6, Types.VARCHAR);

            cstmt.execute();

            int idCita = cstmt.getInt(5);
            String folio = cstmt.getString(6);

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
        String updateSQL = "UPDATE Citas SET idEstado = "
                + "(SELECT idEstado FROM EstadosCita WHERE descripcion = 'Cancelada') "
                + "WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(updateSQL)) {

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
        final String PROCEDIMIENTO = "{CALL ObtenerCitaPorID(?)}";
        Cita cita = null;

        try (Connection conn = conexion.crearConexion();
             CallableStatement cs = conn.prepareCall(PROCEDIMIENTO)) {

            cs.setInt(1, idCita);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    cita = mapear(rs);
                }
            }

        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1644) { // Código para SIGNAL SQLSTATE '45000'
            throw new PersistenciaClinicaException("Cita no encontrada: " + ex.getMessage());
        }
        throw new PersistenciaClinicaException(
            "Error técnico al consultar cita ID " + idCita + ": " + ex.getMessage()
        );
        }

        if (cita == null) {
            throw new PersistenciaClinicaException("Cita no encontrada con ID: " + idCita);
        }

        return cita;
    }

    private Cita mapear(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        
        cita.setIdCita(rs.getInt("idCita"));
        cita.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
        
        EstadosCita estado = new EstadosCita();
        estado.setIdEstado(rs.getInt("idEstado"));
        estado.setDescripcion(rs.getString("estadoCita"));
        cita.setEstado(estado);

        Paciente paciente = new Paciente();
        paciente.setIdPaciente(rs.getInt("idPaciente"));
        paciente.setNombres(rs.getString("nombrePaciente"));
        paciente.setApellidoPaterno(rs.getString("apellidoPaternoPaciente"));
        paciente.setApellidoMaterno(rs.getString("apellidoMaternoPaciente"));
        cita.setPaciente(paciente);

        Medico medico = new Medico();
        medico.setIdMedico(rs.getInt("idMedico"));
        medico.setNombres(rs.getString("nombreMedico"));
        medico.setApellidoPaterno(rs.getString("apellidoPaternoMedico"));
        medico.setApellidoMaterno(rs.getString("apellidoMaternoMedico"));
        cita.setMedico(medico);

        if (rs.getString("tipoCitaNormal") == null) {
            CitaEmergencia emergencia = new CitaEmergencia();
            emergencia.setFolio(rs.getString("folioEmergencia"));
            emergencia.setFechaExpiracion(rs.getTimestamp("fechaExpiracion") != null 
                ? rs.getTimestamp("fechaExpiracion").toLocalDateTime() 
                : null);
            cita.setEmergencia(emergencia);
        } else {
            cita.setNormal(new CitaNormal());
        }

        return cita;
    }
        

    @Override
    public boolean insertarEstadoCita(int idCita, String estado) throws PersistenciaClinicaException {
        String UpdateSQL = "UPDATE citas SET estado = ? WHERE idCita = ?";

        try (Connection conn = conexion.crearConexion(); PreparedStatement ps = conn.prepareStatement(UpdateSQL)) {
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
        String procedimientoEmergencia = "{CALL AgendarCitaEmergencia(?, ?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion(); CallableStatement cstmt = conn.prepareCall(procedimientoEmergencia)) {

            cstmt.setInt(1, cita.getPaciente().getIdPaciente());

            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.TIMESTAMP);
            cstmt.registerOutParameter(5, Types.TIMESTAMP);

            cstmt.execute();

            String folio = cstmt.getString(2);
            int idCita = cstmt.getInt(3);
            Timestamp fechaExpiracion = cstmt.getTimestamp(4);
            Timestamp fechaHoraCita = cstmt.getTimestamp(5);
            // Asignar valores a la cita
            cita.setIdCita(idCita);
            cita.setTipoCita(Cita.TipoCita.EMERGENCIA);
            cita.setFechaHora(fechaHoraCita.toLocalDateTime());

            CitaEmergencia emergencia = new CitaEmergencia();
            emergencia.setFolio(folio);

            if (fechaExpiracion != null) {
                emergencia.setFechaExpiracion(fechaExpiracion.toLocalDateTime());
            }

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
                        emergencia.setFechaExpiracion(rs.getTimestamp("fechaExpiracionEmergencia") != null ? rs.getTimestamp("fechaExpiracionEmergencia").toLocalDateTime() : null);
                        cita.setEmergencia(emergencia);
                    } else if (tipoCita == Cita.TipoCita.PROGRAMADA) {
                        CitaNormal normal = new CitaNormal();
                        cita.setNormal(normal);
                    }

                    citas.add(cita);
                }
            }

        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1644) { // Código de error para SIGNAL SQLSTATE '45000'
                throw new PersistenciaClinicaException("El paciente no existe");
            }
            throw new PersistenciaClinicaException("Error al consultar citas próximas: " + ex.getMessage());
        }

        return citas;
    }

}

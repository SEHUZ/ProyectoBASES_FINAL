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
import Entidades.Paciente;
import Entidades.Medico;
import Entidades.EstadosCita;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Timestamp;


public class CitaDAO implements ICitaDAO {

    IConexionBD conexion;

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;

    }

    // Método para insertar una cita general en la tabla Citas
    @Override
    public Cita agendarCita(Cita cita) throws PersistenciaClinicaException, SQLException {
        String sql = "{call AgendarCita(?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion(); CallableStatement cstmt = conn.prepareCall(sql)) {

            // Mapear parámetros desde la entidad
            cstmt.setInt(1, cita.getPaciente().getIdPaciente());
            cstmt.setInt(2, cita.getMedico().getIdMedico());
            cstmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            cstmt.setString(4, cita.getTipoCita().name());

            // Ejecutar el procedimiento
            cstmt.execute();

            // Obtener el ID generado
            try (ResultSet generatedKeys = cstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cita.setIdCita(generatedKeys.getInt(1));
                } else {
                    throw new PersistenciaClinicaException("No se pudo obtener el ID de la cita generada");
                }
            }

            // Si es emergencia, recuperar datos adicionales
            if (cita.getTipoCita() == Cita.TipoCita.EMERGENCIA) {
                String citaEmergenciaSQL = "SELECT folio FROM CitasEmergencias WHERE idCita = ?";
                try (PreparedStatement ps = conn.prepareStatement(citaEmergenciaSQL)) {
                    ps.setInt(1, cita.getIdCita());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        CitaEmergencia emergencia = new CitaEmergencia();
                        emergencia.setFolio(rs.getString("folio"));
                        cita.setEmergencia(emergencia);
                    }
                }
            }

            return cita;
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
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(actualizarEstadoSQL)) {
            
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
        
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
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

}

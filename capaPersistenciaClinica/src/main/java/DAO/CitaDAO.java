/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

// Imports
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

    /**
     * Método para agendar una cita en la base de datos utilizando un
     * procedimiento almacenado.
     *
     * @param cita Objeto de tipo Cita con la información de la cita a
     * registrar.
     * @return La cita registrada con el ID asignado y, en caso de ser de
     * emergencia, su folio correspondiente.
     * @throws PersistenciaClinicaException En caso de error en la ejecución del
     * procedimiento almacenado.
     * @throws SQLException En caso de error en la conexión a la base de datos.
     */
    @Override
    public Cita agendarCita(Cita cita) throws PersistenciaClinicaException, SQLException {
        String sql = "{call AgendarCita(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = conexion.crearConexion(); CallableStatement cstmt = conn.prepareCall(sql)) {
            // Asignación de parámetros de entrada
            cstmt.setInt(1, cita.getPaciente().getIdPaciente());
            cstmt.setInt(2, cita.getMedico().getIdMedico());
            cstmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            cstmt.setString(4, cita.getTipoCita().name());

            // Parámetros de salida (ID de la cita y folio si es emergencia)
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.registerOutParameter(6, Types.VARCHAR);

            // Ejecución del procedimiento almacenado
            cstmt.execute();

            // Recuperación de valores de salida
            int idCita = cstmt.getInt(5);
            String folio = cstmt.getString(6);
            cita.setIdCita(idCita);

            // Si la cita es de emergencia, se asigna el folio correspondiente
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

    /**
     * Consulta todas las citas asociadas a un médico en la base de datos.
     *
     * @param medico Objeto de tipo Medico con el ID del médico a consultar.
     * @return Lista de citas asociadas al médico.
     * @throws PersistenciaClinicaException En caso de error en la consulta a la
     * base de datos.
     */
    @Override
    public List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException {
        List<Cita> citas = new ArrayList<>();
        String procedimiento = "{CALL ObtenerCitasPorMedico(?)}";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimiento)) {
            cs.setInt(1, medico.getIdMedico());

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Creación y asignación de valores de la cita
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("idCita"));
                    cita.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());

                    // Asignación de información del paciente
                    Paciente paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setNombres(rs.getString("nombrePaciente"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPaternoPaciente"));
                    paciente.setApellidoMaterno(rs.getString("apellidoMaternoPaciente"));
                    cita.setPaciente(paciente);

                    // Asignación de información del médico
                    Medico medicoCita = new Medico();
                    medicoCita.setIdMedico(rs.getInt("idMedico"));
                    medicoCita.setNombres(rs.getString("nombreMedico"));
                    medicoCita.setApellidoPaterno(rs.getString("apellidoPaternoMedico"));
                    medicoCita.setApellidoMaterno(rs.getString("apellidoMaternoMedico"));
                    cita.setMedico(medicoCita);

                    // Asignación del estado de la cita
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

    /**
     * Consulta el estado de una cita específica en la base de datos.
     *
     * @param idCita ID de la cita a consultar.
     * @return Objeto EstadosCita con la información del estado de la cita.
     * @throws PersistenciaClinicaException En caso de que no se encuentre la
     * cita o haya un error en la consulta.
     */
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

    /**
     * Actualiza el estado de una cita en la base de datos.
     *
     * @param idCita ID de la cita a actualizar.
     * @param nuevoEstado El nuevo estado que se asignará a la cita.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * actualización.
     */
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

    /**
     * Cancela una cita estableciendo su estado a "Cancelada".
     *
     * @param idCita ID de la cita a cancelar.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * cancelación.
     */
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

    /**
     * Consulta una cita por su ID y retorna un objeto Cita con sus detalles.
     *
     * @param idCita ID de la cita a consultar.
     * @return Un objeto Cita con todos los datos correspondientes, o null si no
     * se encontró.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    @Override
    public Cita consultarCitaPorID(int idCita) throws PersistenciaClinicaException {
        Cita cita = null; // Inicializa la variable cita como null
        String procedimiento = "SELECT c.idCita, c.fechaHora, p.idPaciente, p.nombres, p.apellidoPaterno, p.apellidoMaterno, "
                + "m.idMedico, m.nombres, m.apellidoPaterno, m.apellidoMaterno, e.idEstado, e.descripcion "
                + "FROM Citas c "
                + "JOIN Pacientes p ON c.idPaciente = p.idPaciente "
                + "JOIN Medicos m ON c.idMedico = m.idMedico "
                + "JOIN EstadosCita e ON c.idEstado = e.idEstado WHERE c.idCita = ?;";

        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(procedimiento)) {
            cs.setInt(1, idCita);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    // Datos cita
                    cita = new Cita();
                    cita.setIdCita(rs.getInt("c.idCita"));
                    cita.setFechaHora(rs.getTimestamp("c.fechaHora").toLocalDateTime());

                    // Datos paciente
                    Paciente paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setNombres(rs.getString("nombres")); // Corregido: 'nombrePaciente' por 'nombres'
                    paciente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    paciente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    cita.setPaciente(paciente);

                    // Datos medico
                    Medico medicoCita = new Medico();
                    medicoCita.setIdMedico(rs.getInt("idMedico"));
                    medicoCita.setNombres(rs.getString("nombres")); // Corregido: 'nombreMedico' por 'nombres'
                    medicoCita.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    medicoCita.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    cita.setMedico(medicoCita);

                    // Datos estadoCita
                    EstadosCita estado = new EstadosCita();
                    estado.setIdEstado(rs.getInt("idEstado"));
                    estado.setDescripcion(rs.getString("descripcion"));
                    cita.setEstado(estado);
                }
            }

        } catch (SQLException ex) {
            //throw new PersistenciaClinicaException("Error al obtener la cita por ID: " + ex.getMessage());
        }
        return cita; // Retornamos la cita o null si no se encontró
    }

    /**
     * Mapea los datos de un ResultSet a un objeto Cita.
     *
     * @param rs El ResultSet que contiene los datos de la cita.
     * @return Un objeto Cita mapeado con todos sus atributos.
     * @throws SQLException Si ocurre un error al acceder a los datos del
     * ResultSet.
     */
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
        paciente.setNombres(rs.getString("nombres")); // Corregido: 'nombrePaciente' por 'nombres'
        paciente.setApellidoPaterno(rs.getString("apellidoPaternoPaciente"));
        paciente.setApellidoMaterno(rs.getString("apellidoMaternoPaciente"));
        cita.setPaciente(paciente);

        Medico medico = new Medico();
        medico.setIdMedico(rs.getInt("idMedico"));
        medico.setNombres(rs.getString("nombres")); // Corregido: 'nombreMedico' por 'nombres'
        medico.setApellidoPaterno(rs.getString("apellidoPaternoMedico"));
        medico.setApellidoMaterno(rs.getString("apellidoMaternoMedico"));
        cita.setMedico(medico);

        // Verifica si la cita es de emergencia
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

        return cita; // Retorna la cita mapeada
    }

    @Override
    /**
     * Inserta el estado de una cita en la base de datos.
     *
     * @param idCita El ID de la cita que se desea actualizar.
     * @param estado El nuevo estado a asignar a la cita.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error al intentar
     * actualizar el estado.
     */
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
    /**
     * Agenda una cita de emergencia en la base de datos.
     *
     * @param cita El objeto Cita que contiene los datos de la cita a agendar.
     * @return El objeto Cita actualizado con el ID y otros datos asignados.
     * @throws PersistenciaClinicaException Si ocurre un error al intentar
     * agendar la cita.
     * @throws SQLException Si ocurre un error de SQL al ejecutar el
     * procedimiento almacenado.
     */
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
    /**
     * Consulta las citas próximas para un paciente específico.
     *
     * @param paciente El objeto Paciente cuyo citas se desean consultar.
     * @return Una lista de citas próximas del paciente.
     * @throws PersistenciaClinicaException Si ocurre un error al intentar
     * consultar las citas.
     */
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

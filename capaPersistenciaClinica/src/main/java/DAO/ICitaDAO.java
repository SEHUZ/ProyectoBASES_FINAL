/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.CitaEmergencia;
import Entidades.EstadosCita;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con las
 * citas.
 *
 * Esta interfaz proporciona métodos para agendar, consultar, actualizar y
 * cancelar citas, así como para gestionar los estados de las citas.
 *
 * @author Jose
 */
public interface ICitaDAO {

    /**
     * Agenda una nueva cita en la base de datos.
     *
     * @param cita El objeto Cita que contiene la información de la cita a
     * agendar.
     * @return El objeto Cita agendada, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error durante el
     * agendamiento de la cita.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     */
    public Cita agendarCita(Cita cita) throws PersistenciaClinicaException, SQLException;

    /**
     * Consulta las citas programadas para un médico específico.
     *
     * @param medico El objeto Medico para el cual se desean consultar las
     * citas.
     * @return Una lista de objetos Cita que representan las citas del médico.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException;

    /**
     * Actualiza el estado de una cita existente.
     *
     * @param idCita El ID de la cita cuyo estado se desea actualizar.
     * @param nuevoEstado El nuevo estado que se asignará a la cita.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * actualización.
     */
    public boolean actualizarEstadoCita(int idCita, String nuevoEstado) throws PersistenciaClinicaException;

    /**
     * Consulta el estado de una cita específica.
     *
     * @param idCita El ID de la cita cuyo estado se desea consultar.
     * @return El objeto EstadosCita que representa el estado de la cita.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public EstadosCita consultarEstadoCita(int idCita) throws PersistenciaClinicaException;

    /**
     * Cancela una cita existente.
     *
     * @param idCita El ID de la cita que se desea cancelar.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * cancelación.
     */
    public boolean cancelarCita(int idCita) throws PersistenciaClinicaException;

    /**
     * Consulta una cita específica por su ID.
     *
     * @param idCita El ID de la cita que se desea consultar.
     * @return El objeto Cita correspondiente al ID proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Cita consultarCitaPorID(int idCita) throws PersistenciaClinicaException;

    /**
     * Inserta un nuevo estado para una cita existente.
     *
     * @param idCita El ID de la cita a la que se le desea asignar un nuevo
     * estado.
     * @param estado El nuevo estado que se asignará a la cita.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * inserción.
     */
    public boolean insertarEstadoCita(int idCita, String estado) throws PersistenciaClinicaException;

    /**
     * Agenda una cita de emergencia en la base de datos.
     *
     * @param cita El objeto Cita que contiene la información de la cita de
     * emergencia a agendar.
     * @return El objeto Cita de emergencia agendada, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error durante el
     * agendamiento de la cita.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     */
    public Cita agendarCitaEmergencia(Cita cita) throws PersistenciaClinicaException, SQLException;

    /**
     * Consulta las citas próximas para un paciente específico.
     *
     * @param paciente El objeto Paciente para el cual se desean consultar las
     * citas próximas.
     * @return Una lista de objetos Cita que representan las citas próximas del
     * paciente.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public List<Cita> consultarCitasProximasPorPaciente(Paciente paciente) throws PersistenciaClinicaException;

}

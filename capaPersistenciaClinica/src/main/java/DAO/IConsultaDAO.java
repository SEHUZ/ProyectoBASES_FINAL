/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Consulta;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con las
 * consultas.
 *
 * Esta interfaz proporciona métodos para insertar, obtener, cancelar y
 * verificar consultas asociadas a pacientes y citas.
 *
 * @author sonic
 */
public interface IConsultaDAO {

    /**
     * Inserta una nueva consulta en la base de datos.
     *
     * @param consulta El objeto Consulta que contiene la información de la
     * consulta a insertar.
     * @return El objeto Consulta insertado, con su ID asignado.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * inserción.
     */
    Consulta insertarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException;

    /**
     * Obtiene todas las consultas de un paciente específico.
     *
     * @param idPaciente El ID del paciente cuyas consultas se desean obtener.
     * @return Una lista de objetos Consulta que representan las consultas del
     * paciente.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    List<Consulta> obtenerConsultasPorPaciente(int idPaciente) throws SQLException, PersistenciaClinicaException;

    /**
     * Cancela una consulta existente.
     *
     * @param idConsulta El ID de la consulta que se desea cancelar.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * cancelación.
     */
    boolean cancelarConsulta(int idConsulta) throws SQLException, PersistenciaClinicaException;

    /**
     * Obtiene una consulta específica por su ID.
     *
     * @param idConsulta El ID de la consulta que se desea obtener.
     * @return El objeto Consulta correspondiente al ID proporcionado.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    Consulta obtenerConsultaPorId(int idConsulta) throws SQLException, PersistenciaClinicaException;

    /**
     * Obtiene las consultas de un paciente por especialidad y rango de fechas.
     *
     * @param idPaciente El ID del paciente cuyas consultas se desean obtener.
     * @param especialidad La especialidad de las consultas a filtrar.
     * @param fechaInicio La fecha de inicio del rango de fechas.
     * @param fechaFin La fecha de fin del rango de fechas.
     * @return Una lista de objetos Consulta que cumplen con los criterios
     * especificados.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    List<Consulta> obtenerConsultasPorEspecialidadYFechas(int idPaciente, String especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException, PersistenciaClinicaException;

    /**
     * Verifica si existe una consulta asociada a una cita específica.
     *
     * @param idCita El ID de la cita que se desea verificar.
     * @return true si existe una consulta para la cita, false en caso
     * contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * verificación.
     */
    boolean existeConsultaParaCita(int idCita) throws PersistenciaClinicaException;

    /**
     * Obtiene el historial de consultas de un paciente.
     *
     * @param idPaciente El ID del paciente cuyo historial de consultas se desea
     * obtener.
     * @return Una lista de objetos Consulta que representan el historial de
     * consultas del paciente.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    List<Consulta> obtenerHistorialConsultasPaciente(int idPaciente) throws PersistenciaClinicaException;
}

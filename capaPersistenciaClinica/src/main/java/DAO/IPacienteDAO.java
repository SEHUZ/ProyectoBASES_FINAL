/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con los
 * pacientes.
 *
 * Esta interfaz proporciona métodos para registrar, consultar y actualizar
 * información sobre pacientes, así como para obtener sus citas próximas.
 *
 * @author sonic
 */
public interface IPacienteDAO {

    /**
     * Registra un nuevo paciente en la base de datos.
     *
     * @param paciente El objeto Paciente que contiene la información a
     * registrar.
     * @return El objeto Paciente registrado, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error durante el
     * registro.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     */
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaClinicaException, SQLException;

    /**
     * Consulta un paciente específico por su ID.
     *
     * @param id El ID del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al ID proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Paciente consultarPacientePorID(int id) throws PersistenciaClinicaException;

    /**
     * Actualiza la información de un paciente en la base de datos.
     *
     * @param paciente El objeto Paciente que contiene la información a
     * actualizar.
     * @return El objeto Paciente actualizado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * actualización.
     */
    public Paciente actualizarPaciente(Paciente paciente) throws PersistenciaClinicaException;

    /**
     * Consulta un paciente en la base de datos por su correo electrónico.
     *
     * @param correo El correo electrónico del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al correo proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Paciente consultarPacientePorCorreo(String correo) throws PersistenciaClinicaException;

    /**
     * Consulta un paciente en la base de datos por su número de teléfono.
     *
     * @param telefono El número de teléfono del paciente que se desea
     * consultar.
     * @return El objeto Paciente correspondiente al teléfono proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Paciente consultarPacientePorTelefono(String telefono) throws PersistenciaClinicaException;

    /**
     * Consulta un paciente en la base de datos por su nombre de usuario.
     *
     * @param User El nombre de usuario del paciente que se desea consultar.
     * @return El objeto Paciente correspondiente al usuario proporcionado, o
     * null si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Paciente consultarPacientePorUsuario(String User) throws PersistenciaClinicaException;

    /**
     * Obtiene las citas próximas para un paciente específico.
     *
     * @param idPaciente El ID del paciente cuyas citas próximas se desean
     * obtener.
     * @return Una lista de objetos Cita que representan las citas próximas del
     * paciente.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public List<Cita> obtenerCitasProximasPorPaciente(int idPaciente) throws PersistenciaClinicaException;
}

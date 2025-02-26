/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.HorarioMedico;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con los
 * médicos.
 *
 * Esta interfaz proporciona métodos para consultar, registrar y actualizar
 * información sobre médicos, así como para obtener sus horarios.
 *
 * @author sonic
 */
public interface IMedicoDAO {

    /**
     * Actualiza el estado de un médico en la base de datos.
     *
     * @param medico El objeto Medico que contiene la información a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * actualización.
     */
    public boolean ActualizarEstado(Medico medico) throws PersistenciaClinicaException;

    /**
     * Consulta una lista de médicos que pertenecen a una especialidad
     * específica.
     *
     * @param Especialidad La especialidad por la cual se desea consultar los
     * médicos.
     * @return Una lista de médicos que coinciden con la especialidad
     * proporcionada.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public List<Medico> consultarMedicoPorEspecialidad(String Especialidad) throws PersistenciaClinicaException;

    /**
     * Consulta un médico específico por su ID.
     *
     * @param IdMedico El ID del médico que se desea consultar.
     * @return El médico correspondiente al ID proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Medico consultarMedicoPorID(int IdMedico) throws PersistenciaClinicaException;

    /**
     * Consulta un médico que está inactivo y está asociado a un usuario
     * específico.
     *
     * @param user El nombre de usuario del médico que se desea consultar.
     * @return El médico correspondiente al usuario proporcionado, si está
     * inactivo.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Medico consultarMedicoParaAlta(String user) throws PersistenciaClinicaException;

    /**
     * Consulta un médico por su nombre de usuario.
     *
     * @param user El nombre de usuario del médico que se desea consultar.
     * @return El médico correspondiente al usuario proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Medico consultarMedicoPorUsuario(String user) throws PersistenciaClinicaException;

    /**
     * Obtiene los horarios de un médico específico.
     *
     * @param medico El médico del cual se desean obtener los horarios.
     * @return Una lista de horarios del médico proporcionado.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public List<HorarioMedico> obtenerHorariosMedico(Medico medico) throws PersistenciaClinicaException;

    /**
     * Registra un nuevo médico en la base de datos.
     *
     * @param medico El objeto Medico que contiene la información a registrar.
     * @return El médico registrado, con su ID asignado.
     * @throws PersistenciaClinicaException Si ocurre un error durante el
     * registro.
     * @throws SQLException Si ocurre un error relacionado con la base de datos.
     */
    public Medico registrarMedico(Medico medico) throws PersistenciaClinicaException, SQLException;

    /**
     * Consulta un médico por su especialidad, devolviendo solo un médico.
     *
     * @param Especialidad La especialidad por la cual se desea consultar un
     * médico.
     * @return Un médico que coincide con la especialidad proporcionada, o null
     * si no se encuentra.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * consulta.
     */
    public Medico consultarUnMedicoPorEspecialidad(String Especialidad) throws PersistenciaClinicaException;
}

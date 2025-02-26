/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Exception.PersistenciaClinicaException;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con los
 * usuarios.
 *
 * Esta interfaz proporciona métodos para el inicio de sesión de pacientes y
 * médicos.
 *
 * @author Daniel M
 */
public interface IUsuarioDAO {

    /**
     * Verifica las credenciales de inicio de sesión de un paciente.
     *
     * @param correo El correo electrónico del paciente.
     * @param contraseña La contraseña proporcionada por el paciente.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * verificación.
     */
    public boolean loginPaciente(String correo, String contraseña) throws PersistenciaClinicaException;

    /**
     * Verifica las credenciales de inicio de sesión de un médico.
     *
     * @param user El nombre de usuario del médico.
     * @param contrasenia La contraseña proporcionada por el médico.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * verificación.
     */
    public boolean loginMedico(String user, String contrasenia) throws PersistenciaClinicaException;
}

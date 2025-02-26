/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Exception.PersistenciaClinicaException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la interfaz IUsuarioDAO para manejar las operaciones de
 * persistencia relacionadas con los usuarios en la base de datos.
 *
 * Esta clase proporciona métodos para el inicio de sesión de pacientes y
 * médicos.
 *
 * @author Daniel M
 */
public class UsuarioDAO implements IUsuarioDAO {

    IConexionBD conexion; // Interfaz para la conexión a la base de datos

    /**
     * Constructor de la clase UsuarioDAO.
     *
     * @param conexion La conexión a la base de datos que se utilizará para las
     * operaciones.
     */
    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    /**
     * Verifica las credenciales de inicio de sesión de un paciente.
     *
     * Este método compara la contraseña proporcionada con la almacenada en la
     * base de datos utilizando el algoritmo Argon2 para la verificación.
     *
     * @param user El nombre de usuario del paciente.
     * @param contrasenia La contraseña proporcionada por el paciente.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * verificación.
     */
    @Override
    public boolean loginPaciente(String user, String contrasenia) throws PersistenciaClinicaException {
        String hashAlmacenado = null;
        String sentenciaSQL = "SELECT contrasenia FROM usuarios WHERE User = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {
            ps.setString(1, user);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                hashAlmacenado = resultSet.getString("contrasenia");

                Argon2 argon2 = Argon2Factory.create();
                return argon2.verify(hashAlmacenado, contrasenia);
            } else {
                logger.warning("No se encontro el usuario del paciente.");
                return false;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al verificar la contraseña", ex);
            throw new PersistenciaClinicaException("Error al verificar la contraseña", ex);
        }
    }

    /**
     * Verifica las credenciales de inicio de sesión de un médico.
     *
     * Este método compara la contraseña proporcionada con la almacenada en la
     * base de datos sin utilizar un algoritmo de encriptación.
     *
     * @param user El nombre de usuario del médico.
     * @param contrasenia La contraseña proporcionada por el médico.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws PersistenciaClinicaException Si ocurre un error durante la
     * verificación.
     */
    @Override
    public boolean loginMedico(String user, String contrasenia) throws PersistenciaClinicaException {
        String contraseniaAlmacenada = null;
        String sentenciaSQL = "SELECT contrasenia FROM usuarios WHERE User = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {
            ps.setString(1, user);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                contraseniaAlmacenada = resultSet.getString("contrasenia");

                // Comparar directamente sin encriptación
                return contraseniaAlmacenada.equals(contrasenia);
            } else {
                logger.warning("No se encontro el usuario del medico.");
                return false; // Usuario no encontrado
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al verificar la contraseña", ex);
            throw new PersistenciaClinicaException("Error al verificar la contraseña", ex);
        }
    }

}

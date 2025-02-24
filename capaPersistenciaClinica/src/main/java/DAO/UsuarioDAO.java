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
 *
 * @author Daniel M
 */
public class UsuarioDAO implements IUsuarioDAO {

    IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

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

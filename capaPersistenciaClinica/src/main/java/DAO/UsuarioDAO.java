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
    public String login(String User, String contrasenia) throws PersistenciaClinicaException {
        String rol = null;
        String hashAlmacenado = null;
        String sentenciaSQL = "SELECT rol, contrasenia FROM usuarios WHERE User = ?";
        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {
            ps.setString(1, User);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                rol = resultSet.getString("rol");
                hashAlmacenado = resultSet.getString("contrasenia");

                Argon2 argon2 = Argon2Factory.create();
                if (!argon2.verify(hashAlmacenado, contrasenia)) {
                    throw new PersistenciaClinicaException("Contraseña incorrecta.");
                }
            } else {
                throw new PersistenciaClinicaException("Usuario no encontrado.");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al crear activista", ex);
            throw new PersistenciaClinicaException("Error al crear al activista", ex);
        }
        
        return rol;
    }
    
    
}

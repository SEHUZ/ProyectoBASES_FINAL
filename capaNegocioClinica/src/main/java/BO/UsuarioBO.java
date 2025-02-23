/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IUsuarioDAO;
import DAO.UsuarioDAO;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel M
 */
public class UsuarioBO {
    // Atributo logger para mostrar mensajes legibles al depurar.

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    // Declaracion de la DAO para hacer uso de sus metodos.
    private final IUsuarioDAO usuarioDAO;
    
    /**
     * Constructor para inicializar la conexion a la base de datos.
     *
     * @param conexion Representa la conexion que se mandara a la DAO.
     */
    public UsuarioBO(IConexionBD conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
    }
    
    public String login(String user, String contrasenia) throws NegocioException {
        try {
            String rol = usuarioDAO.login(user, contrasenia);
            if(rol == null) {
                return null;
            }
            
            return rol;
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar el rol de usuario", ex);
            throw new NegocioException("Error al recuperar el rol de usuario: " + ex.getMessage());
        }
        
    }
    
}

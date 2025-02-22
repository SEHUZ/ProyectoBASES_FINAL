/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebasPersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ICitaDAO;
import DAO.IPacienteDAO;
import DAO.IUsuarioDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Entidades.DireccionPaciente;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class PruebasPersistencia {
    
    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PersistenciaClinicaException {
        // Suponiendo que existe una implementación de IConexionBD, por ejemplo, ConexionBDImpl
        IConexionBD conexion = new ConexionBD();
        
        // Instanciar el DAO con la conexión
        IPacienteDAO pacienteDAO = new PacienteDAO(conexion);  
        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
        
        try {
            System.out.println(usuarioDAO.login("si", "hola"));
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar el paciente: " + ex);
        }
        
    }
    
    
}

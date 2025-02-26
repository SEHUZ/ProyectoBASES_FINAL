/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebasPersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.CitaDAO;
import Entidades.Cita;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author sonic
 */
public class PruebaCitaE {



    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        Connection connection = null;
        
         try {         
            // Llamar al método para agendar la cita de emergencia
            CitaDAO citaDAO = new CitaDAO(conexion);
            Cita citaAgendada = citaDAO.consultarCitaPorID(7);
             System.out.println(citaAgendada);
            
        } catch (PersistenciaClinicaException e) {
            System.err.println("Error al agendar la cita de emergencia: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}

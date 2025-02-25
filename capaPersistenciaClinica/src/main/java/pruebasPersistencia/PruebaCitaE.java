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
            // Crear un paciente (asegúrate de que el id exista en la base de datos)
            Paciente paciente = new Paciente();
            paciente.setIdPaciente(1); // ID de ejemplo
            
            // Crear la entidad Cita y asignarle el paciente
            Cita cita = new Cita();
            cita.setPaciente(paciente);
            
            
            // Llamar al método para agendar la cita de emergencia
            CitaDAO citaDAO = new CitaDAO(conexion);
            Cita citaAgendada = citaDAO.agendarCitaEmergencia(cita);
            
            // Mostrar la información obtenida
            System.out.println("Cita agendada exitosamente:");
            System.out.println("ID Cita: " + citaAgendada.getIdCita());
            System.out.println("Fecha y Hora de la Cita: " + citaAgendada.getFechaHora());
            if (citaAgendada.getEmergencia() != null) {
                System.out.println("Folio de Emergencia: " + citaAgendada.getEmergencia().getFolio());
                System.out.println("Fecha Expiración: " + citaAgendada.getEmergencia().getFechaExpiracion());
            }
            
        } catch (PersistenciaClinicaException | SQLException e) {
            System.err.println("Error al agendar la cita de emergencia: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}

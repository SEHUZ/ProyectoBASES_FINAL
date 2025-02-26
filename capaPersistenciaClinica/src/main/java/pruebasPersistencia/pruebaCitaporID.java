/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebasPersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.CitaDAO;
import Entidades.Cita;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author sonic
 */
public class pruebaCitaporID {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        Connection connection = null;
        
        try {
            // Instanciar el DAO (ajusta el constructor si es necesario)
            CitaDAO citaDAO = new CitaDAO(conexion);
            
            // Especifica el ID de la cita que deseas consultar (debe existir en la base de datos)
            int idCitaToTest = 1;
            
            // Invocar el método para consultar la cita por ID
            Cita cita = citaDAO.consultarCitaPorID(idCitaToTest);
            
            // Imprimir la información de la cita
            System.out.println("Consulta exitosa para la cita con ID: " + cita.getIdCita());
            System.out.println("Fecha y Hora: " + cita.getFechaHora());
            System.out.println("Estado: " + (cita.getEstado() != null ? cita.getEstado().getDescripcion() : "No definido"));
            
            // Datos del paciente
            if (cita.getPaciente() != null) {
                System.out.println("Paciente: " 
                        + cita.getPaciente().getNombres() + " " 
                        + cita.getPaciente().getApellidoPaterno() + " " 
                        + cita.getPaciente().getApellidoMaterno());
            }
            
            // Datos del médico
            if (cita.getMedico() != null) {
                System.out.println("Médico: " 
                        + cita.getMedico().getNombres() + " " 
                        + cita.getMedico().getApellidoPaterno() + " " 
                        + cita.getMedico().getApellidoMaterno());
            }
            
            // Verificar si es cita de emergencia o normal
            if (cita.getEmergencia() != null) {
                System.out.println("Tipo de cita: Emergencia");
                System.out.println("Folio: " + cita.getEmergencia().getFolio());
                System.out.println("Fecha Expiración: " + cita.getEmergencia().getFechaExpiracion());
            } else if (cita.getNormal() != null) {
                System.out.println("Tipo de cita: Normal");
            } else {
                System.out.println("Tipo de cita: No especificado");
            }
            
        } catch (PersistenciaClinicaException ex) {
            System.err.println("Error al consultar la cita: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}

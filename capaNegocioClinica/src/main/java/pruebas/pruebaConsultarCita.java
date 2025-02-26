/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import BO.CitaBO;
import DTO.CitaViejaDTO;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import configuracion.DependencyInjector;
import java.sql.SQLException;

/**
 *
 * @author sonic
 */
public class pruebaConsultarCita {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NegocioException, PersistenciaClinicaException {
        CitaBO citaBO = DependencyInjector.crearCitaBO();
        try {
        // Instanciar la capa de negocio que contiene el método
            
            // Especificar el ID de la cita a consultar (asegúrate de que exista en la BD)
            int idCitaToTest = 25;
            
            // Invocar el método para consultar la cita por ID
            CitaViejaDTO citaDTO = citaBO.consultarCitaPorsuID(idCitaToTest);
            
            // Imprimir la información obtenida
            System.out.println("Cita encontrada:");
            System.out.println("ID Cita: " + citaDTO.getIdCita());
            System.out.println("Fecha y Hora: " + citaDTO.getFechaHora());
            
            if (citaDTO.getPaciente() != null) {
                System.out.println("Paciente: " 
                    + citaDTO.getPaciente().getNombres() + " " 
                    + citaDTO.getPaciente().getApellidoPaterno());
            }
            
            if (citaDTO.getMedico() != null) {
                System.out.println("Médico: " 
                    + citaDTO.getMedico().getNombres() + " " 
                    + citaDTO.getMedico().getApellidoPaterno());
            }
            
            if (citaDTO.getEstado() != null) {
                System.out.println("Estado: " + citaDTO.getEstado().getDescripcion());
            }
            
            if (citaDTO.getEmergencia() != null) {
                System.out.println("Tipo de Cita: Emergencia");
                System.out.println("Folio: " + citaDTO.getEmergencia().getFolio());
                System.out.println("Fecha Expiración: " + citaDTO.getEmergencia().getFechaExpiracion());
            } else if (citaDTO.getNormal() != null) {
                System.out.println("Tipo de Cita: Normal");
            } else {
                System.out.println("Tipo de Cita: No especificado");
            }
            
        } catch (NegocioException | PersistenciaClinicaException  ex) {
            System.err.println("Error al consultar la cita: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
   

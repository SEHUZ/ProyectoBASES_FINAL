/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import BO.CitaBO;
import BO.ConsultaBO;
import DTO.ConsultaNuevaDTO;
import DTO.ConsultaViejaDTO;
import Entidades.Cita;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import configuracion.DependencyInjector;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class pruebaconsulta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
            // Crear la instancia de la capa de negocio para consultas
            ConsultaBO consultaBO = DependencyInjector.crearConsultaBO();
            
            // Crear el DTO de la nueva consulta y asignar datos de prueba
            ConsultaNuevaDTO nuevaConsulta = new ConsultaNuevaDTO();
            nuevaConsulta.setDiagnostico("Diagnóstico de prueba");
            nuevaConsulta.setTratamiento("Tratamiento de prueba");
            
            nuevaConsulta.setFechaHora(LocalDateTime.now().plusMinutes(20));
            
            // Crear y asignar la cita asociada 
            Cita cita = new Cita();
            cita.setIdCita(1);
            nuevaConsulta.setCita(cita);
            
            // Llamar al método para insertar la consulta
            ConsultaViejaDTO consultaInsertada = consultaBO.insertarConsulta(nuevaConsulta);
            
            // Imprimir los resultados
            System.out.println("Consulta insertada con éxito:");
            System.out.println("ID Consulta: " + consultaInsertada.getIdConsulta());
            System.out.println("Diagnóstico: " + consultaInsertada.getDiagnostico());
            System.out.println("Tratamiento: " + consultaInsertada.getTratamiento());
            System.out.println("Fecha y Hora: " + consultaInsertada.getFechaHora());
            
        } catch (NegocioException | SQLException | PersistenciaClinicaException ex) {
            System.err.println("Error al insertar la consulta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}

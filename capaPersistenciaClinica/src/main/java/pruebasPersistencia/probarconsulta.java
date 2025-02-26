/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebasPersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.ConsultaDAO;
import Entidades.Cita;
import Entidades.Consulta;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class probarconsulta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        Connection connection = null;
        try {
            // Crear una instancia de la clase ConsultaDAO (ajusta el constructor según tu implementación)
            ConsultaDAO consultaDAO = new ConsultaDAO(conexion);

            // Crear una cita de ejemplo (asegúrate que este ID exista en tu BD)
            Cita cita = new Cita();
            cita.setIdCita(25);  // ID de la cita ya existente

            // Crear la consulta de prueba y asignar los datos necesarios
            Consulta consulta = new Consulta();
            consulta.setDiagnostico("Diagnóstico de prueba");
            consulta.setEstado("Agendada"); // O el estado que corresponda
            consulta.setFechaHora(LocalDateTime.now());
            consulta.setTratamiento("Tratamiento de prueba");
            consulta.setCita(cita);

            // Llamar al método insertarConsulta y obtener la consulta insertada
            Consulta consultaInsertada = consultaDAO.insertarConsulta(consulta);

            // Imprimir los resultados en consola
            System.out.println("Consulta insertada exitosamente:");
            System.out.println("ID Consulta: " + consultaInsertada.getIdConsulta());
            System.out.println("Diagnóstico: " + consultaInsertada.getDiagnostico());
            System.out.println("Estado: " + consultaInsertada.getEstado());
            System.out.println("Fecha y Hora: " + consultaInsertada.getFechaHora());
            System.out.println("Tratamiento: " + consultaInsertada.getTratamiento());
            System.out.println("ID Cita Asociada: " + consultaInsertada.getCita().getIdCita());
            
        } catch (PersistenciaClinicaException | SQLException ex) {
            System.err.println("Error al insertar la consulta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

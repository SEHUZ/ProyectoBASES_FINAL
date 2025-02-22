/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebasPersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.CitaDAO;
import Entidades.Cita;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sonic
 */
public class PruebaCitaDAO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        Connection connection = null;
        
        try {
            // Obtener la conexión a la base de datos
            connection = conexion.crearConexion();
            
            // Instanciar el DAO de Citas (se asume que en su constructor recibe una Connection)
            CitaDAO citaDAO = new CitaDAO(conexion);
            
            // Crear un objeto Medico con el ID que se desea consultar
            Medico medico = new Medico();
            medico.setIdMedico(1); // Cambia el ID según un médico existente en la base de datos
            
            // Invocar el método consultarCitasMedico y obtener la lista de citas
            List<Cita> citas = citaDAO.consultarCitasMedico(medico);
            
            // Imprimir el resultado
            System.out.println("Citas para el médico con ID " + medico.getIdMedico() + ":");
            for (Cita cita : citas) {
                System.out.println(cita);
            }
            
        } catch (PersistenciaClinicaException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

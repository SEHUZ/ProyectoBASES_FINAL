/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Jose
 */
import Entidades.Cita;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.util.List;

public interface ICitaDAO {

    // Método para insertar una nueva cita
    boolean insertarCita(Cita cita) throws PersistenciaClinicaException;

    // Método para obtener todas las citas
    List<Cita> obtenerTodasLasCitas() throws PersistenciaClinicaException;
    
    List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException;

    // Método para actualizar el estado de una cita
    boolean actualizarEstadoCita(int idCita, int nuevoEstado) throws PersistenciaClinicaException;

    // Método para eliminar una cita
    boolean eliminarCita(int idCita) throws PersistenciaClinicaException;
    
    
}

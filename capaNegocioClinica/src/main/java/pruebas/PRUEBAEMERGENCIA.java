/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import BO.CitaBO;
import BO.MedicoBO;
import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import Entidades.Cita;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import configuracion.DependencyInjector;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Clase de prueba para agendar una cita de emergencia.
 * Esta clase utiliza el objeto de negocio CitaBO para crear y agendar una cita de emergencia
 * utilizando datos de prueba.
 * 
 * @author sonic
 */
public class PRUEBAEMERGENCIA {

    /**
     * Método principal que ejecuta la prueba de agendar una cita de emergencia.
     *
     * @param args Los argumentos de línea de comandos (no se utilizan en esta prueba).
     */
    public static void main(String[] args) {
        CitaBO citaBO = DependencyInjector.crearCitaBO();
        try {
            
            CitaNuevaDTO nuevaDTO = new CitaNuevaDTO();
            
            Paciente paciente = new Paciente();
            paciente.setIdPaciente(1);
            nuevaDTO.setPaciente(paciente);
            
            Medico medico = new Medico();
            medico.setIdMedico(1);
            nuevaDTO.setMedico(medico);
            

            nuevaDTO.setFechaHora(LocalDateTime.now().plusMinutes(15));

            nuevaDTO.setTipoCita(Cita.TipoCita.EMERGENCIA);
            
            
            CitaViejaDTO citaAgendada = citaBO.agendarCitaEmergencia(nuevaDTO);
            
            System.out.println("Cita de Emergencia Agendada Exitosamente:");
            System.out.println("ID Cita: " + citaAgendada.getIdCita());
            System.out.println("Fecha y Hora: " + citaAgendada.getFechaHora());
            
            if (citaAgendada.getEmergencia() != null) {
                System.out.println("Folio: " + citaAgendada.getEmergencia().getFolio());
                System.out.println("Fecha Expiración: " + citaAgendada.getEmergencia().getFechaExpiracion());
            }
        } catch (NegocioException | PersistenciaClinicaException | SQLException ex) {
            System.err.println("Error al agendar la cita de emergencia: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}

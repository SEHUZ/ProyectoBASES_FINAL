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
import Entidades.CitaEmergencia;
import Entidades.EstadosCita;
import Entidades.Medico;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ICitaDAO {


    public Cita agendarCita(Cita cita) throws PersistenciaClinicaException, SQLException;

    List<Cita> consultarCitasMedico(Medico medico) throws PersistenciaClinicaException;

    public boolean actualizarEstadoCita(int idCita, String nuevoEstado) throws PersistenciaClinicaException;
    
    public EstadosCita consultarEstadoCita(int idCita) throws PersistenciaClinicaException;
    
    public boolean cancelarCita(int idCita) throws PersistenciaClinicaException;
    
    public Cita consultarCitaPorID(int idCita) throws PersistenciaClinicaException;
    
    public boolean insertarEstadoCita(int idCita, String estado) throws PersistenciaClinicaException;
    
    public CitaEmergencia agendarCitaEmergencia(CitaEmergencia emergencia) throws PersistenciaClinicaException;
    
    
}
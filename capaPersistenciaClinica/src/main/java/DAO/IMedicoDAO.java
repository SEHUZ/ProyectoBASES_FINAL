/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.HorarioMedico;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IMedicoDAO {
    public boolean ActualizarEstado (Medico medico) throws PersistenciaClinicaException;
    
    public List<Medico> consultarMedicoPorEspecialidad(String Especialidad) throws PersistenciaClinicaException;

    public Medico consultarMedicoPorID (int IdMedico) throws PersistenciaClinicaException;
    
    public Medico consultarMedicoPorUsuario(String user) throws PersistenciaClinicaException;
    
    public boolean verificarDisponibilidad(int idMedico, LocalDateTime fechaHoraCita) throws PersistenciaClinicaException;
    
    public List<HorarioMedico> obtenerHorariosMedico(Medico medico) throws PersistenciaClinicaException;
    
    
}

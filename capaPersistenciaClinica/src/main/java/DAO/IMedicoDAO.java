/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IMedicoDAO {
    
    public Medico registrarMedico (Medico medico) throws PersistenciaClinicaException, SQLException;
    
    public boolean ActualizarEstado (Medico medico) throws PersistenciaClinicaException;
    
    public Medico consultarMedicoPorEspecialidad (Medico medico) throws PersistenciaClinicaException;

    public Medico consultarMedicoPorID (Medico medico) throws PersistenciaClinicaException;
    
    public Medico consultarMedicoPorUsuario(String user) throws PersistenciaClinicaException;
    
    public boolean verificarDisponibilidad(int idMedico, LocalDateTime fechaHoraCita) throws PersistenciaClinicaException;
}

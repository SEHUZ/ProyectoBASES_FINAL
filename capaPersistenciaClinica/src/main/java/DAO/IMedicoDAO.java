/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IMedicoDAO {
    public boolean DarBajaTemporal (Medico medico) throws PersistenciaClinicaException;
    
    public boolean DarAlta (Medico medico) throws PersistenciaClinicaException;
    
    public List<Cita> ConsultarCitas (Medico medico) throws PersistenciaClinicaException;
    
    public boolean ConsultarHistorialPaciente (Paciente paciente) throws PersistenciaClinicaException;
}

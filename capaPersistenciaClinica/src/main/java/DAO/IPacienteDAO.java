/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Entidades.Paciente;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IPacienteDAO {

    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaClinicaException, SQLException;

    public Paciente consultarPacientePorID(int id) throws PersistenciaClinicaException;
    
    public Paciente actualizarPaciente(Paciente paciente) throws PersistenciaClinicaException;
    
    public Paciente consultarPacientePorCorreo(String correo) throws PersistenciaClinicaException;
    
    public Paciente consultarPacientePorTelefono(String telefono) throws PersistenciaClinicaException;
    
    public Paciente consultarPacientePorUsuario(String User) throws PersistenciaClinicaException;
    
    public List<Cita> obtenerCitasProximasPorPaciente(int idPaciente) throws PersistenciaClinicaException;
}

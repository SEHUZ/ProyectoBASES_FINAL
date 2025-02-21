/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.DireccionPaciente;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;

/**
 *
 * @author sonic
 */
public interface IPacienteDAO {

    public Paciente registrarPaciente(Paciente paciente, Usuario usuario, DireccionPaciente direccion) throws PersistenciaClinicaException, SQLException;

    public Paciente consultarPacientePorID(int id) throws PersistenciaClinicaException;
    
    public Paciente actualizarPaciente(Paciente paciente) throws PersistenciaClinicaException;
    
    
}

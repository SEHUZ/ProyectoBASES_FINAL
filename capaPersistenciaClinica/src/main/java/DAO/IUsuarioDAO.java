/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Exception.PersistenciaClinicaException;

/**
 *
 * @author Daniel M
 */
public interface IUsuarioDAO {
    
    public boolean loginPaciente(String correo, String contraseña) throws PersistenciaClinicaException;
    
    public boolean loginMedico(String user, String contrasenia) throws PersistenciaClinicaException;
    
}

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
    
    public String login(String correo, String contraseña) throws PersistenciaClinicaException;
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Conexion;

import Exception.PersistenciaClinicaException;
import java.sql.Connection;

/**
 * Clase interfaz que crea el metodo para la conexion con la base de datos
 * 
 * @author sonic
 */
public interface IConexionBD {

    public Connection crearConexion() throws PersistenciaClinicaException;

}

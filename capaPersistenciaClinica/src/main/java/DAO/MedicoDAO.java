/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
import Entidades.DireccionPaciente;
import Entidades.EstadoCita;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaClinicaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoDAO implements IMedicoDAO{
    private IConexionBD conexion;
    private Connection connection;  // Agregar esta línea para declarar la conexión

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
        try {
            this.connection = conexion.crearConexion();  // Inicializar la conexión aquí
        } catch (PersistenciaClinicaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean ActualizarEstado(Medico medico) throws PersistenciaClinicaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean ConsultarHistorialPaciente(Paciente paciente) throws PersistenciaClinicaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    


    
}

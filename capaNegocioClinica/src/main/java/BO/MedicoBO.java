/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.IPacienteDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoBO {
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    
    private final IMedicoDAO medicoDAO;
    
    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    // Declaracion de la DAO para hacer uso de sus metodos.
}

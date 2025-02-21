/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.mkammerer.argon2.*;

/**
 *
 * @author sonic XDDDDDD
 */
public class PacienteBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper();

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public boolean registrarPaciente(PacienteNuevoDTO pacienteNuevoDTO) throws NegocioException {
        if(pacienteNuevoDTO == null) {
            throw new NegocioException("El paciente debe tener todos sus datos.");
        }
        
        // seguir validaciones
        
        return false;
    }

    public static String contraseñaHash(String contraseña) {
        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(3, 65536, 1, contraseña);
        argon2.wipeArray(contraseña.toCharArray());
        return hash;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ICitaDAO;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.CitaNuevaDTO;
import Entidades.Cita;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class CitaBO {
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    private final ICitaDAO citaDAO;

    private final PacienteMapper mapper = new PacienteMapper();
    
    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
    }
    
    public boolean insertarCita(CitaNuevaDTO citaNuevaDTO) throws PersistenciaClinicaException, NegocioException {
    }
}

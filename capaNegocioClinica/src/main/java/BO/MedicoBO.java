/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import DTO.MedicoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Medico;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.MedicoMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoBO {
    
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    
    private final IMedicoDAO medicoDAO;
    
    private final MedicoMapper mapper = new MedicoMapper();
    
    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    public MedicoDTO buscarPacientePorUsuario(String user) throws NegocioException {
        try {
            Medico medico = medicoDAO.consultarMedicoPorUsuario(user);
            if (medico == null) {
                return null;
            }
            
            return mapper.toDTO(medico);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar los datos del medico", ex);
            throw new NegocioException("Error al recuperar los datos del medico: " + ex.getMessage());
        }
    }
    
}

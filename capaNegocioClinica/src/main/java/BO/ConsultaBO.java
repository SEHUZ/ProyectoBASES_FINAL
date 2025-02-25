/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ConsultaDAO;
import DAO.ICitaDAO;
import DAO.IConsultaDAO;
import DAO.MedicoDAO;
import DTO.ConsultaNuevaDTO;
import DTO.ConsultaViejaDTO;
import DTO.MedicoNuevoDTO;
import Entidades.Cita;
import Entidades.CitaEmergencia;
import Entidades.Consulta;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import Mappers.ConsultaMapper;

/**
 *
 * @author sonic
 */
public class ConsultaBO {

   private final ICitaDAO citaDAO;
   private final IConsultaDAO consultaDAO;
   private final ConsultaMapper consultaMapper = new ConsultaMapper();

    public ConsultaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
        this.consultaDAO = new ConsultaDAO(conexion);  
    }


    
    public ConsultaViejaDTO insertarConsulta(ConsultaNuevaDTO consultanuevaDTO) throws SQLException, NegocioException, PersistenciaClinicaException {
        try {
            if (consultanuevaDTO.getFechaHora() == null || consultanuevaDTO.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("La fecha y hora de la consulta son inválidas.");
            }
            
            if (consultanuevaDTO == null) {
                throw new NegocioException("Los datos de la cita son requeridos");
            }

            Consulta consulta = consultaMapper.toEntityNuevo(consultanuevaDTO);
            
            if (consulta.getCita() == null || consulta.getCita().getIdCita() <= 0) {
                throw new NegocioException("La cita asociada a la consulta es inválida.");
            }
            
            if (citaDAO.consultarCitaPorID(consulta.getCita().getIdCita()) == null){
                throw new NegocioException("La cita asociada no existe");
            }
            

            Consulta consultaInsertada = consultaDAO.insertarConsulta(consulta);
            ConsultaViejaDTO consultaViejaDTO = consultaMapper.toViejoDTO(consultaInsertada);

            return consultaViejaDTO;

        }catch (SQLException e) {
            throw new NegocioException("Error al insertar consulta: " + e.getMessage(), e);
    }
}
}


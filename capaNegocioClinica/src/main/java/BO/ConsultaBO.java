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
import Entidades.Consulta;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.CitaMapper;
import Mappers.ConsultaMapper;
import Mappers.HorarioMapper;
import Mappers.MedicoMapper;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class ConsultaBO {

   
   //private final IConsultaDAO consultaDAO;

    // Se pasa el parámetro correctamente y se inicializa la propiedad
   //// public ConsultaBO(IConsultaDAO consultaDAO) {
       // this.consultaDAO = consultaDAO;  // Inicializas la propiedad correctamente
   // }
        private final IConsultaDAO consultaDAO;

    // Modificamos el constructor para recibir IConexionBD en lugar de IConsultaDAO
    public ConsultaBO(IConexionBD conexionBD) {
        // Creamos la instancia de ConsultaDAO dentro del BO, utilizando la conexión proporcionada
        this.consultaDAO = new ConsultaDAO(conexionBD);
    }


    public Consulta registrarConsulta(Consulta consulta) throws PersistenciaClinicaException {
        try {
            return consultaDAO.insertarConsulta(consulta);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al registrar consulta", e);
        }
    }
    
    public List<Consulta> obtenerConsultasPorPaciente(int idPaciente) throws PersistenciaClinicaException {
        try {
            return consultaDAO.obtenerConsultasPorPaciente(idPaciente);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consultas del paciente", e);
        }
    }
    
    public boolean actualizarConsulta(Consulta consulta) throws PersistenciaClinicaException {
        try {
            return consultaDAO.actualizarConsulta(consulta);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al actualizar consulta", e);
        }
    }
    
    public boolean cancelarConsulta(int idConsulta) throws PersistenciaClinicaException {
        try {
            return consultaDAO.cancelarConsulta(idConsulta);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al cancelar consulta", e);
        }
    }
    
    public Consulta obtenerConsultaPorId(int idConsulta) throws PersistenciaClinicaException {
        try {
            return consultaDAO.obtenerConsultaPorId(idConsulta);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consulta por ID", e);
        }
    }
    
    public List<Consulta> obtenerConsultasPorEspecialidadYFechas(int idPaciente, String especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaClinicaException {
        try {
            return consultaDAO.obtenerConsultasPorEspecialidadYFechas(idPaciente, especialidad, fechaInicio, fechaFin);
        } catch (SQLException e) {
            throw new PersistenciaClinicaException("Error al obtener consultas por especialidad y fechas", e);
        }
    }
    
    public boolean existeConsultaParaCita(int idCita) throws PersistenciaClinicaException {
        try {
            return consultaDAO.existeConsultaParaCita(idCita);
        } catch (Exception e) {
            throw new PersistenciaClinicaException("Error al verificar existencia de consulta", e);
        }
    }
}






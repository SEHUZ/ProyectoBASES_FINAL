/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ICitaDAO;
import DAO.IMedicoDAO;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import Entidades.Cita;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.CitaMapper;
import Mappers.PacienteMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class CitaBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    private final ICitaDAO citaDAO;
    private final IPacienteDAO pacienteDAO;
    private final IMedicoDAO medicoDAO;

    private final CitaMapper mapper = new CitaMapper();

    public CitaBO(ICitaDAO citaDAO, IPacienteDAO pacienteDAO, IMedicoDAO medicoDAO) {
        this.citaDAO = citaDAO;
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
    }

    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
        this.pacienteDAO = null;
        this.medicoDAO = null;
    }

    public CitaViejaDTO agendarCita(CitaNuevaDTO citaNuevaDTO) throws NegocioException {
        try {
            if (citaNuevaDTO.getFechaHora() == null || citaNuevaDTO.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("Fecha y hora inválidas");

            }
            if (citaNuevaDTO.getFechaHora() == null || citaNuevaDTO.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new NegocioException("Fecha y hora inválidas");
            }

            if (citaNuevaDTO.getTipoCita() == null || (!citaNuevaDTO.getTipoCita().equalsIgnoreCase("EMERGENCIA") && !citaNuevaDTO.getTipoCita().equalsIgnoreCase("PROGRAMADA"))) {
                throw new NegocioException("Tipo de cita inválido");
            }
            
            Cita cita = mapper.toEntityNuevo(citaNuevaDTO);
            
            if(!medicoDAO.verificarDisponibilidad(cita.getMedico().getIdMedico(), cita.getFechaHora())) {
                throw new NegocioException("El médico no está disponible en ese horario");
            }
            if (pacienteDAO.consultarPacientePorID(cita.getPaciente().getIdPaciente()) == null) {
                throw new NegocioException("Paciente no registrado");
            }
            
             Cita citaNueva = citaDAO.insertarCita(cita);
             
             return mapper.toViejoDTO(citaNueva);
            
        }catch (PersistenciaClinicaException | IllegalArgumentException e) {
            throw new NegocioException("Error al agendar cita: " + e.getMessage());
        }
    }
    
    public void cancelarCita(int idCita) throws NegocioException {
        try {
            Cita cita = citaDAO.consultarCitaPorID(idCita);
            
            // Validar que no esté ya cancelada
            if (cita.getEstado().getDescripcion().equalsIgnoreCase("Cancelada")) {
                throw new NegocioException("La cita ya está cancelada");
            }
            
            // Validar tiempo mínimo de cancelación (24 horas antes)
            if (LocalDateTime.now().isAfter(cita.getFechaHora().minusHours(24))) {
                throw new NegocioException("No se puede cancelar con menos de 24 horas de anticipación");
            }
            
            // Ejecutar cancelación
            if (!citaDAO.cancelarCita(idCita)) {
                throw new NegocioException("Error al procesar la cancelación");
            }
            
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error técnico al cancelar: " + ex.getMessage());
        }
    }
    
    private void validarDisponibilidadMedico(Cita cita) throws NegocioException {
        try {
            if (!medicoDAO.verificarDisponibilidad(
                cita.getMedico().getIdMedico(), 
                cita.getFechaHora())) {
                throw new NegocioException("El médico no está disponible en ese horario");
            }
        } catch (PersistenciaClinicaException e) {
            throw new NegocioException("Error al validar disponibilidad médica");
        }
    }
    
}

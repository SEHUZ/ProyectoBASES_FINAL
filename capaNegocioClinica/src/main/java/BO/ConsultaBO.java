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
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class ConsultaBO {

     private static final Logger logger = Logger.getLogger(ConsultaBO.class.getName());

    private final IConsultaDAO consultaDAO;
    private final ICitaDAO citaDAO;
    private final ConsultaMapper consultaMapper = new ConsultaMapper();
    private final CitaMapper citaMapper = new CitaMapper();

    public ConsultaBO(IConexionBD conexion) {
        this.consultaDAO = new ConsultaDAO(conexion);
        this.citaDAO = new CitaDAO(conexion);
    }

  //  public ConsultaViejaDTO registrarConsulta(ConsultaNuevaDTO consultaDTO) throws NegocioException {
       // try {
            // Validaciones básicas
           // if (consultaDTO.getDiagnostico() == null || consultaDTO.getDiagnostico().isBlank()) {
            //    throw new NegocioException("El diagnóstico es obligatorio");
            }
          //  if (consultaDTO.getCita() == null || consultaDTO.getCita().getIdCita() == 0) {
              //  throw new NegocioException("Debe especificar una cita válida");
           // }
            
            // Validar existencia de la cita
          //  Cita cita = citaDAO.consultarCitaPorID(consultaDTO.getCita().getIdCita());
          //  if (cita == null) {
               // throw new NegocioException("La cita no existe");
           // }
            
            // Verificar que la cita esté en estado "Atendida"
           // if (!"Atendida".equalsIgnoreCase(cita.getEstado().getDescripcion())) {
                // throw new NegocioException("La cita debe estar en estado 'Atendida'");
           //  }
            
            // Verificar que no exista una consulta previa para la cita
            // if (consultaDAO.existeConsultaParaCita(cita.getIdCita())) {
             //    throw new NegocioException("Ya existe una consulta para esta cita");
            // }
            
            // Validar coherencia de fechas
             //if (consultaDTO.getFechaHora().isAfter(LocalDateTime.now())) {
               //  throw new NegocioException("La fecha de la consulta no puede ser futura");
           //  }
            // if (consultaDTO.getFechaHora().isBefore(cita.getFechaHora())) {
              //   throw new NegocioException("La fecha de la consulta no puede ser anterior a la cita");
            // }
            
            // Convertir DTO a entidad
          //  Consulta consulta = consultaMapper.toEntity(consultaDTO);
          //  Consulta consultaRegistrada = consultaDAO.insertarConsulta(consulta);
            
            // Actualizar el estado de la cita a "Completada"
          //  cita.setEstado(new Estado("Completada"));
          //   citaDAO.actualizarCita(cita);
            
           // return consultaMapper.toViejoDTO(consultaRegistrada);
       // } catch (PersistenciaClinicaException | SQLException e) {
         //   logger.log(Level.SEVERE, "Error al registrar consulta", e);
           // throw new NegocioException("Error al registrar la consulta: " + e.getMessage());
       //  }
    // }
    
   // public List<ConsultaViejaDTO> consultarConsultasPorMedico(MedicoDTO medicoDTO) throws NegocioException {
       // try {
           // if (medicoDTO == null || medicoDTO.getIdMedico() == 0) {
              //  throw new NegocioException("Datos del médico inválidos");
           // }
            
           // return consultas.stream().map(consultaMapper::toViejoDTO).collect(Collectors.toList());
       // } catch (PersistenciaClinicaException e) {
           // logger.log(Level.SEVERE, "Error al consultar consultas del médico", e);
           // throw new NegocioException("Error al obtener las consultas: " + e.getMessage());
       //  }
   // }

    
    
 //}

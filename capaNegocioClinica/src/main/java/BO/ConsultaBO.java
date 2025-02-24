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
import DTO.MedicoNuevoDTO;
import Entidades.Cita;
import Entidades.Consulta;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
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

    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());

    private final IConsultaDAO consultaDAO;
    private final ICitaDAO citaDAO;

    //private final ConsultaMapper horariomapper = new HorarioMapper();
    public ConsultaBO(IConexionBD conexion) {
        this.consultaDAO = new ConsultaDAO(conexion);
        this.citaDAO = new CitaDAO(conexion);
    }

    //public boolean registrarConsulta(ConsultaNuevaDTO consultaDTO) throws NegocioException, SQLException, PersistenciaClinicaException {
//        try {
//             1. Validar campos obligatorios
//            if (consultaDTO.getDiagnostico() == null || consultaDTO.getDiagnostico().isBlank()
//                    || consultaDTO.getEstado() == null || consultaDTO.getCita().getIdCita() == 0) {
//                throw new NegocioException("Campos requeridos incompletos");
//            }
//
//             2. Convertir DTO a entidad
//            Consulta consulta = mapper.toEntity(consultaDTO);
//
//             3. Validar cita relacionada
//            Cita cita = citaDAO.consultarCitaPorID(consultaDTO.getCita().getIdCita());
//            if (cita == null) {
//                throw new NegocioException("La cita no existe");
//            }
//
//             4. Validar estado de la cita (debe estar en estado "Atendida")
//            if (!cita.getEstado().getDescripcion().equals("Atendida")) {
//                throw new NegocioException("La cita no está en estado válido para generar consulta");
//            }
//
//             5. Validar que no exista consulta previa para esta cita
//            if (consultaDAO.existeConsultaParaCita(consultaDTO.getIdCita())) {
//                throw new NegocioException("Ya existe una consulta registrada para esta cita");
//            }
//
//             6. Validar fecha de consulta
//            if (consulta.getFechaHora().isAfter(LocalDateTime.now())) {
//                throw new NegocioException("Fecha de consulta no puede ser futura");
//            }
//
//             7. Validar coherencia con fecha de la cita
//            if (consulta.getFechaHora().isBefore(cita.getFechaHora())) {
//                throw new NegocioException("Fecha de consulta no puede ser anterior a la cita");
//            }
//
//             8. Insertar consulta
//            Consulta nuevaConsulta = consultaDAO.insertarConsulta(consulta);
//
//             9. Actualizar estado de la cita si es necesario
//            cita.setEstado(obtenerEstado("Completada"));
//            citaDAO.actualizarCita(cita);
//
//            return mapper.toDTO(nuevaConsulta);
//
//        } catch (PersistenciaClinicaException | SQLException e) {
//            throw new NegocioException("Error al crear consulta: " + e.getMessage());
//        }
//    }
// }
    
    
}

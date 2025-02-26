/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.ConsultaNuevaDTO;
import DTO.ConsultaViejaDTO;
import Entidades.Cita;
import Entidades.Consulta;
import Entidades.HorarioMedico;
import java.time.LocalDateTime;

/**
 * Clase que se encarga de mapear entre objetos de transferencia de datos (DTO) y entidades
 * relacionadas con consultas. Proporciona métodos para convertir entre ConsultaNuevaDTO,
 * ConsultaViejaDTO y la entidad Consulta.
 * 
 * @author sonic
 */
public class ConsultaMapper {
    
    /**
     * Convierte un objeto ConsultaNuevaDTO a una entidad Consulta.
     *
     * @param dto Objeto ConsultaNuevaDTO que se desea convertir.
     * @return Entidad Consulta correspondiente al DTO, o null si el DTO es null.
     */
   
    public Consulta toEntityNuevo(ConsultaNuevaDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Consulta(
            dto.getCita(),
            dto.getDiagnostico(),
            dto.getEstado(),
            dto.getFechaHora(),
            dto.getTratamiento()
        );
    }
    
    /**
     * Convierte una entidad Consulta a un objeto ConsultaViejaDTO.
     *
     * @param consulta Entidad Consulta que se desea convertir.
     * @return Objeto ConsultaViejaDTO correspondiente a la entidad, o null si la entidad es null.
     */

    public ConsultaViejaDTO toViejoDTO(Consulta consulta) {
        if (consulta == null) {
            return null;
        }
        return new ConsultaViejaDTO(
            consulta.getIdConsulta(),
            consulta.getCita(),
            consulta.getDiagnostico(),
            consulta.getEstado(),
            consulta.getFechaHora(),
            consulta.getTratamiento()
        );
    }
}

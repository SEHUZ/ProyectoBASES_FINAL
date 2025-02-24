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
 *
 * @author sonic
 */
public class ConsultaMapper {
   
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

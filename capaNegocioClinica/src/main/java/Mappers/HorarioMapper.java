/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.HorarioMedicoNuevoDTO;
import DTO.HorarioMedicoViejoDTO;
import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.HorarioMedico;
import Entidades.Paciente;

/**
 * Clase que se encarga de mapear entre objetos de transferencia de datos (DTO) y entidades
 * relacionadas con el horario de los médicos. Proporciona métodos para convertir entre
 * HorarioMedicoNuevoDTO, HorarioMedicoViejoDTO y la entidad HorarioMedico.
 * 
 * @author sonic
 */
public class HorarioMapper {
    
    /**
     * Convierte un objeto HorarioMedicoNuevoDTO a una entidad HorarioMedico.
     *
     * @param dto Objeto HorarioMedicoNuevoDTO que se desea convertir.
     * @return Entidad HorarioMedico correspondiente al DTO, o null si el DTO es null.
     */
    public HorarioMedico toEntityNuevo(HorarioMedicoNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new HorarioMedico(
            dto.getMedico(),
            dto.getHoraEntrada(),
            dto.getHoraSalida(),
            dto.getDiaSemana()
        );
    }
    
    /**
     * Convierte una entidad HorarioMedico a un objeto HorarioMedicoViejoDTO.
     *
     * @param horarioMedico Entidad HorarioMedico que se desea convertir.
     * @return Objeto HorarioMedicoViejoDTO correspondiente a la entidad, o null si la entidad es null.
     */

    // Convertir de Paciente a PacienteViejoDTO
    public HorarioMedicoViejoDTO toViejoDTO(HorarioMedico horarioMedico) {
        if (horarioMedico == null) {
            return null;
        }
        HorarioMedicoViejoDTO dto = new HorarioMedicoViejoDTO();
        dto.setMedico(horarioMedico.getMedico());
        dto.setHoraEntrada(horarioMedico.getHoraEntrada());
        dto.setHoraSalida(horarioMedico.getHoraSalida());
        dto.setDiaSemana(horarioMedico.getDiaSemana());
        
        return dto;
    }
    
    /**
     * Convierte un objeto HorarioMedicoViejoDTO a una entidad HorarioMedico.
     *
     * @param dto Objeto HorarioMedicoViejoDTO que se desea convertir.
     * @return Entidad HorarioMedico correspondiente al DTO, o null si el DTO es null.
     */

    // Convertir de PacienteViejoDTO a Paciente
    public HorarioMedico toEntityViejo(HorarioMedicoViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new HorarioMedico(
            dto.getIdHorario(),
            dto.getMedico(),
            dto.getHoraEntrada(),
            dto.getHoraSalida(),
            dto.getDiaSemana()
            
        );
    }
    
    /**
     * Convierte una entidad HorarioMedico a un objeto HorarioMedicoNuevoDTO.
     *
     * @param horarioMedico Entidad HorarioMedico que se desea convertir.
     * @return Objeto HorarioMedicoNuevoDTO correspondiente a la entidad, o null si la entidad es null.
     */
    
    public HorarioMedicoNuevoDTO toDTO(HorarioMedico horarioMedico) {
        if (horarioMedico == null) {
            return null;
        }
        return new HorarioMedicoNuevoDTO(
                horarioMedico.getMedico(),
                horarioMedico.getHoraEntrada(),
                horarioMedico.getHoraSalida(),
                horarioMedico.getDiaSemana()

        );
    }
}

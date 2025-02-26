/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Cita;
import Entidades.Paciente;

/**
 * Clase que se encarga de mapear entre objetos de transferencia de datos (DTO) y entidades.
 * Proporciona métodos para convertir entre CitaNuevaDTO, CitaViejaDTO y la entidad Cita.
 * 
 * @author sonic
 */
public class CitaMapper {
    
    /**
     * Convierte un objeto CitaNuevaDTO a una entidad Cita.
     *
     * @param dto Objeto CitaNuevaDTO que se desea convertir.
     * @return Entidad Cita correspondiente al DTO, o null si el DTO es null.
     */

    public Cita toEntityNuevo(CitaNuevaDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Cita(
                dto.getPaciente(),
                dto.getMedico(),
                dto.getEstado(),
                dto.getFechaHora(),
                dto.getTipoCita()
        );
    }
    
    /**
     * Convierte una entidad Cita a un objeto CitaViejaDTO.
     *
     * @param cita Entidad Cita que se desea convertir.
     * @return Objeto CitaViejaDTO correspondiente a la entidad, o null si la entidad es null.
     */

    // Convertir de Paciente a PacienteViejoDTO
    public CitaViejaDTO toViejoDTO(Cita cita) {
        if (cita == null) {
            return null;
        }
        CitaViejaDTO dto = new CitaViejaDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setPaciente(cita.getPaciente());
        dto.setMedico(cita.getMedico());
        dto.setEstado(cita.getEstado());
        dto.setFechaHora(cita.getFechaHora());
        dto.setTipoCita(cita.getTipoCita());
        dto.setAuditorias(cita.getAuditorias());
        dto.setEmergencia(cita.getEmergencia());
        dto.setNormal(cita.getNormal());
        dto.setConsulta(cita.getConsulta());

        return dto;
    }
    
    /**
     * Convierte un objeto CitaViejaDTO a una entidad Cita.
     *
     * @param dto Objeto CitaViejaDTO que se desea convertir.
     * @return Entidad Cita correspondiente al DTO, o null si el DTO es null.
     */

    // Convertir de PacienteViejoDTO a Paciente
    public Cita toEntityViejo(CitaViejaDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Cita(
                dto.getIdCita(),
                dto.getPaciente(),
                dto.getMedico(),
                dto.getEstado(),
                dto.getFechaHora(),
                dto.getTipoCita()
        );
    }

}

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
 *
 * @author sonic
 */
public class CitaMapper {
    
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
    
    // Convertir de Paciente a PacienteViejoDTO
    public CitaViejaDTO toViejoDTO(Cita cita) {
        if (cita == null) {
            return null;
        }
        CitaViejaDTO dto = new CitaViejaDTO();
        dto.setMedico(cita.getMedico());
        dto.setPaciente(cita.getPaciente());
        dto.setEstado(cita.getEstado());
        dto.setIdCita(cita.getIdCita());
        dto.setFechaHora(cita.getFechaHora());
        return dto;
    }

    // Convertir de PacienteViejoDTO a Paciente
    public Cita toEntityViejo(CitaViejaDTO dto) {
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
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;

/**
 * Clase que se encarga de mapear entre objetos de transferencia de datos (DTO) y entidades
 * relacionadas con pacientes. Proporciona métodos para convertir entre PacienteNuevoDTO,
 * PacienteViejoDTO y la entidad Paciente.
 * 
 * @author sonic
 */
public class PacienteMapper {
    
    /**
     * Convierte un objeto PacienteNuevoDTO a una entidad Paciente.
     *
     * @param dto Objeto PacienteNuevoDTO que se desea convertir.
     * @return Entidad Paciente correspondiente al DTO, o null si el DTO es null.
     */

    // Convertir de PacienteNuevoDTO a Paciente
    public Paciente toEntityNuevo(PacienteNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Paciente(
            dto.getDireccion(),
            dto.getUsuario(),
            dto.getNombre(),
            dto.getApellidoPaterno(),
            dto.getApellidoMaterno(),
            dto.getFechaNacimiento(),
            dto.getEmail(),
            dto.getTelefono()
        );
    }
    
     /**
     * Convierte una entidad Paciente a un objeto PacienteViejoDTO.
     *
     * @param paciente Entidad Paciente que se desea convertir.
     * @return Objeto PacienteViejoDTO correspondiente a la entidad, o null si la entidad es null.
     */

    // Convertir de Paciente a PacienteViejoDTO
    public PacienteViejoDTO toViejoDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteViejoDTO dto = new PacienteViejoDTO();
        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setDireccion(paciente.getDireccion());
        dto.setUsuario(paciente.getUsuario());
        dto.setNombre(paciente.getNombres());
        dto.setApellidoPaterno(paciente.getApellidoPaterno());
        dto.setApellidoMaterno(paciente.getApellidoMaterno());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setEmail(paciente.getEmail());
        dto.setTelefono(paciente.getTelefono());
        return dto;
    }
    
    /**
     * Convierte un objeto PacienteViejoDTO a una entidad Paciente.
     *
     * @param dto Objeto PacienteViejoDTO que se desea convertir.
     * @return Entidad Paciente correspondiente al DTO, o null si el DTO es null.
     */

    // Convertir de PacienteViejoDTO a Paciente
    public Paciente toEntityViejo(PacienteViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Paciente(
            dto.getIdPaciente(),
            dto.getDireccion(),
            dto.getUsuario(),
            dto.getNombres(),
            dto.getApellidoPaterno(),
            dto.getApellidoMaterno(),
            dto.getFechaNacimiento(),
            dto.getEmail(),
            dto.getTelefono()
        );
    }
}

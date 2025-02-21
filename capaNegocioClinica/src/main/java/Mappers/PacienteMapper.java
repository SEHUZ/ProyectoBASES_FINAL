/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;

/**
 *
 * @author sonic
 */
public class PacienteMapper {

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

    // Convertir de PacienteViejoDTO a Paciente
    public Paciente toEntityViejo(PacienteViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Paciente(
            dto.getIdPaciente(),
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.MedicoDTO;
import DTO.MedicoNuevoDTO;
import Entidades.Medico;

/**
 *
 * @author Daniel M
 */
public class MedicoMapper {

    // Entidad medico a MedicoDTO
    public MedicoDTO toDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return new MedicoDTO(
                medico.getIdMedico(),
                medico.getUsuario(),
                medico.getNombres(),
                medico.getApellidoPaterno(),
                medico.getApellidoMaterno(),
                medico.getCedula(),
                medico.getEspecialidad(),
                medico.isActivo()
        );
    }

    public Medico toEntity(MedicoNuevoDTO medicoNuevoDTO) {
        if (medicoNuevoDTO == null) {
            return null;
        }
        return new Medico(
                medicoNuevoDTO.getUsuario(),
                medicoNuevoDTO.getNombres(),
                medicoNuevoDTO.getApellidoPaterno(),
                medicoNuevoDTO.getApellidoMaterno(),
                medicoNuevoDTO.getCedula(),
                medicoNuevoDTO.getEspecialidad(),
                medicoNuevoDTO.isActivo()
        );
    }
    
}

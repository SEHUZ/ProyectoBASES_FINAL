/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.MedicoDTO;
import Entidades.Medico;

/**
 *
 * @author Daniel M
 */
public class MedicoMapper {

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

}

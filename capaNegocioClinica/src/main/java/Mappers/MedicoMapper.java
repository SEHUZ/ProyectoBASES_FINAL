/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.MedicoDTO;
import DTO.MedicoNuevoDTO;
import Entidades.Medico;

/**
 * Clase que se encarga de mapear entre objetos de transferencia de datos (DTO) y entidades
 * relacionadas con médicos. Proporciona métodos para convertir entre MedicoDTO,
 * MedicoNuevoDTO y la entidad Medico.
 * 
 * @author Daniel M
 */
public class MedicoMapper {
    
    /**
     * Convierte una entidad Medico a un objeto MedicoDTO.
     *
     * @param medico Entidad Medico que se desea convertir.
     * @return Objeto MedicoDTO correspondiente a la entidad, o null si la entidad es null.
     */
    

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
    
    /**
     * Convierte un objeto MedicoNuevoDTO a una entidad Medico.
     *
     * @param medicoNuevoDTO Objeto MedicoNuevoDTO que se desea convertir.
     * @return Entidad Medico correspondiente al DTO, o null si el DTO es null.
     */

    public Medico toEntityNuevo(MedicoNuevoDTO medicoNuevoDTO) {
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
    
    /**
     * Convierte un objeto MedicoDTO a una entidad Medico.
     *
     * @param medicoDTO Objeto MedicoDTO que se desea convertir.
     * @return Entidad Medico correspondiente al DTO, o null si el DTO es null.
     */
        
    public Medico toEntityDTO(MedicoDTO medicoDTO) {
        if (medicoDTO == null) {
            return null;
        }
        return new Medico(
                medicoDTO.getIdMedico(),
                medicoDTO.getUsuario(),
                medicoDTO.getNombres(),
                medicoDTO.getApellidoPaterno(),
                medicoDTO.getApellidoMaterno(),
                medicoDTO.getCedula(),
                medicoDTO.getEspecialidad(),
                medicoDTO.isActivo()
        );
    }

}

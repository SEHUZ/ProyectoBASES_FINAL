/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.HorarioMedicoNuevoDTO;
import Entidades.HorarioMedico;

/**
 *
 * @author sonic
 */
public class ConsultaMapper {
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
}

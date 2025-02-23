///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Mappers;
//
//import DTO.HorarioMedicoNuevoDTO;
//import DTO.HorarioMedicoViejoDTO;
//import DTO.PacienteNuevoDTO;
//import DTO.PacienteViejoDTO;
//import Entidades.HorarioMedico;
//import Entidades.Paciente;
//
///**
// *
// * @author sonic
// */
//public class HorarioMapper {
//    public HorarioMedico toEntityNuevo(HorarioMedicoNuevoDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//        return new HorarioMedico(
//            dto.getMedico(),
//            dto.getHoraEntrada(),
//            dto.getHoraSalida(),
//            dto.getDiaSemana()
//        );
//    }
//
//    // Convertir de Paciente a PacienteViejoDTO
//    public HorarioMedicoViejoDTO toViejoDTO(HorarioMedico horarioMedico) {
//        if (horarioMedico == null) {
//            return null;
//        }
//        HorarioMedicoViejoDTO dto = new HorarioMedicoViejoDTO();
//        dto.setMedico(horarioMedico.getMedico());
//        dto.setHoraEntrada(horarioMedico.getHoraEntrada());
//        dto.setHoraSalida(horarioMedico.getHoraSalida());
//        dto.setDiaSemana(horarioMedico.getDiaSemana());
//        
//        return dto;
//    }
//
//    // Convertir de PacienteViejoDTO a Paciente
//    public HorarioMedico toEntityViejo(HorarioMedicoViejoDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//        return new HorarioMedico(
//            dto.getIdHorario(),
//            dto.getMedico(),
//            dto.getHoraEntrada(),
//            dto.getHoraSalida(),
//            dto.getDiaSemana()
//            
//        );
//    }
//    
//    public HorarioMedicoNuevoDTO toDTO(HorarioMedico horarioMedico) {
//        if (horarioMedico == null) {
//            return null;
//        }
//        return new HorarioMedicoNuevoDTO(
//                horarioMedico.getMedico(),
//                horarioMedico.getHoraEntrada(),
//                horarioMedico.getHoraSalida(),
//                horarioMedico.getDiaSemana()
//
//        );
//    }
//}

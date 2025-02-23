/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Medico;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 *
 * @author sonic
 */
public class HorarioMedicoViejoDTO {
    private int idHorario;
    private Medico medico;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private DayOfWeek diaSemana;

    public HorarioMedicoViejoDTO(int idHorario, Medico medico, LocalTime horaEntrada, LocalTime horaSalida, DayOfWeek diaSemana) {
        this.idHorario = idHorario;
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diaSemana = diaSemana;
    }

    public HorarioMedicoViejoDTO(Medico medico, LocalTime horaEntrada, LocalTime horaSalida, DayOfWeek diaSemana) {
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diaSemana = diaSemana;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public HorarioMedicoViejoDTO() {
    }
    
    

    @Override
    public String toString() {
        return "HorarioMedicoViejoDTO{" + "idHorario=" + idHorario + ", medico=" + medico + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", diaSemana=" + diaSemana + '}';
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Medico;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para el horario de un médico.
 * Contiene información sobre el médico, la hora de entrada, la hora de salida y el día de la semana
 * en el que el médico está disponible.
 * 
 * @author sonic
 */
public class HorarioMedicoNuevoDTO {
    private Medico medico;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private DayOfWeek diaSemana;
    
    /**
     * Constructor que inicializa todos los atributos del horario del médico.
     *
     * @param medico Médico asociado al horario.
     * @param horaEntrada Hora de entrada del médico.
     * @param horaSalida Hora de salida del médico.
     * @param diaSemana Día de la semana en el que el médico está disponible.
     */

    public HorarioMedicoNuevoDTO(Medico medico, LocalTime horaEntrada, LocalTime horaSalida, DayOfWeek diaSemana) {
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diaSemana = diaSemana;
    }

    public HorarioMedicoNuevoDTO() {
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

    @Override
    public String toString() {
        return "HorarioMedicoNuevoDTO{" + "medico=" + medico + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", diaSemana=" + diaSemana + '}';
    }
    
    
    
}

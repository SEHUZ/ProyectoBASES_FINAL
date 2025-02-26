/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Clase que representa el horario de un médico.
 *
 * Esta clase almacena información sobre el horario de trabajo de un médico,
 * incluyendo la hora de entrada, la hora de salida y el día de la semana.
 *
 * @author sonic
 */
public class HorarioMedico {

    private int idHorario;          // Identificador único del horario
    private Medico medico;          // Médico asociado a este horario
    private LocalTime horaEntrada;   // Hora de entrada del médico
    private LocalTime horaSalida;    // Hora de salida del médico
    private DayOfWeek diaSemana;     // Día de la semana en que se aplica este horario

    /**
     * Constructor de la clase HorarioMedico con todos los parámetros.
     *
     * @param idHorario Identificador único del horario.
     * @param medico El médico asociado a este horario.
     * @param horaEntrada La hora de entrada del médico.
     * @param horaSalida La hora de salida del médico.
     * @param diaSemana El día de la semana en que se aplica este horario.
     */
    public HorarioMedico(int idHorario, Medico medico, LocalTime horaEntrada, LocalTime horaSalida, DayOfWeek diaSemana) {
        this.idHorario = idHorario;
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diaSemana = diaSemana;
    }

    /**
     * Constructor de la clase HorarioMedico sin idHorario.
     *
     * @param medico El médico asociado a este horario.
     * @param horaEntrada La hora de entrada del médico.
     * @param horaSalida La hora de salida del médico.
     * @param diaSemana El día de la semana en que se aplica este horario.
     */
    public HorarioMedico(Medico medico, LocalTime horaEntrada, LocalTime horaSalida, DayOfWeek diaSemana) {
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diaSemana = diaSemana;
    }

    /**
     * Constructor por defecto de la clase HorarioMedico.
     */
    public HorarioMedico() {
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return "HorarioMedico{" + "idHorario=" + idHorario + ", medico=" + medico + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", diaSemana=" + diaSemana + '}';
    }

}

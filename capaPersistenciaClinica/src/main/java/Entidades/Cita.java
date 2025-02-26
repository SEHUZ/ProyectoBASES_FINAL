/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cita médica.
 *
 * Esta clase almacena información sobre la cita, incluyendo el paciente, el
 * médico, el estado de la cita, la fecha y hora, el tipo de cita, y auditorías
 * relacionadas.
 *
 * @author sonic
 */
public class Cita {

    private int idCita;                // Identificador único de la cita
    private Paciente paciente;          // Paciente asociado a la cita
    private Medico medico;              // Médico asociado a la cita
    private EstadosCita estado;         // Estado actual de la cita
    private LocalDateTime fechaHora;    // Fecha y hora de la cita
    private TipoCita tipoCita;          // Tipo de cita (emergencia o programada)
    private List<Auditoria> auditorias; // Lista de auditorías relacionadas con la cita
    private CitaEmergencia emergencia;  // Información específica de una cita de emergencia
    private CitaNormal normal;          // Información específica de una cita normal
    private Consulta consulta;           // Consulta asociada a la cita

    /**
     * Enumerado que define los tipos de cita.
     */
    public enum TipoCita {
        EMERGENCIA, PROGRAMADA
    }

    /**
     * Constructor de la clase Cita.
     *
     * @param idCita Identificador único de la cita.
     * @param paciente El paciente asociado a la cita.
     * @param medico El médico asociado a la cita.
     * @param estado El estado actual de la cita.
     * @param fechaHora La fecha y hora de la cita.
     * @param tipoCita El tipo de cita (emergencia o programada).
     */
    public Cita(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
    }

    /**
     * Constructor de la clase Cita sin ID.
     *
     * @param paciente El paciente asociado a la cita.
     * @param medico El médico asociado a la cita.
     * @param estado El estado actual de la cita.
     * @param fechaHora La fecha y hora de la cita.
     * @param tipoCita El tipo de cita (emergencia o programada).
     */
    public Cita(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
    }

    /**
     * Constructor de la clase Cita con auditorías y consulta normal.
     *
     * @param idCita Identificador único de la cita.
     * @param paciente El paciente asociado a la cita.
     * @param medico El médico asociado a la cita.
     * @param estado El estado actual de la cita.
     * @param fechaHora La fecha y hora de la cita.
     * @param tipoCita El tipo de cita (emergencia o programada).
     * @param auditorias Lista de auditorías relacionadas con la cita.
     * @param normal Información específica de una cita normal.
     * @param consulta Consulta asociada a la cita.
     */
    public Cita(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, List<Auditoria> auditorias, CitaNormal normal, Consulta consulta) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.normal = normal;
        this.consulta = consulta;
    }

    /**
     * Constructor de la clase Cita con auditorías y cita de emergencia.
     *
     * @param idCita Identificador único de la cita.
     * @param paciente El paciente asociado a la cita.
     * @param medico El médico asociado a la cita.
     * @param estado El estado actual de la cita.
     * @param fechaHora La fecha y hora de la cita.
     * @param tipoCita El tipo de cita (emergencia o programada).
     * @param auditorias Lista de auditorías relacionadas con la cita.
     * @param emergencia Información específica de una cita de emergencia.
     * @param consulta Consulta asociada a la cita.
     */
    public Cita(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, Consulta consulta) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.emergencia = emergencia;
        this.consulta = consulta;
    }

    // Getters y Setters
    public TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(TipoCita tipoCita) {
        this.tipoCita = tipoCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public EstadosCita getEstado() {
        return estado;
    }

    public void setEstado(EstadosCita estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cita() {
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<Auditoria> auditorias) {
        this.auditorias = auditorias;
    }

    public CitaEmergencia getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(CitaEmergencia emergencia) {
        this.emergencia = emergencia;
    }

    public CitaNormal getNormal() {
        return normal;
    }

    public void setNormal(CitaNormal normal) {
        this.normal = normal;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + '}';
    }

}

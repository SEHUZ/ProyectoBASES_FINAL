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
 *
 * @author sonic
 */
public class Cita {
    private int idCita;
    private Paciente paciente;
    private Medico medico;
    private EstadosCita estado;
    private LocalDateTime fechaHora;
    private TipoCita tipoCita;
    private List<Auditoria> auditorias;
    private CitaEmergencia emergencia;
    private CitaNormal normal;
    private List<Consulta> consultas;
    
    public enum TipoCita {
        EMERGENCIA, PROGRAMADA
    }

    public Cita(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.consultas = new ArrayList<>();
        this.auditorias = new ArrayList<>();
    }

    public Cita(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
    }

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

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    

    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + '}';
    }
    
    
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.EstadoCita;
import Entidades.Medico;
import Entidades.Paciente;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class CitaViejaDTO {
    private int idCita;
    private Paciente paciente;
    private Medico medico;
    private EstadoCita estado;
    private LocalDateTime fechaHora;

    public CitaViejaDTO(int idCita, Paciente paciente, Medico medico, EstadoCita estado, LocalDateTime fechaHora) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    public CitaViejaDTO(Paciente paciente, Medico medico, EstadoCita estado, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    public CitaViejaDTO() {
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

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "CitaViejaDTO{" + "idCita=" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + '}';
    }
    
    
}

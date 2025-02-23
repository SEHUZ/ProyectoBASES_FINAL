/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Auditoria;
import Entidades.Cita;
import Entidades.Cita.TipoCita;
import Entidades.CitaEmergencia;
import Entidades.CitaNormal;
import Entidades.Consulta;
import Entidades.EstadosCita;
import Entidades.Medico;
import Entidades.Paciente;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public class CitaViejaDTO {
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

    public CitaViejaDTO(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, Cita.TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, CitaNormal normal, List<Consulta> consultas) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.emergencia = emergencia;
        this.normal = normal;
        this.consultas = consultas;
    }

    public CitaViejaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, Cita.TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, CitaNormal normal, List<Consulta> consultas) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.emergencia = emergencia;
        this.normal = normal;
        this.consultas = consultas;
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

    public Cita.TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(Cita.TipoCita tipoCita) {
        this.tipoCita = tipoCita;
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

    public CitaViejaDTO() {
    }
    
    

    @Override
    public String toString() {
        return "CitaViejaDTO{" + "idCita=" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + ", tipoCita=" + tipoCita + ", auditorias=" + auditorias + ", emergencia=" + emergencia + ", normal=" + normal + ", consultas=" + consultas + '}';
    }
    
}
    


    
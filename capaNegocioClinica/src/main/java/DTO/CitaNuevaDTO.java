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
 * Clase que representa un objeto de transferencia de datos (DTO) para una nueva cita.
 * Contiene información sobre el paciente, médico, estado de la cita, fecha y hora,
 * tipo de cita, auditorías y detalles específicos de la cita (emergencia o normal).
 * 
 * @author sonic
 */
public class CitaNuevaDTO {
    private Paciente paciente;
    private Medico medico;
    private EstadosCita estado;
    private LocalDateTime fechaHora;
    private Cita.TipoCita tipoCita;
    private List<Auditoria> auditorias;
    private CitaEmergencia emergencia;
    private CitaNormal normal;
    private Consulta consulta;
    
    /**
     * Constructor que inicializa todos los atributos de la cita nueva.
     *
     * @param paciente Paciente asociado a la cita.
     * @param medico Médico asignado a la cita.
     * @param estado Estado de la cita.
     * @param fechaHora Fecha y hora de la cita.
     * @param tipoCita Tipo de cita (emergencia o normal).
     * @param auditorias Lista de auditorías asociadas a la cita.
     * @param emergencia Detalles de la cita de emergencia.
     * @param normal Detalles de la cita normal.
     * @param consulta Consulta asociada a la cita.
     */
      

    public CitaNuevaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, CitaNormal normal, Consulta consulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.emergencia = emergencia;
        this.normal = normal;
        this.consulta = consulta;
    }

    
    /**
     * Constructor que inicializa la cita nueva sin detalles de emergencia.
     *
     * @param paciente Paciente asociado a la cita.
     * @param medico Médico asignado a la cita.
     * @param estado Estado de la cita.
     * @param fechaHora Fecha y hora de la cita.
     * @param tipoCita Tipo de cita (emergencia o normal).
     * @param auditorias Lista de auditorías asociadas a la cita.
     * @param normal Detalles de la cita normal.
     * @param consulta Consulta asociada a la cita.
     */
    public CitaNuevaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, List<Auditoria> auditorias, CitaNormal normal, Consulta consulta) {
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
     * Constructor que inicializa la cita nueva sin detalles de cita normal.
     *
     * @param paciente Paciente asociado a la cita.
     * @param medico Médico asignado a la cita.
     * @param estado Estado de la cita.
     * @param fechaHora Fecha y hora de la cita.
     * @param tipoCita Tipo de cita (emergencia o normal).
     * @param auditorias Lista de auditorías asociadas a la cita.
     * @param emergencia Detalles de la cita de emergencia.
     * @param consulta Consulta asociada a la cita.
     */
    public CitaNuevaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, Consulta consulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.auditorias = auditorias;
        this.emergencia = emergencia;
        this.consulta = consulta;
    }
    
    /**
     * Constructor que inicializa la cita nueva sin detalles de auditoría y cita normal.
     *
     * @param paciente Paciente asociado a la cita.
     * @param medico Médico asignado a la cita.
     * @param estado Estado de la cita.
     * @param fechaHora Fecha y hora de la cita.
     * @param tipoCita Tipo de cita (emergencia o normal).
     * @param emergencia Detalles de la cita de emergencia.
     * @param consulta Consulta asociada a la cita.
     */

    public CitaNuevaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, CitaEmergencia emergencia, Consulta consulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.emergencia = emergencia;
        this.consulta = consulta;
    }
    
    
    
    /**
     * Constructor que inicializa la cita nueva sin detalles de auditoría y cita de emergencia.
     *
     * @param paciente Paciente asociado a la cita.
     * @param medico Médico asignado a la cita.
     * @param estado Estado de la cita.
     * @param fechaHora Fecha y hora de la cita.
     * @param tipoCita Tipo de cita (emergencia o normal).
     * @param normal Detalles de la cita normal.
     * @param consulta Consulta asociada a la cita.
     */
    public CitaNuevaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, TipoCita tipoCita, CitaNormal normal, Consulta consulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.normal = normal;
        this.consulta = consulta;
    }
    

    public CitaNuevaDTO() {
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

    public TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(TipoCita tipoCita) {
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

    public Consulta getConsultas() {
        return consulta;
    }

    public void setConsultas(Consulta consulta) {
        this.consulta = consulta;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public String toString() {
        return "CitaNuevaDTO{" + "paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + ", tipoCita=" + tipoCita + ", auditorias=" + auditorias + ", emergencia=" + emergencia + ", normal=" + normal + ", consulta=" + consulta + '}';
    }
    
    
    
    

   
}

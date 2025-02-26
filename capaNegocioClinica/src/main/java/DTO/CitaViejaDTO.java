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
 * Clase que representa un objeto de transferencia de datos (DTO) para una cita existente.
 * Contiene información sobre el ID de la cita, paciente, médico, estado de la cita,
 * fecha y hora, tipo de cita, auditorías y detalles específicos de la cita (emergencia o normal).
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
    private Consulta consulta;
    
    /**
     * Constructor que inicializa todos los atributos de la cita vieja.
     *
     * @param idCita Identificador único de la cita.
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

    public CitaViejaDTO(int idCita, Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, Cita.TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, CitaNormal normal, Consulta consulta) {
        this.idCita = idCita;
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
     * Constructor que inicializa la cita vieja sin el ID de la cita.
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

    public CitaViejaDTO(Paciente paciente, Medico medico, EstadosCita estado, LocalDateTime fechaHora, Cita.TipoCita tipoCita, List<Auditoria> auditorias, CitaEmergencia emergencia, CitaNormal normal, Consulta consulta) {
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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consultas) {
        this.consulta = consultas;
    }

    public CitaViejaDTO() {
    }
    
    

    @Override
    public String toString() {
        return "CitaViejaDTO{" + "idCita=" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", estado=" + estado + ", fechaHora=" + fechaHora + ", tipoCita=" + tipoCita + ", auditorias=" + auditorias + ", emergencia=" + emergencia + ", normal=" + normal + ", consultas=" + consulta + '}';
    }
    
}
    


    
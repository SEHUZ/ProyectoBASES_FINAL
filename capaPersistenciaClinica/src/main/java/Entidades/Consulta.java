/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class Consulta {
    private int idConsulta;
    private Cita cita;
    private String diagnostico;
    private String estado;
    private LocalDateTime fechaHora;
    private String tratamiento;

    public Consulta(int idConsulta, Cita cita, String diagnostico, String estado, LocalDateTime fechaHora, String tratamiento) {
        this.idConsulta = idConsulta;
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tratamiento = tratamiento;
    }

    public Consulta(Cita cita, String diagnostico, String estado, LocalDateTime fechaHora, String tratamiento) {
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tratamiento = tratamiento;
    }

    public Consulta() {
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Override
    public String toString() {
        return "Consulta{" + "idConsulta=" + idConsulta + ", cita=" + cita + ", diagnostico=" + diagnostico + ", estado=" + estado + ", fechaHora=" + fechaHora + ", tratamiento=" + tratamiento + '}';
    }
    
    
}

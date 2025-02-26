/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Cita;
import Entidades.Consulta;
import java.time.LocalDateTime;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para una nueva consulta.
 * Contiene información sobre la cita asociada, diagnóstico, estado, fecha y hora,
 * y tratamiento recomendado.
 * 
 * @author sonic
 */
public class ConsultaNuevaDTO {
    private Cita cita;
    private String diagnostico;
    private String estado;
    private LocalDateTime fechaHora;
    private String tratamiento;
    
    /**
     * Constructor que inicializa todos los atributos de la nueva consulta.
     *
     * @param cita Cita asociada a la consulta.
     * @param diagnostico Diagnóstico realizado durante la consulta.
     * @param estado Estado de la consulta.
     * @param fechaHora Fecha y hora de la consulta.
     * @param tratamiento Tratamiento recomendado tras la consulta.
     */

    public ConsultaNuevaDTO(Cita cita, String diagnostico, String estado, LocalDateTime fechaHora, String tratamiento) {
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tratamiento = tratamiento;
    }

    public ConsultaNuevaDTO() {
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
        return "ConsultaNuevaDTO{" + "cita=" + cita + ", diagnostico=" + diagnostico + ", estado=" + estado + ", fechaHora=" + fechaHora + ", tratamiento=" + tratamiento + '}';
    }
     
    
}

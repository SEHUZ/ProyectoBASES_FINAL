/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 * Clase que representa una consulta médica.
 *
 * Esta clase almacena información sobre la consulta, incluyendo el diagnóstico,
 * el estado de la consulta, la fecha y hora, y el tratamiento recomendado.
 *
 * @author sonic
 */
public class Consulta {

    private int idConsulta;            // Identificador único de la consulta
    private Cita cita;                 // Cita asociada a la consulta
    private String diagnostico;        // Diagnóstico realizado durante la consulta
    private String estado;             // Estado actual de la consulta
    private LocalDateTime fechaHora;   // Fecha y hora de la consulta
    private String tratamiento;         // Tratamiento recomendado durante la consulta

    /**
     * Constructor de la clase Consulta.
     *
     * @param idConsulta Identificador único de la consulta.
     * @param cita La cita asociada a la consulta.
     * @param diagnostico El diagnóstico realizado durante la consulta.
     * @param estado El estado actual de la consulta.
     * @param fechaHora La fecha y hora de la consulta.
     * @param tratamiento El tratamiento recomendado durante la consulta.
     */
    public Consulta(int idConsulta, Cita cita, String diagnostico, String estado, LocalDateTime fechaHora, String tratamiento) {
        this.idConsulta = idConsulta;
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tratamiento = tratamiento;
    }

    /**
     * Constructor de la clase Consulta sin ID.
     *
     * @param cita La cita asociada a la consulta.
     * @param diagnostico El diagnóstico realizado durante la consulta.
     * @param estado El estado actual de la consulta.
     * @param fechaHora La fecha y hora de la consulta.
     * @param tratamiento El tratamiento recomendado durante la consulta.
     */
    public Consulta(Cita cita, String diagnostico, String estado, LocalDateTime fechaHora, String tratamiento) {
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.tratamiento = tratamiento;
    }

    /**
     * Constructor por defecto de la clase Consulta.
     */
    public Consulta() {
    }

    // Getters y Setters
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

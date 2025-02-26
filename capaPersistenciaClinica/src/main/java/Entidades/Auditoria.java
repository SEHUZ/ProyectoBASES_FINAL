/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 * Clase que representa una auditoría de operaciones realizadas sobre citas.
 *
 * Esta clase almacena información sobre la operación realizada, la cita
 * afectada, la fecha y hora de la operación, y detalles adicionales.
 *
 * @author sonic
 */
public class Auditoria {

    private int idAuditoria;          // Identificador único de la auditoría
    private Cita cita;                // Cita asociada a la operación auditada
    private String tipoOperacion;      // Tipo de operación realizada (ej. "CREAR", "ACTUALIZAR", "CANCELAR")
    private LocalDateTime fechaHora;   // Fecha y hora en que se realizó la operación
    private String detalles;           // Detalles adicionales sobre la operación

    /**
     * Constructor de la clase Auditoria.
     *
     * @param idAuditoria Identificador único de la auditoría.
     * @param cita La cita asociada a la operación auditada.
     * @param tipoOperacion El tipo de operación realizada.
     * @param fechaHora La fecha y hora en que se realizó la operación.
     * @param detalles Detalles adicionales sobre la operación.
     */
    public Auditoria(int idAuditoria, Cita cita, String tipoOperacion, LocalDateTime fechaHora, String detalles) {
        this.idAuditoria = idAuditoria;
        this.cita = cita;
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    /**
     * Constructor de la clase Auditoria sin el ID de auditoría.
     *
     * @param cita La cita asociada a la operación auditada.
     * @param tipoOperacion El tipo de operación realizada.
     * @param fechaHora La fecha y hora en que se realizó la operación.
     * @param detalles Detalles adicionales sobre la operación.
     */
    public Auditoria(Cita cita, String tipoOperacion, LocalDateTime fechaHora, String detalles) {
        this.cita = cita;
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    /**
     * Constructor vacio.
     */
    public Auditoria() {
    }

    // Getters y Setters
    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Auditoria{" + "idAuditoria=" + idAuditoria + ", cita=" + cita + ", tipoOperacion=" + tipoOperacion + ", fechaHora=" + fechaHora + ", detalles=" + detalles + '}';
    }

}

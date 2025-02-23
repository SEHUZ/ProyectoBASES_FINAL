/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */

public class Auditoria {
    private int idAuditoria;
    private Cita cita;
    private String tipoOperacion;
    private LocalDateTime fechaHora;
    private String detalles;

    public Auditoria(int idAuditoria, Cita cita, String tipoOperacion, LocalDateTime fechaHora, String detalles) {
        this.idAuditoria = idAuditoria;
        this.cita = cita;
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    public Auditoria(Cita cita, String tipoOperacion, LocalDateTime fechaHora, String detalles) {
        this.cita = cita;
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    public Auditoria() {
    }

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

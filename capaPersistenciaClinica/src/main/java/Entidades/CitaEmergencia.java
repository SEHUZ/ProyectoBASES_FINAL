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
public class CitaEmergencia {
    private int idCitaEmergencia;
    private Cita cita;
    private String folio;
    private LocalDateTime fechaExpiracion;

    public CitaEmergencia(int idCitaEmergencia, Cita cita, String folio, LocalDateTime fechaExpiracion) {
        this.idCitaEmergencia = idCitaEmergencia;
        this.cita = cita;
        this.folio = folio;
        this.fechaExpiracion = fechaExpiracion;
    }

    public CitaEmergencia(Cita cita, String folio, LocalDateTime fechaExpiracion) {
        this.cita = cita;
        this.folio = folio;
        this.fechaExpiracion = fechaExpiracion;
    }

    public CitaEmergencia() {
    }

    public int getIdCitaEmergencia() {
        return idCitaEmergencia;
    }

    public void setIdCitaEmergencia(int idCitaEmergencia) {
        this.idCitaEmergencia = idCitaEmergencia;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    @Override
    public String toString() {
        return "CitaEmergencia{" + "idCitaEmergencia=" + idCitaEmergencia + ", cita=" + cita + ", folio=" + folio + ", fechaExpiracion=" + fechaExpiracion + '}';
    }
    
    
}

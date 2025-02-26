/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 * Clase que representa una cita de emergencia.
 *
 * Esta clase almacena información sobre la cita de emergencia, incluyendo el
 * folio de la cita, la fecha de expiración y la cita asociada.
 *
 * @author sonic
 */
public class CitaEmergencia {

    private int idCitaEmergencia;      // Identificador único de la cita de emergencia
    private Cita cita;                 // Cita asociada a la emergencia
    private String folio;              // Folio de la cita de emergencia
    private LocalDateTime fechaExpiracion; // Fecha y hora de expiración de la cita de emergencia

    /**
     * Constructor de la clase CitaEmergencia.
     *
     * @param idCitaEmergencia Identificador único de la cita de emergencia.
     * @param cita La cita asociada a la emergencia.
     * @param folio El folio de la cita de emergencia.
     * @param fechaExpiracion La fecha y hora de expiración de la cita de
     * emergencia.
     */
    public CitaEmergencia(int idCitaEmergencia, Cita cita, String folio, LocalDateTime fechaExpiracion) {
        this.idCitaEmergencia = idCitaEmergencia;
        this.cita = cita;
        this.folio = folio;
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * Constructor de la clase CitaEmergencia sin ID.
     *
     * @param cita La cita asociada a la emergencia.
     * @param folio El folio de la cita de emergencia.
     * @param fechaExpiracion La fecha y hora de expiración de la cita de
     * emergencia.
     */
    public CitaEmergencia(Cita cita, String folio, LocalDateTime fechaExpiracion) {
        this.cita = cita;
        this.folio = folio;
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * Constructor por defecto de la clase CitaEmergencia.
     */
    public CitaEmergencia() {
    }

    // Getters y Setters
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Cita;
import java.time.LocalDate;

/**
 *
 * @author sonic
 */
public class citaEmergenciaNuevaDTO {
    private Cita cita;
    private String folio;
    private LocalDate fechaExpiracion;

    public citaEmergenciaNuevaDTO(Cita cita, String folio, LocalDate fechaExpiracion) {
        this.cita = cita;
        this.folio = folio;
        this.fechaExpiracion = fechaExpiracion;
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

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public citaEmergenciaNuevaDTO() {
    }
    
    
    
    
}

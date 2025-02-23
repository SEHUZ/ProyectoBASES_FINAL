/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Cita;

/**
 *
 * @author sonic
 */
public class citaNormalNuevaDTO {
    private Cita cita;

    public citaNormalNuevaDTO(Cita cita) {
        this.cita = cita;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public citaNormalNuevaDTO() {
    }
    
    
}


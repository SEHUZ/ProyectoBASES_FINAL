/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Cita;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para una nueva cita normal.
 * Contiene información sobre la cita asociada.
 * 
 * @author sonic
 */
public class citaNormalNuevaDTO {
    private Cita cita;
    
    /**
     * Constructor que inicializa el objeto citaNormalNuevaDTO con la cita proporcionada.
     *
     * @param cita Cita asociada a la nueva cita normal.
     */

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


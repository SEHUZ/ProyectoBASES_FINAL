/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author sonic
 */
public class CitaNormal {
    private int idCitaNormal;
    private Cita cita;

    public CitaNormal(int idCitaNormal, Cita cita) {
        this.idCitaNormal = idCitaNormal;
        this.cita = cita;
    }

    public CitaNormal(Cita cita) {
        this.cita = cita;
    }

    public CitaNormal() {
    }

    public int getIdCitaNormal() {
        return idCitaNormal;
    }

    public void setIdCitaNormal(int idCitaNormal) {
        this.idCitaNormal = idCitaNormal;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    @Override
    public String toString() {
        return "CitaNormal{" + "idCitaNormal=" + idCitaNormal + ", cita=" + cita + '}';
    }
    
    
}

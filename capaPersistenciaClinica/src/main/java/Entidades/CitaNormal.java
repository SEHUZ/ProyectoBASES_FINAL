/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa una cita normal.
 *
 * Esta clase almacena información sobre una cita normal, incluyendo el
 * identificador único de la cita normal y la cita asociada.
 *
 * @author sonic
 */
public class CitaNormal {

    private int idCitaNormal;  // Identificador único de la cita normal
    private Cita cita;         // Cita asociada a la cita normal

    /**
     * Constructor de la clase CitaNormal.
     *
     * @param idCitaNormal Identificador único de la cita normal.
     * @param cita La cita asociada a la cita normal.
     */
    public CitaNormal(int idCitaNormal, Cita cita) {
        this.idCitaNormal = idCitaNormal;
        this.cita = cita;
    }

    /**
     * Constructor de la clase CitaNormal sin ID.
     *
     * @param cita La cita asociada a la cita normal.
     */
    public CitaNormal(Cita cita) {
        this.cita = cita;
    }

    /**
     * Constructor por defecto de la clase CitaNormal.
     */
    public CitaNormal() {
    }

    // Getters y Setters
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

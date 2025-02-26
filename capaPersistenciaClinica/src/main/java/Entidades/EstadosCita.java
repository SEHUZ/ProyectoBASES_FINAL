/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa el estado de una cita médica.
 *
 * Esta clase almacena información sobre el estado de la cita, incluyendo un
 * identificador único y una descripción del estado.
 *
 * @author sonic
 */
public class EstadosCita {

    private int idEstado;          // Identificador único del estado de la cita
    private String descripcion;     // Descripción del estado de la cita

    /**
     * Constructor de la clase EstadosCita con todos los parámetros.
     *
     * @param idEstado Identificador único del estado de la cita.
     * @param descripcion Descripción del estado de la cita.
     */
    public EstadosCita(int idEstado, String descripcion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
    }

    /**
     * Constructor por defecto de la clase EstadosCita.
     */
    public EstadosCita() {
    }

    /**
     * Constructor de la clase EstadosCita con solo la descripción.
     *
     * @param descripcion Descripción del estado de la cita.
     */
    public EstadosCita(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EstadoCita{" + "idEstado=" + idEstado + ", descripcion=" + descripcion + '}';
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa la dirección de un paciente.
 *
 * Esta clase almacena información sobre la dirección, incluyendo la calle, el
 * número, el código postal y el ID del paciente asociado.
 *
 * @author sonic
 */
public class DireccionPaciente {

    private int idDireccion;    // Identificador único de la dirección
    private int idPaciente;      // Identificador del paciente asociado a la dirección
    private String calle;        // Calle de la dirección
    private String numero;       // Número de la dirección
    private String codigoPostal;  // Código postal de la dirección

    /**
     * Constructor de la clase DireccionPaciente con todos los parámetros.
     *
     * @param idDireccion Identificador único de la dirección.
     * @param idPaciente Identificador del paciente asociado a la dirección.
     * @param calle La calle de la dirección.
     * @param numero El número de la dirección.
     * @param codigoPostal El código postal de la dirección.
     */
    public DireccionPaciente(int idDireccion, int idPaciente, String calle, String numero, String codigoPostal) {
        this.idDireccion = idDireccion;
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    /**
     * Constructor de la clase DireccionPaciente sin idDireccion.
     *
     * Este constructor se utiliza para crear una nueva dirección.
     *
     * @param idPaciente Identificador del paciente asociado a la dirección.
     * @param calle La calle de la dirección.
     * @param numero El número de la dirección.
     * @param codigoPostal El código postal de la dirección.
     */
    public DireccionPaciente(int idPaciente, String calle, String numero, String codigoPostal) {
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    /**
     * Constructor de la clase DireccionPaciente con solo los detalles de la
     * dirección.
     *
     * @param calle La calle de la dirección.
     * @param numero El número de la dirección.
     * @param codigoPostal El código postal de la dirección.
     */
    public DireccionPaciente(String calle, String numero, String codigoPostal) {
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    /**
     * Constructor por defecto de la clase DireccionPaciente.
     */
    public DireccionPaciente() {
    }

    // Getters y Setters
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "DireccionPaciente{"
                + "calle='" + calle + '\''
                + ", numero='" + numero + '\''
                + ", codigoPostal='" + codigoPostal + '\''
                + '}';
    }

}

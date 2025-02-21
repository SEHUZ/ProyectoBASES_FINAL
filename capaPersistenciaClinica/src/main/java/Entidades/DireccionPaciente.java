/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author sonic
 */
public class DireccionPaciente {

    private int idDireccion;
    private int idPaciente;
    private String calle;
    private String numero;
    private String codigoPostal;

    // Constructor con todos los parámetros
    public DireccionPaciente(int idDireccion, int idPaciente, String calle, String numero, String codigoPostal) {
        this.idDireccion = idDireccion;
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    // Constructor sin idDireccion (para crear una nueva dirección)
    public DireccionPaciente(int idPaciente, String calle, String numero, String codigoPostal) {
        this.idPaciente = idPaciente;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    public DireccionPaciente() {
    }

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
        return "DireccionPaciente{" +
               "calle='" + calle + '\'' +
               ", numero='" + numero + '\'' +
               ", codigoPostal='" + codigoPostal + '\'' +
               '}';
    }
    
    

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 * Clase que representa una excepción específica para errores de persistencia en
 * la clínica.
 *
 * Esta excepción se utiliza para manejar errores relacionados con la
 * persistencia de datos en el sistema clínico, proporcionando información sobre
 * el error ocurrido.
 *
 * @author sonic
 */
public class PersistenciaClinicaException extends Exception {

    /**
     * Constructor de la clase PersistenciaClinicaException.
     *
     * @param message Mensaje que describe la excepción.
     */
    public PersistenciaClinicaException(String message) {
        super(message);
    }

    /**
     * Constructor de la clase PersistenciaClinicaException con causa.
     *
     * @param message Mensaje que describe la excepción.
     * @param cause La causa de la excepción (otra excepción que provocó esta).
     */
    public PersistenciaClinicaException(String message, Throwable cause) {
        super(message, cause);
    }

}

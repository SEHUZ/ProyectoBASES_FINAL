/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 * Clase que representa una excepción específica de negocio.
 * Esta excepción se utiliza para indicar errores relacionados con las reglas de negocio
 * en la aplicación.
 * 
 * @author Daniel M
 */
public class NegocioException extends Exception {
    
    /**
     * Constructor que inicializa la excepción con un mensaje específico.
     *
     * @param message Mensaje que describe la excepción.
     */

    public NegocioException(String message) {
        super(message);
    }
    
    
    /**
     * Constructor que inicializa la excepción con un mensaje específico y una causa.
     *
     * @param message Mensaje que describe la excepción.
     * @param cause Causa de la excepción (otra excepción que se ha producido).
     */

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}

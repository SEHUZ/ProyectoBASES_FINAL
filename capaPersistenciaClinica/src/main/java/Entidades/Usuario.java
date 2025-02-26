/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa a un usuario del sistema.
 *
 * Esta clase almacena información sobre el usuario, incluyendo su
 * identificación, nombre de usuario, contraseña y rol dentro del sistema.
 *
 * @author sonic
 */
public class Usuario {

    private int idUsuario;        // Identificador único del usuario
    private String User;          // Nombre de usuario
    private String contrasenia;   // Contraseña del usuario
    private String rol;           // Rol del usuario (ej. "paciente", "médico", "administrador")

    /**
     * Constructor por defecto de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor de la clase Usuario con todos los parámetros.
     *
     * @param idUsuario Identificador único del usuario.
     * @param User Nombre de usuario.
     * @param contrasenia Contraseña del usuario.
     * @param rol Rol del usuario dentro del sistema.
     */
    public Usuario(int idUsuario, String User, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.User = User;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    /**
     * Constructor de la clase Usuario sin idUsuario.
     *
     * @param User Nombre de usuario.
     * @param contrasenia Contraseña del usuario.
     * @param rol Rol del usuario dentro del sistema.
     */
    public Usuario(String User, String contrasenia, String rol) {
        this.User = User;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "idUsuario=" + idUsuario
                + ", user='" + User + '\''
                + ", contrasenia='" + contrasenia + '\''
                + ", rol='" + rol + '\''
                + '}';
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para un nuevo usuario.
 * Contiene información sobre el nombre de usuario, contraseña y rol del usuario.
 * 
 * @author sonic
 */
public class UsuarioNuevoDTO {

    private String User;
    private String contrasenia;
    private String rol;

    public UsuarioNuevoDTO() {
    }
    
    /**
     * Constructor que inicializa todos los atributos del nuevo usuario.
     *
     * @param User Nombre de usuario.
     * @param contrasenia Contraseña del usuario.
     * @param rol Rol del usuario.
     */

    public UsuarioNuevoDTO(String User, String contrasenia, String rol) {
        this.User = User;
        this.contrasenia = contrasenia;
        this.rol = rol;
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
        return "UsuarioNuevoDTO{" + "User=" + User + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
    
}


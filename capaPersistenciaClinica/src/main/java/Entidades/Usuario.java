/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author sonic
 */
public class Usuario {

    private int idUsuario;
    private String User;
    private String contrasenia;
    private String rol;

    public Usuario() {
    }

    public Usuario(int idUsuario, String User, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.User = User;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario(String User, String contrasenia, String rol) {
        this.User = User;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

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
        return "Usuario{" + "idUsuario=" + idUsuario + ", User=" + User + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
    
    


}

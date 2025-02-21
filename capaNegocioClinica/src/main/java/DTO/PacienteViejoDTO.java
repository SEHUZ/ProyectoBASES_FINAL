/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.DireccionPaciente;
import Entidades.Usuario;
import java.time.LocalDate;

/**
 *
 * @author Daniel M
 */
public class PacienteViejoDTO {
    
    private int idPaciente;
    private DireccionPaciente direccion;
    private Usuario usuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;

    public PacienteViejoDTO(int idPaciente, DireccionPaciente direccion, Usuario usuario, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.idPaciente = idPaciente;
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }
    
    

    public PacienteViejoDTO() {
    }

    public PacienteViejoDTO(DireccionPaciente direccion, Usuario usuario, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public DireccionPaciente getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionPaciente direccion) {
        this.direccion = direccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "PacienteViejoDTO{" + "idPaciente=" + idPaciente + ", direccion=" + direccion + ", usuario=" + usuario + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", telefono=" + telefono + '}';
    }

    

    
}

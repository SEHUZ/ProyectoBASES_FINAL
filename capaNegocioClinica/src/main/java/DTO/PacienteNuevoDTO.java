/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.DireccionPaciente;
import Entidades.Usuario;
import java.time.LocalDate;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para un nuevo paciente.
 * Contiene información sobre la dirección, usuario asociado, nombre, apellidos,
 * fecha de nacimiento, correo electrónico y número de teléfono del paciente.
 * 
 * @author Daniel M
 */
public class PacienteNuevoDTO {
    
    
    private DireccionPaciente direccion;
    private Usuario usuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    
/**
     * Constructor vacío para la clase PacienteNuevoDTO.
     */
    public PacienteNuevoDTO() {
    }
    
    /**
     * Constructor que inicializa todos los atributos del nuevo paciente.
     *
     * @param direccion Dirección del paciente.
     * @param usuario Usuario asociado al paciente.
     * @param nombre Nombre del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param email Correo electrónico del paciente.
     * @param telefono Número de teléfono del paciente.
     */

    public PacienteNuevoDTO(DireccionPaciente direccion, Usuario usuario, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }
    
    /**
     * Constructor que inicializa los atributos del nuevo paciente sin el usuario.
     *
     * @param direccion Dirección del paciente.
     * @param nombre Nombre del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param email Correo electrónico del paciente.
     * @param telefono Número de teléfono del paciente.
     */

    public PacienteNuevoDTO(DireccionPaciente direccion, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.direccion = direccion;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
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
        return "PacienteNuevoDTO{" + "direccion=" + direccion + ", usuario=" + usuario + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", telefono=" + telefono + '}';
    }
    
    
    
}

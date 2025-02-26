/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;

/**
 * Clase que representa a un paciente.
 *
 * Esta clase almacena información sobre el paciente, incluyendo su
 * identificación, dirección, datos personales, fecha de nacimiento, correo
 * electrónico y teléfono.
 *
 * @author sonic
 */
public class Paciente {

    private int idPaciente;                // Identificador único del paciente
    private DireccionPaciente direccion;    // Dirección del paciente
    private Usuario usuario;                // Usuario asociado al paciente
    private String nombres;                 // Nombres del paciente
    private String apellidoPaterno;         // Apellido paterno del paciente
    private String apellidoMaterno;         // Apellido materno del paciente
    private LocalDate fechaNacimiento;      // Fecha de nacimiento del paciente
    private String email;                   // Correo electrónico del paciente
    private String telefono;                 // Número de teléfono del paciente

    /**
     * Constructor por defecto de la clase Paciente.
     */
    public Paciente() {
    }

    /**
     * Constructor de la clase Paciente con todos los parámetros.
     *
     * @param idPaciente Identificador único del paciente.
     * @param direccion La dirección del paciente.
     * @param usuario El usuario asociado al paciente.
     * @param nombres Los nombres del paciente.
     * @param apellidoPaterno El apellido paterno del paciente.
     * @param apellidoMaterno El apellido materno del paciente.
     * @param fechaNacimiento La fecha de nacimiento del paciente.
     * @param email El correo electrónico del paciente.
     * @param telefono El número de teléfono del paciente.
     */
    public Paciente(int idPaciente, DireccionPaciente direccion, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.idPaciente = idPaciente;
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Constructor de la clase Paciente sin idPaciente.
     *
     * @param direccion La dirección del paciente.
     * @param usuario El usuario asociado al paciente.
     * @param nombres Los nombres del paciente.
     * @param apellidoPaterno El apellido paterno del paciente.
     * @param apellidoMaterno El apellido materno del paciente.
     * @param fechaNacimiento La fecha de nacimiento del paciente.
     * @param email El correo electrónico del paciente.
     * @param telefono El número de teléfono del paciente.
     */
    public Paciente(DireccionPaciente direccion, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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
        return "Paciente{" + "idPaciente=" + idPaciente + ", direccion=" + direccion + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", telefono=" + telefono + '}';
    }

}

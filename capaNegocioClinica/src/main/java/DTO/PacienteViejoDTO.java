/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.DireccionPaciente;
import Entidades.Usuario;
import java.time.LocalDate;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para un paciente existente.
 * Contiene información sobre el ID del paciente, dirección, usuario asociado, nombres,
 * apellidos, fecha de nacimiento, correo electrónico y número de teléfono del paciente.
 * 
 * @author Daniel M
 */
public class PacienteViejoDTO {

    private int idPaciente;
    private DireccionPaciente direccion;
    private Usuario usuario;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    
    /**
     * Constructor que inicializa todos los atributos del paciente existente.
     *
     * @param idPaciente Identificador único del paciente.
     * @param direccion Dirección del paciente.
     * @param usuario Usuario asociado al paciente.
     * @param nombres Nombres del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param email Correo electrónico del paciente.
     * @param telefono Número de teléfono del paciente.
     */

    public PacienteViejoDTO(int idPaciente, DireccionPaciente direccion, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
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
     * Constructor vacío para la clase PacienteViejoDTO.
     */

    public PacienteViejoDTO() {
    }
    
    /**
     * Constructor que inicializa los atributos del paciente existente sin el ID del paciente.
     *
     * @param direccion Dirección del paciente.
     * @param usuario Usuario asociado al paciente.
     * @param nombres Nombres del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param email Correo electrónico del paciente.
     * @param telefono Número de teléfono del paciente.
     */

    public PacienteViejoDTO(DireccionPaciente direccion, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String email, String telefono) {
        this.direccion = direccion;
        this.usuario = usuario;
        this.nombres = nombres;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombre(String nombres) {
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
        return "PacienteViejoDTO{" + "idPaciente=" + idPaciente + ", direccion=" + direccion + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", telefono=" + telefono + '}';
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa a un médico.
 *
 * Esta clase almacena información sobre el médico, incluyendo su
 * identificación, datos personales, especialidad y estado de actividad.
 *
 * @author sonic
 */
public class Medico {

    private int idMedico;                // Identificador único del médico
    private Usuario usuario;              // Usuario asociado al médico
    private String nombres;               // Nombres del médico
    private String apellidoPaterno;       // Apellido paterno del médico
    private String apellidoMaterno;       // Apellido materno del médico
    private String cedula;                // Cédula profesional del médico
    private String especialidad;          // Especialidad del médico
    private boolean activo;               // Estado de actividad del médico (activo/inactivo)

    /**
     * Constructor de la clase Medico con todos los parámetros.
     *
     * @param idMedico Identificador único del médico.
     * @param usuario El usuario asociado al médico.
     * @param nombres Los nombres del médico.
     * @param apellidoPaterno El apellido paterno del médico.
     * @param apellidoMaterno El apellido materno del médico.
     * @param cedula La cédula profesional del médico.
     * @param especialidad La especialidad del médico.
     * @param activo Estado de actividad del médico (true si está activo, false
     * si está inactivo).
     */
    public Medico(int idMedico, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, String cedula, String especialidad, boolean activo) {
        this.idMedico = idMedico;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    /**
     * Constructor de la clase Medico sin idMedico.
     *
     * @param usuario El usuario asociado al médico.
     * @param nombres Los nombres del médico.
     * @param apellidoPaterno El apellido paterno del médico.
     * @param apellidoMaterno El apellido materno del médico.
     * @param cedula La cédula profesional del médico.
     * @param especialidad La especialidad del médico.
     * @param activo Estado de actividad del médico (true si está activo, false
     * si está inactivo).
     */
    public Medico(Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, String cedula, String especialidad, boolean activo) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    // Getters y Setters
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Medico() {
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{" + "idMedico=" + idMedico + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", cedula=" + cedula + ", especialidad=" + especialidad + '}';
    }

}

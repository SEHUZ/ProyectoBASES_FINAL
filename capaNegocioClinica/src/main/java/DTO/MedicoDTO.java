/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Usuario;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para un médico.
 * Contiene información sobre el ID del médico, usuario asociado, nombres, apellidos,
 * cédula, especialidad y estado de actividad.
 * 
 * @author sonic
 */
public class MedicoDTO {
    private int idMedico;
    private Usuario usuario;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String cedula;
    private String especialidad;
    private boolean activo;
    
    /**
     * Constructor vacío para la clase MedicoDTO.
     */

    public MedicoDTO() {
    }
    
    /**
     * Constructor que inicializa todos los atributos del médico.
     *
     * @param idMedico Identificador único del médico.
     * @param usuario Usuario asociado al médico.
     * @param nombres Nombres del médico.
     * @param apellidoPaterno Apellido paterno del médico.
     * @param apellidoMaterno Apellido materno del médico.
     * @param cedula Cédula del médico.
     * @param especialidad Especialidad del médico.
     * @param activo Estado de actividad del médico (activo/inactivo).
     */

    public MedicoDTO(int idMedico, Usuario usuario, String nombres, String apellidoPaterno, String apellidoMaterno, String cedula, String especialidad, boolean activo) {
        this.idMedico = idMedico;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.activo = activo;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "MedicoDTO{" + "idMedico=" + idMedico + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", cedula=" + cedula + ", especialidad=" + especialidad + ", activo=" + activo + '}';
    }
    
    
    
}

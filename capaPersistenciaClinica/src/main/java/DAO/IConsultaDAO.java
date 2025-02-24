/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Consulta;
import Exception.PersistenciaClinicaException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */


public interface IConsultaDAO {

    // Método para insertar una nueva consulta
    Consulta insertarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException;

    // Método para obtener todas las consultas de un paciente específico
    List<Consulta> obtenerConsultasPorPaciente(int idPaciente) throws SQLException, PersistenciaClinicaException;

    // Método para actualizar una consulta
    boolean actualizarConsulta(Consulta consulta) throws SQLException, PersistenciaClinicaException;

    // Método para cancelar una consulta
    boolean cancelarConsulta(int idConsulta) throws SQLException, PersistenciaClinicaException;

    // Método para obtener una consulta por ID
    Consulta obtenerConsultaPorId(int idConsulta) throws SQLException, PersistenciaClinicaException;
    
    List<Consulta> obtenerConsultasPorEspecialidadYFechas(int idPaciente, String especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException, PersistenciaClinicaException;

    public boolean existeConsultaParaCita(int idCita) throws PersistenciaClinicaException;

}







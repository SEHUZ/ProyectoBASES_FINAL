/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.DireccionPacienteDAO;
import DAO.IDireccionPacienteDAO;
import DTO.DireccionNuevoDTO;
import Entidades.DireccionPaciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.DireccionPacienteMapper;

/**
 *
 * @author sonic
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public class DireccionesPacientesBO {

    private static final Logger logger = Logger.getLogger(DireccionesPacientesBO.class.getName());
    private final IDireccionPacienteDAO direccionesDAO;
    private final DireccionPacienteMapper mapper = new DireccionPacienteMapper();

    public DireccionesPacientesBO(IConexionBD conexion) {
        this.direccionesDAO = new DireccionPacienteDAO(conexion);
    }

    public boolean registrarDireccion(DireccionNuevoDTO direccionDTO) throws NegocioException {
        if (direccionDTO == null) {
            throw new NegocioException("La dirección no puede ser nula.");
        }

        if (direccionDTO.getCalle().isEmpty() || direccionDTO.getNumero().isEmpty() || direccionDTO.getCp().isEmpty()) {
            throw new NegocioException("No puede haber campos vacíos.");
        }

        if (direccionDTO.getCp().length() != 5 || !direccionDTO.getCp().matches("\\d{5}")) {
            throw new NegocioException("El código postal debe tener 5 dígitos numéricos.");
        }

        DireccionPaciente direccion = mapper.toEntity(direccionDTO);

        try {
            return direccionesDAO.agregarDireccion(direccion);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al registrar la dirección", ex);
            throw new NegocioException("Hubo un error al registrar la dirección.", ex);
        }
    }

    public DireccionNuevoDTO consultarDireccionPorPaciente(int idPaciente) throws NegocioException {
        if (idPaciente <= 0) {
            throw new NegocioException("El ID del paciente debe ser un número positivo.");
        }

        try {
            DireccionPaciente direccion = direccionesDAO.consultarDireccionPorPaciente(idPaciente);
            if (direccion == null) {
                throw new NegocioException("No se encontró una dirección para el paciente con ID: " + idPaciente);
            }
            return mapper.toDTO(direccion);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar la dirección", ex);
            throw new NegocioException("Hubo un error al consultar la dirección.", ex);
        }
    }

    public boolean actualizarDireccion(DireccionNuevoDTO direccionDTO) throws NegocioException {
        if (direccionDTO == null) {
            throw new NegocioException("La dirección no puede ser nula.");
        }

        if (direccionDTO.getCalle().isEmpty() || direccionDTO.getNumero().isEmpty() || direccionDTO.getCp().isEmpty()) {
            throw new NegocioException("No puede haber campos vacíos.");
        }

        if (direccionDTO.getCp().length() != 5 || !direccionDTO.getCp().matches("\\d{5}")) {
            throw new NegocioException("El código postal debe tener 5 dígitos numéricos.");
        }

        DireccionPaciente direccion = mapper.toEntity(direccionDTO);

        try {
            return direccionesDAO.actualizarDireccion(direccion);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar la dirección", ex);
            throw new NegocioException("Hubo un error al actualizar la dirección.", ex);
        }
    }
}

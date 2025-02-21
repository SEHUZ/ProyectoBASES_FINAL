/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.mkammerer.argon2.*;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author sonic XDDDDDD
 */
public class PacienteBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper();

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public boolean registrarPaciente(PacienteNuevoDTO pacienteNuevoDTO) throws NegocioException, SQLException {
        if (pacienteNuevoDTO == null) {
            throw new NegocioException("El paciente debe tener todos sus datos.");
        }

        if (pacienteNuevoDTO.getNombre() == null || pacienteNuevoDTO.getApellidoPaterno() == null
                || pacienteNuevoDTO.getEmail() == null || pacienteNuevoDTO.getTelefono() == null
                || pacienteNuevoDTO.getFechaNacimiento() == null || pacienteNuevoDTO.getUsuario().getUser() == null
                || pacienteNuevoDTO.getUsuario().getContrasenia() == null || pacienteNuevoDTO.getUsuario().getRol() == null
                || pacienteNuevoDTO.getUsuario() == null || pacienteNuevoDTO.getDireccion().getCalle() == null
                || pacienteNuevoDTO.getDireccion().getNumero() == null || pacienteNuevoDTO.getDireccion().getCodigoPostal() == null
                || pacienteNuevoDTO.getDireccion() == null) {
            throw new NegocioException("Los datos del paciente no pueden estar vacios.");
        }

        if (pacienteNuevoDTO.getNombre().trim().isEmpty() || pacienteNuevoDTO.getApellidoPaterno().trim().isEmpty()
                || pacienteNuevoDTO.getEmail().trim().isEmpty() || pacienteNuevoDTO.getTelefono().trim().isEmpty()
                || pacienteNuevoDTO.getUsuario().getUser().trim().isEmpty() || pacienteNuevoDTO.getUsuario().getContrasenia().trim().isEmpty()
                || pacienteNuevoDTO.getUsuario().getRol().trim().isEmpty() || pacienteNuevoDTO.getDireccion().getCalle().trim().isEmpty()
                || pacienteNuevoDTO.getDireccion().getNumero().trim().isEmpty() || pacienteNuevoDTO.getDireccion().getCodigoPostal().trim().isEmpty()) {
            throw new NegocioException("Los datos del paciente no pueden estar vacios o con espacios en blanco.");
        }

        if (pacienteNuevoDTO.getTelefono().length() != 10) {
            throw new NegocioException("El numero de telefono debe ser estrictamente de 10 digitos.");
        }

        if (pacienteNuevoDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new NegocioException("La fecha de nacimiento debe ser valida.");
        }

        // falta validar si el paciente ya existe, o si el correo esta repetido, o si el numero de telefono ya esta registrado
        // tambien validar si el correo es valido
        // Se encripta la contraseña
        String contraseña = contraseñaHash(pacienteNuevoDTO.getUsuario().getContrasenia());
        pacienteNuevoDTO.getUsuario().setContrasenia(contraseña);

        Paciente pacienteEntity = mapper.toEntityNuevo(pacienteNuevoDTO);

        try {
            Paciente pacienteRegistrado = pacienteDAO.registrarPaciente(pacienteEntity);
            return pacienteRegistrado != null;
        } catch (PersistenciaClinicaException | SQLException e) {
            logger.log(Level.SEVERE, "Error en el registro del paciente.", e);
            throw new NegocioException("Error en el registro del paciente.");
        }
    }

    public static String contraseñaHash(String contraseña) {
        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(3, 65536, 1, contraseña);
        argon2.wipeArray(contraseña.toCharArray());
        return hash;
    }

}

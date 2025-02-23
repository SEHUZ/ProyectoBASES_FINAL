/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

// Imports
import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.PacienteMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.mkammerer.argon2.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase BO que representa al Paciente y se encarga de las validaciones de los
 * datos ingresados.
 *
 * @author Daniel M
 */
public class PacienteBO {

    // Atributo logger para mostrar mensajes legibles al depurar.
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());

    // Declaracion de la DAO para hacer uso de sus metodos.
    private final IPacienteDAO pacienteDAO;

    // Declaracion del mapper del Paciente para hacer conversiones.
    private final PacienteMapper mapper = new PacienteMapper();

    /**
     * Constructor para inicializar la conexion a la base de datos.
     *
     * @param conexion Representa la conexion que se mandara a la DAO.
     */
    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    /**
     * Metodo que registra un paciente utilizando DAOs.
     *
     * @param pacienteNuevoDTO Paciente a registrar.
     * @return True si el Paciente se registro correctamente, False en caso
     * contrario.
     * @throws NegocioException Si se viola una regla de Negocio.
     * @throws SQLException Si ocurre un error con SQL.
     * @throws PersistenciaClinicaException Si hay un error en la DAO.
     */
    public boolean registrarPaciente(PacienteNuevoDTO pacienteNuevoDTO) throws NegocioException, SQLException, PersistenciaClinicaException {
        // Verificar que el paciente no sea nulo.
        if (pacienteNuevoDTO == null) {
            throw new NegocioException("El paciente debe tener todos sus datos.");
        }

        // Verificar que ninguno de los datos ingresados sea nulo.
        if (pacienteNuevoDTO.getNombre() == null || pacienteNuevoDTO.getApellidoPaterno() == null
                || pacienteNuevoDTO.getEmail() == null || pacienteNuevoDTO.getTelefono() == null
                || pacienteNuevoDTO.getFechaNacimiento() == null || pacienteNuevoDTO.getUsuario().getUser() == null
                || pacienteNuevoDTO.getUsuario().getContrasenia() == null || pacienteNuevoDTO.getUsuario().getRol() == null
                || pacienteNuevoDTO.getUsuario() == null || pacienteNuevoDTO.getDireccion().getCalle() == null
                || pacienteNuevoDTO.getDireccion().getNumero() == null || pacienteNuevoDTO.getDireccion().getCodigoPostal() == null
                || pacienteNuevoDTO.getDireccion() == null) {
            throw new NegocioException("Los datos del paciente no pueden estar vacios.");
        }

        // Verificar que no existan espacios en blanco.
        if (pacienteNuevoDTO.getNombre().trim().isEmpty() || pacienteNuevoDTO.getApellidoPaterno().trim().isEmpty()
                || pacienteNuevoDTO.getEmail().trim().isEmpty() || pacienteNuevoDTO.getTelefono().trim().isEmpty()
                || pacienteNuevoDTO.getUsuario().getUser().trim().isEmpty() || pacienteNuevoDTO.getUsuario().getContrasenia().trim().isEmpty()
                || pacienteNuevoDTO.getUsuario().getRol().trim().isEmpty() || pacienteNuevoDTO.getDireccion().getCalle().trim().isEmpty()
                || pacienteNuevoDTO.getDireccion().getNumero().trim().isEmpty() || pacienteNuevoDTO.getDireccion().getCodigoPostal().trim().isEmpty()) {
            throw new NegocioException("Los datos del paciente no pueden estar vacios o con espacios en blanco.");
        }

        // Verificar que el numero de telefono sea estrictamente de 10 digitos.
        if (pacienteNuevoDTO.getTelefono().length() != 10) {
            throw new NegocioException("El numero de telefono debe ser estrictamente de 10 digitos.");
        }

        // Verificar que la fecha de nacimiento no sea despues del momento en el que se esta ingresando.
        if (pacienteNuevoDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new NegocioException("La fecha de nacimiento debe ser valida.");
        }

        // Verificar si el correo ya ha sido registrado.
        if (pacienteDAO.consultarPacientePorCorreo(pacienteNuevoDTO.getEmail()) != null) {
            throw new NegocioException("El correo ingresado ya esta registrado.");
        }

        // Verificar si el telefono ya ha sido registrado.
        if (pacienteDAO.consultarPacientePorTelefono(pacienteNuevoDTO.getTelefono()) != null) {
            throw new NegocioException("El telefono ingresado ya esta registrado.");
        }

        // Verificar si el nombre de usuario ya ha sido registrado.
        if (pacienteDAO.consultarPacientePorUsuario(pacienteNuevoDTO.getUsuario().getUser()) != null) {
            throw new NegocioException("El nombre de usuario ingresado ya esta registrado.");
        }

        // Verificar si el correo tiene un formato valido.
        if (!validarCorreo(pacienteNuevoDTO.getEmail())) {
            throw new NegocioException("El correo ingresado tiene un formato incorrecto.");
        }

        // Encriptar la contraseña
        String contraseña = contraseñaHash(pacienteNuevoDTO.getUsuario().getContrasenia());
        pacienteNuevoDTO.getUsuario().setContrasenia(contraseña);

        // Se convierte el pacienteNuevoDTO a entidad Paciente
        Paciente pacienteEntity = mapper.toEntityNuevo(pacienteNuevoDTO);

        try {
            // Se manda al metodo registrarPaciente de PacienteDAO
            Paciente pacienteRegistrado = pacienteDAO.registrarPaciente(pacienteEntity);
            return pacienteRegistrado != null;
        } catch (PersistenciaClinicaException | SQLException e) {
            logger.log(Level.SEVERE, "Error en el registro del paciente.", e);
            throw new NegocioException("Error en el registro del paciente.");
        }
    }

    public Paciente actualizarPaciente(PacienteViejoDTO pacienteViejoDTO) throws NegocioException, SQLException, PersistenciaClinicaException {
        // Verificar que el paciente no sea nulo.
        if (pacienteViejoDTO == null) {
            throw new NegocioException("Para actualizar el paciente debe cambiar al menos un campo.");
        }

        // Verificar si el correo ya ha sido registrado.
        if (pacienteDAO.consultarPacientePorCorreo(pacienteViejoDTO.getEmail()) != null) {
            throw new NegocioException("El correo ingresado ya esta registrado.");
        }

        // Verificar si el telefono ya ha sido registrado.
        if (pacienteDAO.consultarPacientePorTelefono(pacienteViejoDTO.getTelefono()) != null) {
            throw new NegocioException("El telefono ingresado ya esta registrado.");
        }

        try {
            Paciente pacienteActualizar = new PacienteMapper().toEntityViejo(pacienteViejoDTO);
            Paciente pacienteActualizado = pacienteDAO.actualizarPaciente(pacienteActualizar);

            return pacienteActualizado;
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar paciente", ex);
            throw new NegocioException("Error al actualizar paciente: " + ex.getMessage());
        }
    }

    public PacienteViejoDTO buscarPacientePorUsuario(String user) throws NegocioException {
        try {
            Paciente paciente = pacienteDAO.consultarPacientePorUsuario(user);
            if (paciente == null) {
                return null;
            }

            return mapper.toViejoDTO(paciente);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar los datos del paciente", ex);
            throw new NegocioException("Error al recuperar los datos del paciente: " + ex.getMessage());
        }
    }

    /**
     * Metodo que encripta la contraseña del usuario.
     *
     * @param contraseña Contraseña que se recibe sin encriptar
     * @return La contraseña encriptada.
     */
    public static String contraseñaHash(String contraseña) {
        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(3, 65536, 1, contraseña);
        argon2.wipeArray(contraseña.toCharArray());
        return hash;
    }

    /**
     * Metodo que valida el formato del correo del usuario.
     *
     * @param correo El correo que se verificara
     * @return True si el formato es valido, False en caso contrario.
     */
    public static boolean validarCorreo(String correo) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import DTO.HorarioMedicoNuevoDTO;
import DTO.MedicoDTO;
import DTO.MedicoNuevoDTO;
import Entidades.HorarioMedico;
import Entidades.Medico;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.HorarioMapper;
import Mappers.MedicoMapper;
import java.util.ArrayList;
import java.util.List;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */

public class MedicoBO {

    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());

    private final IMedicoDAO medicoDAO;

    private final MedicoMapper mapper = new MedicoMapper();

    private final HorarioMapper horariomapper = new HorarioMapper();

    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    public boolean registrarMedico(MedicoNuevoDTO medicoNuevoDTO) throws NegocioException, SQLException, PersistenciaClinicaException {
        // Verificar que el medico no sea nulo.
        if (medicoNuevoDTO == null) {
            throw new NegocioException("El paciente debe tener todos sus datos.");
        }

        // Verificar que ninguno de los datos ingresados sea nulo.
        if (medicoNuevoDTO.getNombres() == null || medicoNuevoDTO.getApellidoPaterno() == null
                || medicoNuevoDTO.getUsuario().getUser() == null || medicoNuevoDTO.getUsuario().getContrasenia() == null
                || medicoNuevoDTO.getUsuario().getRol() == null || medicoNuevoDTO.getUsuario() == null
                || medicoNuevoDTO.getEspecialidad() == null || medicoNuevoDTO.getCedula() == null) {
            throw new NegocioException("Los datos del medico no pueden estar vacios.");
        }

        // Verificar que no existan espacios en blanco.
        if (medicoNuevoDTO.getNombres().trim().isEmpty() || medicoNuevoDTO.getApellidoPaterno().trim().isEmpty()
                || medicoNuevoDTO.getUsuario().getUser().trim().isEmpty() || medicoNuevoDTO.getUsuario().getContrasenia().trim().isEmpty()
                || medicoNuevoDTO.getUsuario().getRol().trim().isEmpty() || medicoNuevoDTO.getEspecialidad().trim().isEmpty()
                || medicoNuevoDTO.getCedula().trim().isEmpty()) {
            throw new NegocioException("Los datos del medico no pueden estar vacios o con espacios en blanco.");
        }

        // Verificar si el nombre de usuario ya ha sido registrado.
        if (medicoDAO.consultarMedicoPorUsuario(medicoNuevoDTO.getUsuario().getUser()) != null) {
            throw new NegocioException("El nombre de usuario ingresado ya esta registrado.");
        }

        // Encriptar la contraseña
        String contraseña = contraseñaHash(medicoNuevoDTO.getUsuario().getContrasenia());
        medicoNuevoDTO.getUsuario().setContrasenia(contraseña);

        // Se convierte el medicoNuevoDTO a entidad Medico
        Medico medicoEntity = mapper.toEntity(medicoNuevoDTO);

        try {
            // Se manda al metodo registrarPaciente de PacienteDAO
            Medico pacienteRegistrado = medicoDAO.registrarMedico(medicoEntity);
            return pacienteRegistrado != null;
        } catch (PersistenciaClinicaException | SQLException e) {
            logger.log(Level.SEVERE, "Error en el registro del medico.", e);
            throw new NegocioException("Error en el registro del medico.");
        }
    }
    public MedicoDTO buscarMedicoPorUsuario(String user) throws NegocioException {
        try {
            Medico medico = medicoDAO.consultarMedicoPorUsuario(user);
            if (medico == null) {
                return null;
            }

            return mapper.toDTO(medico);
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar los datos del medico", ex);
            throw new NegocioException("Error al recuperar los datos del medico: " + ex.getMessage());
        }
    }

    public List<HorarioMedicoNuevoDTO> obtenerHorariosMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            Medico medico = mapper.toEntity(medicoDTO);
            List<HorarioMedico> horarios = medicoDAO.obtenerHorariosMedico(medico);
            List<HorarioMedicoNuevoDTO> horariosDTO = new ArrayList<>();
            for (HorarioMedico horarioMedico : horarios) {
                horariosDTO.add(horariomapper.toDTO(horarioMedico));
            }
            return horariosDTO;
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al obtener horarios: " + ex.getMessage());
        }
    }

    public boolean actualizarEstadoMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            // Convertimos el DTO a dominio para trabajar con el DAO
            Medico medico = mapper.toEntity(medicoDTO);
            boolean actualizado = medicoDAO.ActualizarEstado(medico);
            // Se actualiza el DTO con el nuevo estado
            medicoDTO.setActivo(medico.isActivo());
            return actualizado;
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al actualizar estado del médico: " + ex.getMessage());
        }
    }

    public List<MedicoDTO> consultarMedicoPorEspecialidad(String especialidad) throws NegocioException {
        try {
            // Se obtiene la lista de médicos de la capa de datos
            List<Medico> medicos = medicoDAO.consultarMedicoPorEspecialidad(especialidad);
            // Se convierte cada objeto Medico en MedicoDTO usando el mapper
            List<MedicoDTO> medicosDTO = new ArrayList<>();
            for (Medico medico : medicos) {
                medicosDTO.add(mapper.toDTO(medico));
            }
            return medicosDTO;
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar médicos por especialidad", ex);
            throw new NegocioException("Error al consultar médicos por especialidad: " + ex.getMessage());
        }
    }

    public MedicoDTO consultarMedicoPorID(int idMedico) throws NegocioException {
        try {
            Medico medicoEncontrado = medicoDAO.consultarMedicoPorID(idMedico);
            return mapper.toDTO(medicoEncontrado);
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al consultar médico por ID: " + ex.getMessage());
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
}

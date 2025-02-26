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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class MedicoBO {

    // Logger para registrar eventos y errores.
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());

    // DAOs y mappers necesarios para la lógica de negocio.
    private final IMedicoDAO medicoDAO;  // DAO para la gestión de médicos.
    private final MedicoMapper mapper = new MedicoMapper();  // Mapeador para convertir entre objetos Medico y DTOs.
    private final HorarioMapper horariomapper = new HorarioMapper();  // Mapeador para convertir objetos HorarioMedico a DTOs.

    // Constructor que inicializa el DAO de médicos.
    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);  // Inicializa el DAO de médicos con la conexión a la base de datos.
    }

    // Método para actualizar el estado de un médico (activo/inactivo).
    public boolean actualizarEstadoMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            // Convertir el DTO de médico a entidad Medico.
            Medico medico = mapper.toEntityDTO(medicoDTO);
            // Actualizar el estado del médico en la base de datos.
            boolean actualizado = medicoDAO.ActualizarEstado(medico);
            // Actualizar el DTO con el nuevo estado del médico.
            medicoDTO.setActivo(medico.isActivo());
            return actualizado;  // Devolver si la actualización fue exitosa.
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al actualizar estado del médico: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para buscar un médico por su nombre de usuario.
    public MedicoDTO buscarMedicoPorUsuario(String user) throws NegocioException {
        try {
            // Buscar el médico por su usuario en el DAO.
            Medico medico = medicoDAO.consultarMedicoPorUsuario(user);
            if (medico == null) {
                return null;  // Si no se encuentra el médico, retornar null.
            }
            return mapper.toDTO(medico);  // Convertir el médico a DTO y devolverlo.
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar los datos del medico", ex);
            throw new NegocioException("Error al recuperar los datos del medico: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para buscar un médico para darlo de alta, por su usuario.
    public MedicoDTO buscarMedicoParaAlta(String user) throws NegocioException {
        try {
            // Buscar el médico por su usuario para darlo de alta.
            Medico medico = medicoDAO.consultarMedicoParaAlta(user);
            if (medico == null) {
                return null;  // Si no se encuentra el médico, retornar null.
            }
            return mapper.toDTO(medico);  // Convertir el médico a DTO y devolverlo.
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar los datos del medico", ex);
            throw new NegocioException("Error al recuperar los datos del medico: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para obtener los horarios de un médico.
    public List<HorarioMedicoNuevoDTO> obtenerHorariosMedico(MedicoDTO medicoDTO) throws NegocioException {
        // Validar que el DTO de médico sea válido.
        if (medicoDTO == null || medicoDTO.getIdMedico() <= 0) {
            throw new NegocioException("Error: El médico proporcionado no es válido.");
        }

        try {
            // Convertir el DTO a entidad.
            Medico medico = mapper.toEntityDTO(medicoDTO);

            // Verificar que la conversión haya sido exitosa.
            if (medico == null || medico.getIdMedico() <= 0) {
                throw new NegocioException("Error: Conversión de DTO a entidad fallida.");
            }

            // Obtener los horarios del médico desde la base de datos.
            List<HorarioMedico> horarios = medicoDAO.obtenerHorariosMedico(medico);
            List<HorarioMedicoNuevoDTO> horariosDTO = new ArrayList<>();

            // Convertir cada horario a su correspondiente DTO.
            for (HorarioMedico horarioMedico : horarios) {
                horariosDTO.add(horariomapper.toDTO(horarioMedico));
            }
            return horariosDTO;  // Devolver la lista de horarios en formato DTO.

        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al obtener horarios: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para consultar médicos por especialidad.
    public List<MedicoDTO> consultarMedicoPorEspecialidad(String especialidad) throws NegocioException {
        try {
            // Consultar los médicos por especialidad en el DAO.
            List<Medico> medicos = medicoDAO.consultarMedicoPorEspecialidad(especialidad);
            List<MedicoDTO> medicosDTO = new ArrayList<>();
            
            // Convertir cada objeto Medico a MedicoDTO usando el mapper.
            for (Medico medico : medicos) {
                medicosDTO.add(mapper.toDTO(medico));
            }
            return medicosDTO;  // Devolver la lista de médicos en formato DTO.
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar médicos por especialidad", ex);
            throw new NegocioException("Error al consultar médicos por especialidad: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para consultar un solo médico por especialidad.
    public MedicoDTO consultarUnMedicoPorEspecialidad(String Especialidad) throws NegocioException {
        try {
            // Consultar un médico por especialidad.
            Medico medico = medicoDAO.consultarUnMedicoPorEspecialidad(Especialidad);
            return mapper.toDTO(medico);  // Convertir el médico a MedicoDTO y devolverlo.
        } catch (PersistenciaClinicaException ex) {
            logger.log(Level.SEVERE, "Error al consultar médicos por especialidad", ex);
            throw new NegocioException("Error al consultar médicos por especialidad: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    // Método para consultar un médico por su ID.
    public MedicoDTO consultarMedicoPorID(int idMedico) throws NegocioException {
        try {
            // Consultar el médico por ID.
            Medico medicoEncontrado = medicoDAO.consultarMedicoPorID(idMedico);
            return mapper.toDTO(medicoEncontrado);  // Convertir el médico encontrado a MedicoDTO y devolverlo.
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al consultar médico por ID: " + ex.getMessage());  // Manejo de errores de persistencia.
        }
    }

    /**
     * Método que encripta la contraseña del usuario usando Argon2.
     *
     * @param contraseña Contraseña que se recibe sin encriptar.
     * @return La contraseña encriptada.
     */
    public static String contraseñaHash(String contraseña) {
        Argon2 argon2 = Argon2Factory.create();  // Crear una instancia de Argon2.
        String hash = argon2.hash(3, 65536, 1, contraseña);  // Encriptar la contraseña.
        argon2.wipeArray(contraseña.toCharArray());  // Limpiar los datos de la contraseña.
        return hash;  // Devolver la contraseña encriptada.
    }
}
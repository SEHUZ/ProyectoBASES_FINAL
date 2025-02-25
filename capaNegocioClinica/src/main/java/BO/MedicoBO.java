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

    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());

    private final IMedicoDAO medicoDAO;

    private final MedicoMapper mapper = new MedicoMapper();

    private final HorarioMapper horariomapper = new HorarioMapper();

    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    public boolean actualizarEstadoMedico(MedicoDTO medicoDTO) throws NegocioException {
        try {
            // Convertimos el DTO a entidad para trabajar con el DAO
            Medico medico = mapper.toEntityDTO(medicoDTO);
            boolean actualizado = medicoDAO.ActualizarEstado(medico);
            // Se actualiza el DTO con el nuevo estado
            medicoDTO.setActivo(medico.isActivo());
            return actualizado;
        } catch (PersistenciaClinicaException ex) {
            throw new NegocioException("Error al actualizar estado del médico: " + ex.getMessage());
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
    
    public MedicoDTO buscarMedicoParaAlta(String user) throws NegocioException {
        try {
            Medico medico = medicoDAO.consultarMedicoParaAlta(user);
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
        if (medicoDTO == null || medicoDTO.getIdMedico() <= 0) {
            throw new NegocioException("Error: El médico proporcionado no es válido.");
        }

        try {
            System.out.println("MedicoDTO recibido: " + medicoDTO);
            Medico medico = mapper.toEntityDTO(medicoDTO);
            System.out.println("Medico convertido a entidad: " + medico);

            if (medico == null || medico.getIdMedico() <= 0) {
                throw new NegocioException("Error: Conversión de DTO a entidad fallida.");
            }

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
    
    public MedicoDTO consultarUnMedicoPorEspecialidad(String Especialidad) throws NegocioException {
        try {
            // Se obtiene el medico
            Medico medico = medicoDAO.consultarUnMedicoPorEspecialidad(Especialidad);
            // Se convierte el medico a medicoDTO
            MedicoDTO medicoEncontrado = mapper.toDTO(medico);
            
            return medicoEncontrado;
            
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

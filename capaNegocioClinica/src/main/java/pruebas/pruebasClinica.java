/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import BO.CitaBO;
import BO.MedicoBO;
import DAO.PacienteDAO;
import Exception.NegocioException;
import Mappers.CitaMapper;
import configuracion.DependencyInjector;
import java.util.logging.Logger;

/**
 * Clase de prueba para realizar operaciones relacionadas con la clínica.
 * Esta clase utiliza los objetos de negocio MedicoBO y CitaBO para realizar
 * pruebas de funcionalidad en el sistema.
 * 
 * @author Daniel M
 */
public class pruebasClinica {

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());
    
    private final CitaMapper mapper = new CitaMapper();

    /**
     * Método principal que ejecuta las pruebas de la clínica.
     *
     * @param args Los argumentos de línea de comandos (no se utilizan en esta prueba).
     * @throws NegocioException Si ocurre un error relacionado con las reglas de negocio.
     */
    public static void main(String[] args) throws NegocioException {

        MedicoBO medicoBO = DependencyInjector.crearMedicoBO();
        CitaBO citaBO = DependencyInjector.crearCitaBO();

//        try {
//            MedicoDTO medico = medicoBO.consultarMedicoPorID(1);
//            System.out.println("Médico obtenido: " + medico);
//
//            if (medico == null) {
//                System.out.println("El médico no fue encontrado en la base de datos.");
//            } else if (medico.getIdMedico() <= 0) {
//                System.out.println("El ID del médico es inválido.");
//            } else {
//                System.out.println(medicoBO.obtenerHorariosMedico(medico));
//            }
//
//        } catch (NegocioException ex) {
//            logger.log(Level.SEVERE, "Error al consultar el médico: " + ex);
//            ex.printStackTrace();
//        }
            
        }
        
        
    }

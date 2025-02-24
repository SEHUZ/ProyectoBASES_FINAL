/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import BO.MedicoBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DTO.HorarioMedicoNuevoDTO;
import DTO.MedicoDTO;
import Entidades.Medico;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import configuracion.DependencyInjector;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel M
 */
public class pruebasClinica {

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MedicoBO medicoBO = DependencyInjector.crearMedicoBO();

        try {
            MedicoDTO medico = medicoBO.consultarMedicoPorID(1);
            System.out.println("Médico obtenido: " + medico);

            if (medico == null) {
                System.out.println("El médico no fue encontrado en la base de datos.");
            } else if (medico.getIdMedico() <= 0) {
                System.out.println("El ID del médico es inválido.");
            } else {
                System.out.println(medicoBO.obtenerHorariosMedico(medico));
            }

        } catch (NegocioException ex) {
            logger.log(Level.SEVERE, "Error al consultar el médico: " + ex);
            ex.printStackTrace();
        }
    }

}

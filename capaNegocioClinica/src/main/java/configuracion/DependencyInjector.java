/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import BO.CitaBO;
import BO.ConsultaBO;
import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;

/**
 * Clase que se encarga de la inyección de dependencias para los objetos de negocio (BO).
 * Proporciona métodos estáticos para crear instancias de los diferentes BOs, 
 * asegurando que cada uno tenga su propia conexión a la base de datos.
 * 
 * @author Daniel M
 */
public class DependencyInjector {
    
    /**
     * Crea una instancia de PacienteBO con una nueva conexión a la base de datos.
     *
     * @return Una nueva instancia de PacienteBO.
     */

    public static PacienteBO crearPacienteBO() {
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);

        return pacienteBO;
    }
    
    /**
     * Crea una instancia de MedicoBO con una nueva conexión a la base de datos.
     *
     * @return Una nueva instancia de MedicoBO.
     */
    
    public static MedicoBO crearMedicoBO() {
        IConexionBD conexion = new ConexionBD();
        MedicoBO medicoBO = new MedicoBO(conexion);

        return medicoBO;
    }
    
    /**
     * Crea una instancia de UsuarioBO con una nueva conexión a la base de datos.
     *
     * @return Una nueva instancia de UsuarioBO.
     */
    
    public static UsuarioBO crearUsuarioBO() {
        IConexionBD conexion = new ConexionBD();
        UsuarioBO usuarioBO = new UsuarioBO(conexion);

        return usuarioBO;
    }
    
    /**
     * Crea una instancia de CitaBO con una nueva conexión a la base de datos.
     *
     * @return Una nueva instancia de CitaBO.
     */
    
    public static CitaBO crearCitaBO() {
        IConexionBD conexion = new ConexionBD();
        CitaBO citaBO = new CitaBO(conexion);

        return citaBO;
    }
    
    /**
     * Crea una instancia de ConsultaBO con una nueva conexión a la base de datos.
     *
     * @return Una nueva instancia de ConsultaBO.
     */
    
     public static ConsultaBO crearConsultaBO() {
        // Crear la conexión
        IConexionBD conexion = new ConexionBD();

        // Crear el BO, que automáticamente creará el DAO con la conexión
        ConsultaBO consultaBO = new ConsultaBO(conexion);

        return consultaBO;
    }
    
}

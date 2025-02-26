/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.CitaBO;
import DTO.CitaViejaDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Mappers.PacienteMapper;
import configuracion.DependencyInjector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
public class dashboardPaciente extends javax.swing.JFrame {

    private CitaBO citaBO = DependencyInjector.crearCitaBO();

    private PacienteViejoDTO paciente;

    private agendarCitaEmergencia agendarCitaEmergencia;
    private agendarCita agendarCita;
    private iniciarSesion ventanaInicio;
    private editarPerfilPaciente ventanaEditarPerfil;

    private agendarCitaEmergencia ventanaAgendarCitaEmergencia;
    private agendarCita ventanaAgendarCita;
    private listaCitasProximas ventanaCitasProximas;
    private historialConsultas ventanaHistorialConsultas;
    private PacienteMapper mapperPaciente = new PacienteMapper();
    historialConsultas historialConsultas;
    /**
     * Creates new form dashboardPaciente
     *
     * @param paciente
     */
    public dashboardPaciente(PacienteViejoDTO paciente) {
        this.paciente = paciente;
        Paciente pacienteNormal = mapperPaciente.toEntityViejo(paciente);
        this.historialConsultas = new historialConsultas(pacienteNormal);
        initComponents();
        cargarDatosPaciente(paciente);
    }

    public dashboardPaciente() {
        initComponents();
    }
//
    public void setVentanaInicio(iniciarSesion ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public void setVentanaEditarPerfil(editarPerfilPaciente ventanaEditarPerfil) {
        this.ventanaEditarPerfil = ventanaEditarPerfil;
    }

    public void setAgendarCitaEmergencia(agendarCitaEmergencia ventanaAgendarCitaEmergencia) {
        this.ventanaAgendarCitaEmergencia = ventanaAgendarCitaEmergencia;
    }

    public void setAgendarCita(agendarCita agendarcita) {
        this.agendarCita = agendarcita;
    }

    public void setVentanaCitasProximas(listaCitasProximas ventanaCitasProximas) {
        this.ventanaCitasProximas = ventanaCitasProximas;
    }

    public void setVentanaHistorialConsultas(historialConsultas ventanaHistorialConsultas) {
        this.ventanaHistorialConsultas = ventanaHistorialConsultas;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonEditarPerfil = new javax.swing.JButton();
        botonCerrarSesion = new javax.swing.JButton();
        BotonConsultaEmergencia = new javax.swing.JButton();
        botonAgendarCita = new javax.swing.JButton();
        botonCitasProximas = new javax.swing.JButton();
        botonHistorialConsultas = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblCalle = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblCodigoPostal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(632, 757));

        BotonEditarPerfil.setText("Editar perfil");
        BotonEditarPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEditarPerfilActionPerformed(evt);
            }
        });

        botonCerrarSesion.setText("Cerrar sesion");
        botonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarSesionActionPerformed(evt);
            }
        });

        BotonConsultaEmergencia.setText("Agendar cita de emergencia");
        BotonConsultaEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonConsultaEmergenciaActionPerformed(evt);
            }
        });

        botonAgendarCita.setText("Agendar una cita");
        botonAgendarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgendarCitaActionPerformed(evt);
            }
        });

        botonCitasProximas.setText("Ver citas proximas");
        botonCitasProximas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCitasProximasActionPerformed(evt);
            }
        });

        botonHistorialConsultas.setText("Ver historial de consultas");
        botonHistorialConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHistorialConsultasActionPerformed(evt);
            }
        });

        lblNombre.setText("nombre");

        lblApellidoPaterno.setText("apellidopaterno");

        lblApellidoMaterno.setText("apellidomaterno");

        lblRol.setText("rol");

        lblTelefono.setText("telefono");

        lblCalle.setText("calle");

        lblNumero.setText("numero");

        lblCodigoPostal.setText("codigopostal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BotonEditarPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15)))
                    .addComponent(lblTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblRol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblApellidoPaterno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblApellidoMaterno))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblCalle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigoPostal)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 227, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonAgendarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonConsultaEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(216, 216, 216))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonHistorialConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCitasProximas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(241, 241, 241))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblApellidoPaterno)
                    .addComponent(lblApellidoMaterno)
                    .addComponent(lblRol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalle)
                    .addComponent(lblNumero)
                    .addComponent(lblCodigoPostal))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(BotonEditarPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(BotonConsultaEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonAgendarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonCitasProximas, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonHistorialConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonEditarPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEditarPerfilActionPerformed
        abrirVentanaEditarPerfil(paciente);
    }//GEN-LAST:event_BotonEditarPerfilActionPerformed

    private void botonCitasProximasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCitasProximasActionPerformed
        abrirVentanaCitasProximas(paciente);
    }//GEN-LAST:event_botonCitasProximasActionPerformed

    private void botonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_botonCerrarSesionActionPerformed

    private void BotonConsultaEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConsultaEmergenciaActionPerformed
        abrirVentanaCitaEmergencia(paciente);
    }//GEN-LAST:event_BotonConsultaEmergenciaActionPerformed

    private void botonAgendarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgendarCitaActionPerformed
        abrirVentanaCitas(paciente);
    }//GEN-LAST:event_botonAgendarCitaActionPerformed

    private void botonHistorialConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHistorialConsultasActionPerformed
        abrirVentanaHistorialConsultas();
        historialConsultas.cargarHistorialConsultas();
    }//GEN-LAST:event_botonHistorialConsultasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboardPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonConsultaEmergencia;
    private javax.swing.JButton BotonEditarPerfil;
    private javax.swing.JButton botonAgendarCita;
    private javax.swing.JButton botonCerrarSesion;
    private javax.swing.JButton botonCitasProximas;
    private javax.swing.JButton botonHistorialConsultas;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblCalle;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTelefono;
    // End of variables declaration//GEN-END:variables

    public void actualizarDatos(Paciente paciente) {
        lblNombre.setText("Nombres: " + paciente.getNombres());
        lblApellidoPaterno.setText("Apellido Materno:" + paciente.getApellidoPaterno());
        lblApellidoMaterno.setText("Apellido Materno:" + paciente.getApellidoMaterno());
        lblTelefono.setText("Telefono" + paciente.getTelefono());
        lblRol.setText("Rol: " + paciente.getUsuario().getRol());
        lblCalle.setText("Calle" + paciente.getDireccion().getCalle());
        lblNumero.setText("Numero: " + paciente.getDireccion().getNumero());
        lblCodigoPostal.setText("CP: " + paciente.getDireccion().getCodigoPostal());
    }

    private void cargarDatosPaciente(PacienteViejoDTO paciente) {
        lblRol.setText("Paciente:");
        lblNombre.setText(paciente.getNombres());
        lblApellidoPaterno.setText(paciente.getApellidoPaterno());
        lblApellidoMaterno.setText(paciente.getApellidoMaterno());
        lblTelefono.setText("Telefono: " + paciente.getTelefono());
        lblCalle.setText("Dirección: " + paciente.getDireccion().getCalle());
        lblNumero.setText(paciente.getDireccion().getNumero());
        lblCodigoPostal.setText(paciente.getDireccion().getCodigoPostal());
    }

    public void abrirVentanaEditarPerfil(PacienteViejoDTO paciente) {
        if (ventanaEditarPerfil == null) {
            ventanaEditarPerfil = new editarPerfilPaciente(paciente);
        }

        ventanaEditarPerfil = new editarPerfilPaciente(paciente);
        ventanaEditarPerfil.setVentanaPaciente(this);
        ventanaEditarPerfil.setLocationRelativeTo(null);
        ventanaEditarPerfil.setVisible(true);
        this.dispose();
    }

    public void abrirVentanaCitaEmergencia(PacienteViejoDTO paciente) {
        if (ventanaAgendarCitaEmergencia == null) {
            ventanaAgendarCitaEmergencia = new agendarCitaEmergencia(paciente);
        }

        ventanaAgendarCitaEmergencia = new agendarCitaEmergencia(paciente);
        ventanaAgendarCitaEmergencia.setVentanaPaciente(this);
        ventanaAgendarCitaEmergencia.setLocationRelativeTo(null);
        ventanaAgendarCitaEmergencia.setVisible(true);
        this.dispose();
    }

    public void abrirVentanaCitas(PacienteViejoDTO paciente) {
        if (ventanaAgendarCita == null) {
            ventanaAgendarCita = new agendarCita(paciente);
        }

        ventanaAgendarCita = new agendarCita(paciente);
        ventanaAgendarCita.setVentanaPaciente(this);
        ventanaAgendarCita.setLocationRelativeTo(null);
        ventanaAgendarCita.setVisible(true);
        this.dispose();
    }

    public void abrirVentanaCitasProximas(PacienteViejoDTO paciente) {
        List<CitaViejaDTO> citas;
        try {
            citas = citaBO.consultarCitasProximasPaciente(paciente);
            if (citas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tiene citas próximas actualmente.");
                return;
            }
        } catch (NegocioException ex) {
            Logger.getLogger(dashboardPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ventanaCitasProximas == null) {
            ventanaCitasProximas = new listaCitasProximas(paciente);
        }

        ventanaCitasProximas = new listaCitasProximas(paciente);
        ventanaCitasProximas.setVentanaPaciente(this);
        ventanaCitasProximas.setLocationRelativeTo(null);
        ventanaCitasProximas.setVisible(true);
        this.dispose();
    }

    public void abrirVentanaHistorialConsultas() {
        Paciente pacienteNormal = mapperPaciente.toEntityViejo(paciente);
        if (ventanaHistorialConsultas == null) {
            ventanaHistorialConsultas = new historialConsultas(pacienteNormal);
        }

        ventanaHistorialConsultas = new historialConsultas(pacienteNormal);
        ventanaHistorialConsultas.setVentanaPaciente(this);
        ventanaHistorialConsultas.setLocationRelativeTo(null);
        ventanaHistorialConsultas.setVisible(true);
        this.dispose();
    }

    public void cerrarSesion() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Cerrar sesión?",
                "Cierre de sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            ventanaInicio.setVentanaPaciente(this);
            ventanaInicio.setLocationRelativeTo(null);
            ventanaInicio.setVisible(true);
            this.dispose();

        } else {
            System.out.println("El usuario canceló.");
        }
    }

}

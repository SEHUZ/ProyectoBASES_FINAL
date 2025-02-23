/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import DTO.MedicoDTO;
import DTO.PacienteViejoDTO;
import Exception.NegocioException;
import configuracion.DependencyInjector;
import java.text.Normalizer;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
public class iniciarSesion extends javax.swing.JFrame {

    private registrarUsuario ventanaRegistro;
    private dashBoardMedico ventanaMedico;
    private dashboardPaciente ventanaPaciente;

    private PacienteBO pacienteBO = DependencyInjector.crearPacienteBO();
    private MedicoBO medicoBO = DependencyInjector.crearMedicoBO();
    private UsuarioBO usuarioBO = DependencyInjector.crearUsuarioBO();

    /**
     * Creates new form iniciarSesion
     */
    public iniciarSesion() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonIniciarSesion1 = new javax.swing.JButton();
        BotonRegistrarse1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fieldUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BotonRegistrarse = new javax.swing.JButton();
        BotonIniciarSesion = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        BotonSalir = new javax.swing.JButton();
        JFieldContrasenia = new javax.swing.JPasswordField();

        BotonIniciarSesion1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BotonIniciarSesion1.setText("Iniciar Sesion");
        BotonIniciarSesion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIniciarSesion1ActionPerformed(evt);
            }
        });

        BotonRegistrarse1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BotonRegistrarse1.setText("Registrarse");
        BotonRegistrarse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegistrarse1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(632, 757));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("CLINICA PRIVADA SAN JOSE");

        fieldUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUsuarioActionPerformed(evt);
            }
        });

        jLabel2.setText("Usuario");

        jLabel3.setText("Contraseña");

        BotonRegistrarse.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BotonRegistrarse.setText("Registrarse");
        BotonRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegistrarseActionPerformed(evt);
            }
        });

        BotonIniciarSesion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BotonIniciarSesion.setText("Iniciar Sesion");
        BotonIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIniciarSesionActionPerformed(evt);
            }
        });

        jLabel4.setText("No tienes una cuenta?");

        BotonSalir.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BotonSalir.setText("Salir");
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BotonIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BotonRegistrarse))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BotonSalir)
                                .addGap(102, 102, 102))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(JFieldContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)))
                        .addGap(179, 179, 179))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JFieldContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(BotonIniciarSesion)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonRegistrarse)
                .addGap(49, 49, 49)
                .addComponent(BotonSalir)
                .addGap(84, 84, 84))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setVentanaRegistro(registrarUsuario ventanaRegistro) {
        this.ventanaRegistro = ventanaRegistro;
    }

    public void setVentanaMedico(dashBoardMedico ventanaMedico) {
        this.ventanaMedico = ventanaMedico;
    }

    public void setVentanaPaciente(dashboardPaciente ventanaPaciente) {
        this.ventanaPaciente = ventanaPaciente;
    }
    
    private void BotonRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegistrarseActionPerformed
        abrirPantallaRegistro();
    }//GEN-LAST:event_BotonRegistrarseActionPerformed

    private void BotonIniciarSesion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIniciarSesion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonIniciarSesion1ActionPerformed

    private void BotonIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIniciarSesionActionPerformed
        iniciarSesionUsuario();
    }//GEN-LAST:event_BotonIniciarSesionActionPerformed

    private void BotonRegistrarse1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegistrarse1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonRegistrarse1ActionPerformed

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void fieldUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldUsuarioActionPerformed

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
            java.util.logging.Logger.getLogger(iniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(iniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(iniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(iniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new iniciarSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonIniciarSesion;
    private javax.swing.JButton BotonIniciarSesion1;
    private javax.swing.JButton BotonRegistrarse;
    private javax.swing.JButton BotonRegistrarse1;
    private javax.swing.JButton BotonSalir;
    private javax.swing.JPasswordField JFieldContrasenia;
    private javax.swing.JTextField fieldUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

    public void iniciarSesionUsuario() {
        String user = fieldUsuario.getText();
        String contrasenia = JFieldContrasenia.getText();

        try {
            String rolRaw = usuarioBO.login(user, contrasenia);

            if (rolRaw != null) {
                String rol = Normalizer.normalize(rolRaw, Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", "")
                        .toLowerCase();

                switch (rol) {
                    case "paciente":
                        PacienteViejoDTO paciente = pacienteBO.buscarPacientePorUsuario(user);
                        if (ventanaPaciente == null) {
                            ventanaPaciente = new dashboardPaciente(paciente);
                        }
                        
                        ventanaPaciente = new dashboardPaciente(paciente);
                        ventanaPaciente.setVentanaInicio(this);
                        ventanaPaciente.setLocationRelativeTo(null);
                        ventanaPaciente.setVisible(true);
                        this.setVisible(false);
                        limpiarCampos();

                        break;
                    case "medico":
                        MedicoDTO medico = medicoBO.buscarMedicoPorUsuario(user);
                        if(ventanaMedico == null){
                            ventanaMedico = new dashBoardMedico(medico);
                        }
                        
                        ventanaMedico = new dashBoardMedico(medico);
                        ventanaMedico.setVentanaInicio(this);
                        ventanaPaciente.setLocationRelativeTo(null);
                        ventanaPaciente.setVisible(true);
                        this.setVisible(false);
                        limpiarCampos();
                        
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Rol no reconocido.");
                        break;

                }
            } else {
                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
        }
    }

    public void abrirPantallaRegistro() {
        if (ventanaRegistro == null) {
            ventanaRegistro = new registrarUsuario();
        }

        ventanaRegistro.setVentanaInicio(this);
        ventanaRegistro.setLocationRelativeTo(null);
        ventanaRegistro.setVisible(true);
        this.setVisible(false);
    }

    public void limpiarCampos() {
        // Limpiar campos de texto
        fieldUsuario.setText("");
        JFieldContrasenia.setText("");

    }
}

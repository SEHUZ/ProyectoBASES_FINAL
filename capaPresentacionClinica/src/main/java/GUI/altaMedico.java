/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.MedicoBO;
import BO.UsuarioBO;
import DTO.MedicoDTO;
import Exception.NegocioException;
import configuracion.DependencyInjector;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel M
 */
public class altaMedico extends javax.swing.JFrame {

    private MedicoBO medicoBO = DependencyInjector.crearMedicoBO();
    private UsuarioBO usuarioBO = DependencyInjector.crearUsuarioBO();
    private iniciarSesion ventanaInicio;

    /**
     * Creates new form altaMedico
     */
    public altaMedico() {
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

        jLabel1 = new javax.swing.JLabel();
        fieldContrasenia = new javax.swing.JPasswordField();
        fieldUsuarioMedico = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        botonDarAlta = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("ALTA MÉDICO");

        fieldUsuarioMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUsuarioMedicoActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre de usuario del médico");

        jLabel3.setText("Contraseña");

        botonDarAlta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        botonDarAlta.setText("Dar de alta");
        botonDarAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDarAltaActionPerformed(evt);
            }
        });

        botonVolver.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        botonVolver.setText("Volver");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(197, 197, 197))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldUsuarioMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(fieldContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(181, 181, 181))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonDarAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(237, 237, 237))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonVolver)
                        .addGap(267, 267, 267))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addGap(111, 111, 111)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldUsuarioMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(botonDarAlta)
                .addGap(26, 26, 26)
                .addComponent(botonVolver)
                .addContainerGap(210, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setVentanaInicio(iniciarSesion ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    private void fieldUsuarioMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUsuarioMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldUsuarioMedicoActionPerformed

    private void botonDarAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDarAltaActionPerformed
        darAltaMedico();
    }//GEN-LAST:event_botonDarAltaActionPerformed

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        volverInicioSesion();
    }//GEN-LAST:event_botonVolverActionPerformed

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
            java.util.logging.Logger.getLogger(altaMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(altaMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(altaMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(altaMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new altaMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonDarAlta;
    private javax.swing.JButton botonVolver;
    private javax.swing.JPasswordField fieldContrasenia;
    private javax.swing.JTextField fieldUsuarioMedico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
public void darAltaMedico() {
        // Obtener el nombre de usuario y la contraseña ingresados por el médico
        String user = fieldUsuarioMedico.getText();
        String contrasenia = fieldContrasenia.getText();

        // Verificar que los campos no estén vacíos
        if (user.isBlank() || contrasenia.isBlank()) {
            JOptionPane.showMessageDialog(this, "Ingrese los datos requeridos.");
            return;
        }

        try {
            // Buscar si el médico existe en la base de datos
            if (medicoBO.buscarMedicoParaAlta(user) != null) {
                // Verificar si las credenciales del médico son correctas
                if (usuarioBO.loginMedico(user, contrasenia)) {
                    // Obtener el objeto MedicoDTO correspondiente al usuario ingresado
                    MedicoDTO medico = medicoBO.buscarMedicoParaAlta(user);

                    // Crear un nuevo objeto MedicoDTO con el estado actualizado (activo)
                    MedicoDTO altaMedico = new MedicoDTO(
                            medico.getIdMedico(),
                            medico.getUsuario(),
                            medico.getNombres(),
                            medico.getApellidoPaterno(),
                            medico.getApellidoMaterno(),
                            medico.getCedula(),
                            medico.getEspecialidad(),
                            true // Se cambia el estado a activo
                    );

                    // Actualizar el estado del médico en la base de datos
                    medicoBO.actualizarEstadoMedico(altaMedico);

                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(this, "Médico dado de alta.");

                    // Limpiar los campos de entrada
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        } catch (NegocioException ex) {
            // Manejo de excepción en caso de error en la lógica de negocio
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Método para volver a la ventana de inicio de sesión.
     */
    public void volverInicioSesion() {
        // Si la ventana de inicio no está creada, se instancia
        if (ventanaInicio == null) {
            ventanaInicio = new iniciarSesion();
        }

        // Crear una nueva instancia de la ventana de inicio de sesión
        ventanaInicio = new iniciarSesion();

        // Establecer la referencia de la ventana actual en la de inicio de sesión
        ventanaInicio.setventanaAltaMedico(this);

        // Centrar la ventana en la pantalla
        ventanaInicio.setLocationRelativeTo(null);

        // Hacer visible la ventana de inicio de sesión
        ventanaInicio.setVisible(true);

        // Cerrar la ventana actual
        this.dispose();
    }

    /**
     * Método para limpiar los campos de entrada de usuario y contraseña.
     */
    public void limpiarCampos() {
        // Limpiar campos de texto
        fieldUsuarioMedico.setText("");
        fieldContrasenia.setText("");
    }
}

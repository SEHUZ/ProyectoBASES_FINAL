/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.MedicoBO;
import DTO.HorarioMedicoNuevoDTO;
import DTO.MedicoDTO;
import DTO.PacienteViejoDTO;
import Exception.NegocioException;
import configuracion.DependencyInjector;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
public class dashBoardMedico extends javax.swing.JFrame {

    private MedicoDTO medico;
    private MedicoBO medicoBO = DependencyInjector.crearMedicoBO();
    private iniciarSesion ventanaInicio;
    private agendaDeCitas ventanaAgendaDeCitas;

    public dashBoardMedico(MedicoDTO medico) {
        this.medico = medico;
        initComponents();
        cargarDatosMedico(medico);
        cargarHorariosMedico(medico);
    }

    /**
     * Creates new form dashBoardMedico
     */
    public dashBoardMedico() {
        initComponents();
    }

    public void setVentanaInicio(iniciarSesion ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public void setVentanaAgendaDeCitas(agendaDeCitas ventanaAgendaDeCitas) {
        this.ventanaAgendaDeCitas = ventanaAgendaDeCitas;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonBajaTemporal = new javax.swing.JButton();
        botonCerrarSesion = new javax.swing.JButton();
        botonAgendadeCitas = new javax.swing.JButton();
        lblEspecialidad = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaHorarios = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(632, 757));

        BotonBajaTemporal.setText("Baja temporal");
        BotonBajaTemporal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBajaTemporalActionPerformed(evt);
            }
        });

        botonCerrarSesion.setText("Cerrar sesion");
        botonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarSesionActionPerformed(evt);
            }
        });

        botonAgendadeCitas.setText("Ver agenda de citas");
        botonAgendadeCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgendadeCitasActionPerformed(evt);
            }
        });

        lblEspecialidad.setText("Especialidad");

        lblCedula.setText("cedula");

        lblRol.setText("rol");

        lblNombre.setText("nombre");

        lblApellidoPaterno.setText("apellidopaterno");

        lblApellidoMaterno.setText("apellidomaterno");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("HORARIOS DE ATENCION");

        jTextAreaHorarios.setEditable(false);
        jTextAreaHorarios.setColumns(20);
        jTextAreaHorarios.setRows(5);
        jScrollPane1.setViewportView(jTextAreaHorarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonBajaTemporal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEspecialidad)
                            .addComponent(lblCedula)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblRol)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblApellidoPaterno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblApellidoMaterno))))
                        .addGap(0, 199, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAgendadeCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(lblCedula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEspecialidad)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonBajaTemporal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(botonAgendadeCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonBajaTemporalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBajaTemporalActionPerformed
        darBajaMedico(medico);
    }//GEN-LAST:event_BotonBajaTemporalActionPerformed

    private void botonAgendadeCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgendadeCitasActionPerformed
        abrirVentanaAgendaDeCitas(medico);
    }//GEN-LAST:event_botonAgendadeCitasActionPerformed

    private void botonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_botonCerrarSesionActionPerformed

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
            java.util.logging.Logger.getLogger(dashBoardMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashBoardMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashBoardMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashBoardMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashBoardMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonBajaTemporal;
    private javax.swing.JButton botonAgendadeCitas;
    private javax.swing.JButton botonCerrarSesion;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaHorarios;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRol;
    // End of variables declaration//GEN-END:variables

    /**
     * Método para dar de baja temporal a un médico. Se solicita confirmación al
     * usuario antes de proceder con la baja. Si el médico acepta, se actualiza
     * su estado a inactivo.
     */
    public void darBajaMedico(MedicoDTO medico) {
        // Mostrar un cuadro de confirmación al usuario
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "No podrás ofrecer servicios hasta que vuelvas a activar tu cuenta.",
                "¿Dar baja temporal?",
                JOptionPane.YES_NO_OPTION
        );

        // Si el usuario acepta la baja
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                // Crear un nuevo objeto MedicoDTO con el estado actualizado (inactivo)
                MedicoDTO bajaMedico = new MedicoDTO(
                        medico.getIdMedico(),
                        medico.getUsuario(),
                        medico.getNombres(),
                        medico.getApellidoPaterno(),
                        medico.getApellidoMaterno(),
                        medico.getCedula(),
                        medico.getEspecialidad(),
                        false // Se cambia el estado a inactivo
                );

                // Actualizar el estado del médico en la base de datos
                medicoBO.actualizarEstadoMedico(bajaMedico);

                // Mostrar mensaje de confirmación
                JOptionPane.showMessageDialog(this, "Has sido dado de baja. Para volver a ofrecer servicios, tendrás que activar tu cuenta.");

                // Redirigir a la ventana de inicio de sesión
                ventanaInicio.setVentanaMedico(this);
                ventanaInicio.setLocationRelativeTo(null);
                ventanaInicio.setVisible(true);
                this.dispose(); // Cerrar la ventana actual
            } catch (NegocioException ex) {
                // Manejo de error en caso de fallo en la lógica de negocio
                JOptionPane.showMessageDialog(this, "Ocurrió un error en el proceso de baja: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("El usuario canceló.");
        }
    }

    /**
     * Método para cerrar la sesión del usuario actual. Se solicita confirmación
     * antes de cerrar sesión.
     */
    public void cerrarSesion() {
        // Mostrar cuadro de confirmación al usuario
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Cerrar sesión?",
                "Cierre de sesión",
                JOptionPane.YES_NO_OPTION
        );

        // Si el usuario acepta cerrar sesión
        if (respuesta == JOptionPane.YES_OPTION) {
            // Redirigir a la ventana de inicio de sesión
            ventanaInicio.setVentanaMedico(this);
            ventanaInicio.setLocationRelativeTo(null);
            ventanaInicio.setVisible(true);
            this.dispose(); // Cerrar la ventana actual
        } else {
            System.out.println("El usuario canceló.");
        }
    }

    /**
     * Método para cargar los datos de un médico en la interfaz gráfica. Se
     * actualizan las etiquetas con la información del médico.
     */
    private void cargarDatosMedico(MedicoDTO medico) {
        lblRol.setText("Médico:");
        lblNombre.setText(medico.getNombres());
        lblApellidoPaterno.setText(medico.getApellidoPaterno());
        lblApellidoMaterno.setText(medico.getApellidoMaterno());
        lblCedula.setText(medico.getCedula());
        lblEspecialidad.setText(medico.getEspecialidad());
    }

    /**
     * Método para cargar y mostrar los horarios de un médico. Se obtiene la
     * lista de horarios del médico y se formatea para su visualización.
     */
    public void cargarHorariosMedico(MedicoDTO medico) {
        try {
            // Obtener la lista de horarios del médico desde la base de datos
            List<HorarioMedicoNuevoDTO> horarios = medicoBO.obtenerHorariosMedico(medico);

            // Verificar si el médico no tiene horarios registrados
            if (horarios.isEmpty()) {
                jTextAreaHorarios.setText("El médico no tiene horarios registrados");
                return;
            }

            // Formateador para la hora en formato HH:mm
            DateTimeFormatter formateadorHora = DateTimeFormatter.ofPattern("HH:mm");

            // Construcción del texto a mostrar en la interfaz
            StringBuilder horariosTexto = new StringBuilder();
            horariosTexto.append("Horarios del Dr. ")
                    .append(medico.getNombres())
                    .append(" ")
                    .append(medico.getApellidoPaterno())
                    .append("\n\n");

            // Recorrer los horarios y agregarlos al texto
            for (HorarioMedicoNuevoDTO horario : horarios) {
                horariosTexto.append("• ")
                        .append(horario.getDiaSemana())
                        .append(": ")
                        .append(horario.getHoraEntrada().format(formateadorHora))
                        .append(" - ")
                        .append(horario.getHoraSalida().format(formateadorHora))
                        .append("\n");
            }

            // Mostrar los horarios en el JTextArea
            jTextAreaHorarios.setText(horariosTexto.toString());
            jTextAreaHorarios.setCaretPosition(0); // Mover el scroll al inicio

        } catch (NegocioException ex) {
            // Manejo de error en caso de fallo en la obtención de horarios
            JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            JOptionPane.showMessageDialog(this, "Error inesperado al mostrar horarios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para abrir la ventana de agenda de citas del médico. Se pasa el
     * objeto del médico como referencia.
     */
    public void abrirVentanaAgendaDeCitas(MedicoDTO medico) {
        // Verificar si la ventana de agenda de citas ya está instanciada
        if (ventanaAgendaDeCitas == null) {
            ventanaAgendaDeCitas = new agendaDeCitas(medico);
        }

        // Crear una nueva instancia de la ventana de agenda de citas
        ventanaAgendaDeCitas = new agendaDeCitas(medico);

        // Establecer la referencia de la ventana actual en la agenda de citas
        ventanaAgendaDeCitas.setVentanaMedico(this);

        // Centrar la ventana en la pantalla
        ventanaAgendaDeCitas.setLocationRelativeTo(null);

        // Hacer visible la ventana de agenda de citas
        ventanaAgendaDeCitas.setVisible(true);

        // Cerrar la ventana actual
        this.dispose();
    }
}

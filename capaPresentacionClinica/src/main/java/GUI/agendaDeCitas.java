/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.CitaBO;
import BO.ConsultaBO;
import BO.MedicoBO;
import BO.PacienteBO;
import DTO.CitaViejaDTO;
import DTO.MedicoDTO;
import DTO.PacienteViejoDTO;
import Exception.NegocioException;
import Exception.PersistenciaClinicaException;
import Mappers.CitaMapper;
import configuracion.DependencyInjector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
public class agendaDeCitas extends javax.swing.JFrame {

    /**
     * Creates new form agendaDeCitas
     */
    private MedicoDTO medico;
    private MedicoBO medicoBO = DependencyInjector.crearMedicoBO();
    private CitaBO citaBO = DependencyInjector.crearCitaBO();
    private PacienteBO pacienteBO = DependencyInjector.crearPacienteBO();
    private ConsultaBO consultaBO = DependencyInjector.crearConsultaBO();
    private dashBoardMedico ventanaMedico;
    private final CitaMapper citaMapper = new CitaMapper();
    private historialConsultasMedico ventanaHistorialConsultasMedicoPaciente;

    private panelDeConsulta ventanaAgendarConsulta;
    


    public agendaDeCitas(MedicoDTO medico) {
        this.medico = medico;
        initComponents();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    cargarCitas();
                } catch (NegocioException | PersistenciaClinicaException ex) {
                    JOptionPane.showMessageDialog(agendaDeCitas.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public void setVentanaAgendarConsulta(panelDeConsulta ventanaAgendarConsulta) {
        this.ventanaAgendarConsulta = ventanaAgendarConsulta;
    }

    public void setVentanaMedico(dashBoardMedico VentanaMedico) {
        this.ventanaMedico = VentanaMedico;
    }

    public void setVentanaHistorialConsultasMedicoPaciente(historialConsultasMedico ventanaHistorialConsultasMedicoPaciente) {
        this.ventanaHistorialConsultasMedicoPaciente = ventanaHistorialConsultasMedicoPaciente;
    }
    
    public void setVentanaPaciente(dashBoardMedico ventanaMedico) {
        this.ventanaMedico = ventanaMedico;
    }

    public agendaDeCitas() {
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

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jLabel1 = new javax.swing.JLabel();
        botonVolver = new javax.swing.JButton();
        botonIniciarConsulta = new javax.swing.JButton();
        botonHistorialPaciente = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(632, 757));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("AGENDA DE CITAS");

        botonVolver.setText("Volver");
        botonVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonVolverMouseClicked(evt);
            }
        });
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        botonIniciarConsulta.setText("Iniciar Consulta");
        botonIniciarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarConsultaActionPerformed(evt);
            }
        });

        botonHistorialPaciente.setText("Ver historial del paciente");
        botonHistorialPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHistorialPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(botonVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonIniciarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(botonHistorialPaciente)))))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(87, 87, 87)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 388, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonHistorialPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonIniciarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(botonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        // TODO add your handling code here:
        volverDashboardMedico();
    }//GEN-LAST:event_botonVolverActionPerformed

    private void botonIniciarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarConsultaActionPerformed
        try {
            iniciarConsulta();
        } catch (NegocioException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaClinicaException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_botonIniciarConsultaActionPerformed

    private void botonHistorialPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHistorialPacienteActionPerformed
        try {
            revisarHistorialPaciente();

            // TODO add your handling code here:
        } catch (NegocioException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaClinicaException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(agendaDeCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonHistorialPacienteActionPerformed

    private void botonVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonVolverMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonVolverMouseClicked

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
            java.util.logging.Logger.getLogger(agendaDeCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(agendaDeCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(agendaDeCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(agendaDeCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new agendaDeCitas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonHistorialPaciente;
    private javax.swing.JButton botonIniciarConsulta;
    private javax.swing.JButton botonVolver;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    private void cargarCitas() throws NegocioException, PersistenciaClinicaException {
        try {
            List<CitaViejaDTO> citas = citaBO.consultarCitasMedico(medico);
            if (citas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El médico no tiene ninguna cita");
                return;
            }

            jComboBox1.removeAllItems();
            for (CitaViejaDTO cita : citas) {
                // Filtrar citas cuyo estado no sea "Cancelada"
                if ((cita.getEstado().getIdEstado() != 2) && (cita.getEstado().getIdEstado() != 6) && (cita.getEstado().getIdEstado() != 3) && (cita.getEstado().getIdEstado() != 4)) {
                    jComboBox1.addItem(cita.getIdCita() + " " + cita.getPaciente().getIdPaciente() + " " + cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidoPaterno() + " " + cita.getFechaHora());
                }
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarConsulta() throws NegocioException, PersistenciaClinicaException, SQLException {
        String seleccionado = (String) jComboBox1.getSelectedItem();
        if (seleccionado == null || seleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cita para iniciar la consulta.");
            return;
        }

        String[] partes = seleccionado.split(" ", 2); // Dividir solo en dos partes: ID y resto
        int idCita = Integer.parseInt(partes[0]); // Convertir la primera parte a entero
        CitaViejaDTO citaSeleccionada = citaBO.consultarCitaPorsuID(idCita);

        panelDeConsulta panelConsulta = new panelDeConsulta(citaSeleccionada, medico);
        panelConsulta.setVentanaMedico(this.ventanaMedico);
        panelConsulta.setVentanaAgendaDeCitas(this);

        panelConsulta.setVisible(true);

        this.dispose();

    }

    private void revisarHistorialPaciente() throws NegocioException, PersistenciaClinicaException, SQLException {
        String seleccionado = (String) jComboBox1.getSelectedItem();
        String[] partes = seleccionado.split(" ", 3);
        int pacienteID = Integer.parseInt(partes[1]);
        PacienteViejoDTO pacienteSELECCIONADO = pacienteBO.buscarPacientePorID(pacienteID);

        historialConsultasMedico historialPaciente = new historialConsultasMedico(pacienteSELECCIONADO);

        historialPaciente.setVentanaMedico(this.ventanaMedico);
        historialPaciente.setVentanaAgendaDeCitas(this);

        historialPaciente.setVisible(true);

        // 7. Cerrar o disponer el frame actual, si así lo deseas.
        this.dispose();

    }
    
    public void volverDashboardMedico() {
        if (ventanaMedico == null) {
            ventanaMedico = new dashBoardMedico();
        }
        
        ventanaMedico.setLocationRelativeTo(null);
        ventanaMedico.setVisible(true);
        this.dispose();
    }
    
    
}

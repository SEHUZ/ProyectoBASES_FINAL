/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.CitaBO;
import DTO.CitaViejaDTO;
import DTO.PacienteViejoDTO;
import Entidades.Cita;
import Exception.NegocioException;
import configuracion.DependencyInjector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
public class listaCitasProximas extends javax.swing.JFrame {

    private CitaBO citaBO = DependencyInjector.crearCitaBO();

    private PacienteViejoDTO paciente;
    private dashboardPaciente ventanaPaciente;

    /**
     * Creates new form listaCitasProximas
     */
    public listaCitasProximas(PacienteViejoDTO paciente) {
        this.paciente = paciente;
        initComponents();
        cargarComboBox(paciente);
    }

    public listaCitasProximas() {
        initComponents();
    }

    public void setVentanaPaciente(dashboardPaciente ventanaPaciente) {
        this.ventanaPaciente = ventanaPaciente;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        botonCancelarCita = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();
        botonVerDetalles = new javax.swing.JButton();
        listaCitasProximas = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(632, 757));
        setMinimumSize(new java.awt.Dimension(632, 757));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("LISTA CITAS PROXIMAS");
        jLabel1.setToolTipText("");

        botonCancelarCita.setText("Cancelar Cita");
        botonCancelarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarCitaActionPerformed(evt);
            }
        });

        botonVolver.setText("Volver");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        botonVerDetalles.setText("Ver Detalles");
        botonVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerDetallesActionPerformed(evt);
            }
        });

        listaCitasProximas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaCitasProximasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(botonVolver)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(botonVerDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(botonCancelarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listaCitasProximas, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(125, 125, 125))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134)
                .addComponent(listaCitasProximas, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonVerDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(botonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarCitaActionPerformed
        try {
            cancelarCita();

// TODO add your handling code here:
        } catch (NegocioException ex) {
            Logger.getLogger(listaCitasProximas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonCancelarCitaActionPerformed

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        volverDashboardPaciente();
    }//GEN-LAST:event_botonVolverActionPerformed

    private void botonVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerDetallesActionPerformed
        verDetalleCita();
    }//GEN-LAST:event_botonVerDetallesActionPerformed

    private void listaCitasProximasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaCitasProximasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaCitasProximasActionPerformed

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
            java.util.logging.Logger.getLogger(listaCitasProximas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(listaCitasProximas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(listaCitasProximas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(listaCitasProximas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new listaCitasProximas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelarCita;
    private javax.swing.JButton botonVerDetalles;
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JComboBox<String> listaCitasProximas;
    // End of variables declaration//GEN-END:variables
// Carga las citas próximas del paciente en un ComboBox

    public void cargarComboBox(PacienteViejoDTO paciente) {
        try {
            // Obtiene las citas próximas del paciente desde la lógica de negocio
            List<CitaViejaDTO> citas = citaBO.consultarCitasProximasPaciente(paciente);

            // Si no hay citas próximas, muestra un mensaje y termina el método
            if (citas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tiene citas próximas actualmente.");
                return;
            }

            // Elimina los ítems previamente añadidos al ComboBox
            listaCitasProximas.removeAllItems();

            // Itera sobre las citas obtenidas
            for (CitaViejaDTO cita : citas) {
                // Si la cita no está cancelada, la añade al ComboBox
                if (!cita.getEstado().getDescripcion().equals("Cancelada")) {
                    listaCitasProximas.addItem(cita.getIdCita() + " " + cita.getEstado().getDescripcion() + " " + cita.getFechaHora() + " Dr. " + cita.getMedico().getNombres() + " " + cita.getMedico().getApellidoPaterno());
                }
            }
        } catch (NegocioException ex) {
            // Si ocurre un error de negocio, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Muestra los detalles de la cita seleccionada
    public void verDetalleCita() {
        // Obtiene la cita seleccionada del ComboBox
        String seleccionada = (String) listaCitasProximas.getSelectedItem();

        // Verifica si no se ha seleccionado ninguna cita
        if (seleccionada == null || seleccionada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Divide la cadena seleccionada para obtener el ID de la cita
            String[] partes = seleccionada.split(" ", 2); // Divide solo en dos partes: ID y resto
            int idCita = Integer.parseInt(partes[0]); // Convierte la primera parte a entero

            // Consulta la cita completa usando el ID
            Cita citaSeleccionada = citaBO.consultarCitaPorID(idCita);

            // Construye el mensaje con los detalles de la cita
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("ID Cita: ").append(citaSeleccionada.getIdCita()).append("\n");
            mensaje.append("Fecha y Hora: ").append(citaSeleccionada.getFechaHora()).append("\n");
            mensaje.append("Estado: ").append(citaSeleccionada.getEstado().getDescripcion()).append("\n");
            mensaje.append("Paciente: ").append(citaSeleccionada.getPaciente().getNombres()).append(" ")
                    .append(citaSeleccionada.getPaciente().getApellidoPaterno()).append(" ")
                    .append(citaSeleccionada.getPaciente().getApellidoMaterno()).append("\n");
            mensaje.append("Médico: ").append(citaSeleccionada.getMedico().getNombres()).append(" ")
                    .append(citaSeleccionada.getMedico().getApellidoPaterno()).append(" ")
                    .append(citaSeleccionada.getMedico().getApellidoMaterno()).append("\n");

            // Verifica si la cita es de tipo normal o de emergencia
            if (citaSeleccionada.getNormal() != null) {
                mensaje.append("Tipo de Cita: Normal\n");
            } else if (citaSeleccionada.getEmergencia() != null) {
                mensaje.append("Tipo de Cita: Emergencia\n");
                mensaje.append("Folio Emergencia: ").append(citaSeleccionada.getEmergencia().getFolio()).append("\n");
                mensaje.append("Fecha de Expiración: ").append(citaSeleccionada.getEmergencia().getFechaExpiracion()).append("\n");
            }

            // Muestra el mensaje con los detalles de la cita
            JOptionPane.showMessageDialog(this, mensaje.toString(), "Detalles de la Cita", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            // Si hay un error al procesar el ID de la cita, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, "Error al procesar el ID de la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NegocioException ex) {
            // Si ocurre un error de negocio, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Cancela la cita seleccionada
    public void cancelarCita() throws NegocioException {
        // Obtiene la cita seleccionada del ComboBox
        String seleccionada = (String) listaCitasProximas.getSelectedItem();

        // Verifica si no se ha seleccionado ninguna cita
        if (seleccionada == null || seleccionada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Divide la cadena seleccionada para obtener el ID de la cita
            String[] partes = seleccionada.split(" ", 2);
            int idCita = Integer.parseInt(partes[0]);

            // Cancela la cita usando el ID
            citaBO.cancelarCita(idCita);

            // Muestra un mensaje de éxito
            JOptionPane.showMessageDialog(this, "Cita cancelada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (NegocioException ex) {
            // Si ocurre un error de negocio, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Vuelve al dashboard del paciente
    public void volverDashboardPaciente() {
        // Si la ventana de dashboard del paciente no ha sido creada aún, la crea
        if (ventanaPaciente == null) {
            ventanaPaciente = new dashboardPaciente();
        }

        // Establece esta ventana como la ventana de citas próximas
        ventanaPaciente.setVentanaCitasProximas(this);
        // Centra la ventana en la pantalla
        ventanaPaciente.setLocationRelativeTo(null);
        // Hace visible la ventana
        ventanaPaciente.setVisible(true);
        // Cierra la ventana actual
        this.dispose();
    }
}

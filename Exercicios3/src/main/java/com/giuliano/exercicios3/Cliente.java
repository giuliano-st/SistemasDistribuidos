/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.giuliano.exercicios3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author laboratorio
 */
public class Cliente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Cliente.class.getName());

    private DatagramSocket socket;
    private String ipServidor = "localhost";
    private int portaServidor = 1234;
    private String emailUsuario = "";
    
    private Timer timerRequisicao;
    private Timer timerContador;
    private int segundosRestantes = 60;

    /**
     * Creates new form Cliente
     */
    public Cliente() {
        initComponents();
        inicializarSocket();
        configurarAcoes();
    }

    private void inicializarSocket() {
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao criar socket UDP: " + e.getMessage());
        }
    }

    private void configurarAcoes() {
        btnRegistrar.addActionListener(e -> realizarCadastro());
    }

    private void realizarCadastro() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome e o e-mail!");
            return;
        }

        emailUsuario = email;

        txtNome.setEnabled(false);
        txtEmail.setEnabled(false);
        btnRegistrar.setEnabled(false);

        new Thread(() -> {
            try {
                String mensagem = "CADASTRAR;" + nome + ";" + email;
                DatagramPacket pacoteEnvio = ComunicadorUDP.montaMensagem(mensagem, ipServidor, portaServidor);
                ComunicadorUDP.enviaMensagem(socket, pacoteEnvio);

                DatagramPacket pacoteResposta = ComunicadorUDP.recebeMensagem(socket);
                if (pacoteResposta != null) {
                    String resposta = new String(pacoteResposta.getData(), 0, pacoteResposta.getLength()).trim();
                    
                    SwingUtilities.invokeLater(() -> {
                        if (resposta.startsWith("SUCESSO")) {
                            JOptionPane.showMessageDialog(this, resposta);
                            
                            if (resposta.contains("Token inicial: ")) {
                                String tokenInicial = resposta.split("Token inicial: ")[1].trim();
                                txtToken.setText(tokenInicial);
                            }

                            iniciarCicloToken();
                        } else {
                            JOptionPane.showMessageDialog(this, resposta);
                            txtNome.setEnabled(true);
                            txtEmail.setEnabled(true);
                            btnRegistrar.setEnabled(true);
                        }
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Erro na comunicação com o servidor.");
                    txtNome.setEnabled(true);
                    txtEmail.setEnabled(true);
                    btnRegistrar.setEnabled(true);
                });
            }
        }).start();
    }

    private void iniciarCicloToken() {
        segundosRestantes = 60;

        if (timerContador != null) timerContador.stop();
        timerContador = new Timer(1000, e -> {
            segundosRestantes--;
            if (segundosRestantes < 0) {
                segundosRestantes = 60;
            }
            setTitle("Cliente - Próxima renovação em: " + segundosRestantes + "s");
        });
        timerContador.start();

        if (timerRequisicao != null) timerRequisicao.stop();
        timerRequisicao = new Timer(60000, e -> solicitarNovoToken());
        timerRequisicao.start();
    }

    private void solicitarNovoToken() {
        new Thread(() -> {
            try {
                String mensagem = "SOLICITAR_TOKEN;" + emailUsuario;
                DatagramPacket pacoteEnvio = ComunicadorUDP.montaMensagem(mensagem, ipServidor, portaServidor);
                ComunicadorUDP.enviaMensagem(socket, pacoteEnvio);

                DatagramPacket pacoteResposta = ComunicadorUDP.recebeMensagem(socket);
                if (pacoteResposta != null) {
                    String resposta = new String(pacoteResposta.getData(), 0, pacoteResposta.getLength()).trim();
                    
                    if (resposta.startsWith("TOKEN:")) {
                        String novoToken = resposta.split(":")[1].trim();
                        SwingUtilities.invokeLater(() -> {
                            txtToken.setText(novoToken);
                            segundosRestantes = 60;
                        });
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        lblLogo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        lblToken = new javax.swing.JLabel();
        txtToken = new javax.swing.JTextField();
        btnSolicitar = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente");

        lblLogo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblLogo.setText("SisGado");

        lblNome.setText("Nome:");

        lblEmail.setText("E-mail:");

        btnRegistrar.setText("Registrar");

        lblToken.setText("Token");

        txtToken.setEditable(false);

        btnSolicitar.setText("Solicitar");
        btnSolicitar.addActionListener(this::btnSolicitarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblToken)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtToken))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnRegistrar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSolicitar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(lblLogo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLogo)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(btnRegistrar)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblToken)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSolicitar)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSolicitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarActionPerformed
        solicitarNovoToken();
    }//GEN-LAST:event_btnSolicitarActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Cliente().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSolicitar;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblToken;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtToken;
    // End of variables declaration//GEN-END:variables
}

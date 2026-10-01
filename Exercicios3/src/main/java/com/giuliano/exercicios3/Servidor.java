/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.giuliano.exercicios3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author laboratorio
 */
public class Servidor extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Servidor.class.getName());

    /**
     * Creates new form Servidor
     */
    
    private JTextArea txtLog;
    private DefaultListModel<String> modeloListaPessoas;
    private JList<String> listaPessoasUI;

    private DatagramSocket socket;
    private List<Pessoa> listaPessoas;
    
    public Servidor() {
        setTitle("Servidor UDP - Painel de Controle");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        listaPessoas = new ArrayList<>();
        
        inicializarComponentes();
        inicializarServidorUDP();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Área de Logs de Comunicação (Centro)
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Logs de Comunicação UDP"));

        // Lista de Pessoas Cadastradas (Direita)
        modeloListaPessoas = new DefaultListModel<>();
        listaPessoasUI = new JList<>(modeloListaPessoas);
        JScrollPane scrollLista = new JScrollPane(listaPessoasUI);
        scrollLista.setPreferredSize(new Dimension(220, 0));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

        add(scrollLog, BorderLayout.CENTER);
        add(scrollLista, BorderLayout.EAST);
    }
    
    private void inicializarServidorUDP() {
        new Thread(() -> {
            try {
                socket = new DatagramSocket(1234);
                adicionarLog("Servidor ativo à espera do cliente na porta 1234");

                while (true) {
                    DatagramPacket pacoteRecebido = ComunicadorUDP.recebeMensagem(socket);
                    
                    if (pacoteRecebido != null) {
                        String mensagem = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength()).trim();
                        adicionarLog("Recebi: " + mensagem + " | De: " + pacoteResumido(pacoteRecebido));

                        String[] partes = mensagem.split(";");
                        String acao = partes[0];
                        String resposta = "";

                        if (acao.equalsIgnoreCase("CADASTRAR")) {
                            String nome = partes.length > 1 ? partes[1] : "";
                            String email = partes.length > 2 ? partes[2] : "";

                            synchronized (listaPessoas) {
                                boolean existe = listaPessoas.stream()
                                        .anyMatch(p -> p.getEmail().equalsIgnoreCase(email));

                                if (existe) {
                                    resposta = "ERRO: E-mail já cadastrado!";
                                } else if (nome.isEmpty() || email.isEmpty()) {
                                    resposta = "ERRO: Dados incompletos para cadastro!";
                                } else {
                                    Pessoa novaPessoa = new Pessoa(nome, email, pacoteRecebido.getAddress(), pacoteRecebido.getPort());
                                    listaPessoas.add(novaPessoa);
                                    resposta = "SUCESSO: Cadastro realizado! Token inicial: " + novaPessoa.getToken();
                                    
                                    adicionarLog("-> Novo usuário registrado: " + nome + " (" + email + ")");
                                    SwingUtilities.invokeLater(() -> modeloListaPessoas.addElement(nome + " - " + email));
                                }
                            }

                        } else if (acao.equalsIgnoreCase("SOLICITAR_TOKEN")) {
                            String email = partes.length > 1 ? partes[1] : "";

                            synchronized (listaPessoas) {
                                Pessoa pessoaEncontrada = listaPessoas.stream()
                                        .filter(p -> p.getEmail().equalsIgnoreCase(email))
                                        .findFirst()
                                        .orElse(null);

                                if (pessoaEncontrada == null) {
                                    resposta = "ERRO: Usuário não encontrado!";
                                } else {
                                    if (pessoaEncontrada.tokenExpirado()) {
                                        pessoaEncontrada.gerarNovoToken();
                                        adicionarLog("-> Token expirado para " + email + ". Novo token gerado.");
                                    } else {
                                        adicionarLog("-> Token solicitado dentro do prazo para " + email + ". Mantendo o mesmo.");
                                    }
                                    resposta = "TOKEN:" + pessoaEncontrada.getToken();
                                }
                            }
                        } else {
                            resposta = "ERRO: Ação desconhecida.";
                        }

                        // Envia a resposta de volta usando o ComunicadorUDP
                        DatagramPacket pacoteResposta = ComunicadorUDP.montaMensagem(
                                resposta, 
                                pacoteRecebido.getAddress().getHostAddress(), 
                                pacoteRecebido.getPort()
                        );
                        ComunicadorUDP.enviaMensagem(socket, pacoteResposta);
                    }
                }
            } catch (Exception ex) {
                adicionarLog("Erro no servidor UDP: " + ex.getMessage());
            }
        }).start();
    }
    
    private void adicionarLog(String texto) {
        System.out.println(texto);
        SwingUtilities.invokeLater(() -> {
            txtLog.append(texto + "\n");
            txtLog.setCaretPosition(txtLog.getDocument().getLength()); // Auto-scroll
        });
    }

    private String pacoteResumido(DatagramPacket p) {
        return p.getAddress().getHostName() + ":" + p.getPort();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new Servidor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

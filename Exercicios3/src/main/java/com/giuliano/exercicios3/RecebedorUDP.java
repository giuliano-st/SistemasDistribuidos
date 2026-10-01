package com.giuliano.exercicios3;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RecebedorUDP {
    DatagramSocket socket;
    private List<Pessoa> listaPessoas;

    public RecebedorUDP() {
        listaPessoas = new ArrayList<>();
        criaServerSocket();
        System.out.println("Servidor ativo à espera do cliente/enviador");
        escutar();
    }

    private void criaServerSocket() {
        try {
            socket = new DatagramSocket(1234);
        } catch (Exception ex) {
        }
    }
    
    private void escutar() {
        while (true) {
            DatagramPacket pacoteRecebido = ComunicadorUDP.recebeMensagem(socket);
            
            if (pacoteRecebido != null) {
                String mensagem = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength()).trim();
                System.out.println("Recebi: " + mensagem);
                System.out.println("De: " + pacoteResumido(pacoteRecebido));

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
                            System.out.println("-> Novo usuário registrado: " + nome + " (" + email + ")");
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
                                pessoaEncurtarOuGerar(pessoaEncontrada);
                                System.out.println("-> Token expirado para " + email + ". Novo token gerado.");
                            } else {
                                System.out.println("-> Token solicitado dentro do prazo para " + email + ". Mantendo o mesmo.");
                            }
                            resposta = "TOKEN:" + pessoaEncontrada.getToken();
                        }
                    }
                } else {
                    resposta = "ERRO: Ação desconhecida.";
                }

                DatagramPacket pacoteResposta = ComunicadorUDP.montaMensagem(
                        resposta, 
                        pacoteRecebido.getAddress().getHostAddress(), 
                        pacoteRecebido.getPort()
                );
                ComunicadorUDP.enviaMensagem(socket, pacoteResposta);
            }
        }
    }
    
    private void pessoaEncurtarOuGerar(Pessoa pessoa) {
        pessoa.gerarNovoToken();
    }

    private String pacoteResumido(DatagramPacket p) {
        return p.getAddress().getHostName() + ":" + p.getPort();
    }

    public static void main(String[] args) {
        RecebedorUDP receptor = new RecebedorUDP();
    }
}

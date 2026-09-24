package ex3;

import java.net.Socket;

public class ThreadRecebedora extends Thread {

    Socket socket;
    Cliente cliente;

    public ThreadRecebedora(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Iniciada a Thread para recebimento de dados do cliente: " + socket.getInetAddress());
        try {
            while (true) {
                String mensagem = Comunicador.recebeMensagem(socket); // recebe a string enviada pela rede
                
                if (mensagem == null) {
                    System.out.println("Cliente " + socket.getInetAddress() + " desconectou.");
                    break; // Encerra apenas esta thread, sem derrubar o servidor todo
                }
                
                // Exibe as informações do cliente junto com a mensagem recebida
                System.out.println("[" + socket.getInetAddress() + "] disse: " + mensagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Garante que o socket será fechado ao sair
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
package ex3;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private ServerSocket servidor;
    // Cria um pool com capacidade para gerenciar até 10 threads simultâneas (exemplo)
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public Servidor() {
        criaServerSocket();
        aguardaClientes();
    }

    private void criaServerSocket() {
        try {
            servidor = new ServerSocket(1234);
            System.out.println("Server escutando na porta 1234");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void aguardaClientes() {
        try {
            // Loop para aceitar múltiplos clientes continuamente
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Recebi uma conexao de um cliente no endereco " + cliente.getInetAddress());
                
                // Instancia as tarefas
                Runnable tr = new ThreadRecebedora(cliente);
                Runnable te = new ThreadEnviadora(cliente);
                
                // Submete as tarefas para a pool de threads executar
                pool.execute(tr);
                pool.execute(te);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor s = new Servidor();
    }
}
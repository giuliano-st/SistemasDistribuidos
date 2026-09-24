package ex2;

import java.io.*;
import java.net.Socket;

public class Cliente {

    Socket socket;

    public Cliente() {
        criaClientSocket();
        Pessoa p = new Pessoa(23, "Joshua");
        ComunicadorObjetos.enviaObjeto(socket, p);
        Pessoa p2 = new Pessoa();
        p2 = (Pessoa)ComunicadorObjetos.recebeObjeto(socket);
        System.out.println("Recebi " + p2.getNome() + "Idade " + p2.getIdade());
    }

    private void criaClientSocket() {
        try {
            //cria um socket TCP para se conectar ao servidor de ip "localhost" porta 1234
            socket = new Socket("10.104.12.40", 1234);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente c = new Cliente();
    }
}

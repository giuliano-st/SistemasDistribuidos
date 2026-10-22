import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class GeraPessoa extends UnicastRemoteObject implements IGeraPessoa {
    public GeraPessoa() throws RemoteException {
    }
    
    @Override
    public Pessoa gerarPessoa(String nome, String cpf) {
        Pessoa p = new Pessoa(nome, cpf);
        return p;        
    }
}

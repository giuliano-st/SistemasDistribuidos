import java.rmi.*;
import java.rmi.RemoteException;

public interface IGeraPessoa extends Remote {
    public Pessoa gerarPessoa(String nome, String cpf) throws RemoteException;
}

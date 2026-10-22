package servicos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Sessao;

public interface TelefoniaService extends Remote {
    Sessao consultarSessao(String numeroCelular)
            throws RemoteException;
}

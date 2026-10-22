package servicos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.Sessao;
import model.TabelaHash;

public class TelefoniaServiceImpl extends UnicastRemoteObject implements TelefoniaService {
    private final TabelaHash tabelaHash;

    public TelefoniaServiceImpl(TabelaHash tabelaHash)
            throws RemoteException {
        super(1100);
        this.tabelaHash = tabelaHash;
    }

    @Override
    public Sessao consultarSessao(String numeroCelular)
            throws RemoteException {
        return tabelaHash.sessoes.get(numeroCelular);
    }
}

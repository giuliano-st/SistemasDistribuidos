/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author laboratorio
 */
public class GerarLink extends UnicastRemoteObject implements IGerarLink {

    String link;
    TabelaHash tbl;
    String enderecoServidor;

    public GerarLink(String enderecoServidor, TabelaHash tbl) throws RemoteException {
        this.tbl = tbl;
        this.enderecoServidor = enderecoServidor;
    }

    public String gerarLink(String link) throws RemoteException {
        this.link = link;
        String hash = String.valueOf(link.hashCode() & 0x7fffffff);

        if (tbl.links.get(hash) == null) {
            tbl.links.put(hash, link);
        } else {
            return "CADASTRADO";
        }

        System.out.println("Exibindo a tabela hash de links!!");
//        for (String chave : tbl.links.keySet()) {
//            String valor = tbl.links.get(chave);
//            System.out.println(chave + " -> " + valor);
//        }

        for (Map.Entry<String, String> entrada : tbl.links.entrySet()) {
            System.out.println(entrada.getKey() + " : " + entrada.getValue());
        }

//        tbl.links.forEach((chave, valor) -> {
//            System.out.println("Chave: " + chave + " -> Valor: " + valor);
//        });
        String url = "http://" + this.enderecoServidor + ":8080/" + hash;

        return url;
    }

    
}

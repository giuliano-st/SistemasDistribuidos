package servidor;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import servidor.TabelaHash;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Server {

    static String enderecoServidor = "10.104.12.33";
    static String HOST_URL = "rmi://" + enderecoServidor + "/GerarLink";
    static TabelaHash tbl = new TabelaHash();


    // Classe interna que lida com o Navegador
    static class RedirectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Pega o caminho digitado (ex: "/24234") e remove a barra inicial
            String path = exchange.getRequestURI().getPath();
            String codigo = path.substring(1); 

            // Busca a URL original no HashMap usando o código
            String urlOriginal = tbl.links.get(codigo);

            if (urlOriginal != null) {
                // Se achou, responde ao navegador com o código 302 (Redirecionamento)
                exchange.getResponseHeaders().set("Location", urlOriginal);
                exchange.sendResponseHeaders(302, -1); // -1 indica que não há corpo na resposta
                System.out.println("Redirecionando " + codigo + " para " + urlOriginal);
            } else {
                // Se o código não existir no HashMap, exibe erro 404
                String respostaErro = "<h1>404 - Link encurtado nao encontrado!</h1>";
                exchange.sendResponseHeaders(404, respostaErro.length());
                exchange.getResponseBody().write(respostaErro.getBytes());
            }
            exchange.close();
        }
    }
    
    public static void main(String args[]) {
        try {
            // 1. Inicia Servidor RMI
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            GerarLink objetoRemoto = new GerarLink(enderecoServidor, tbl);

            Naming.bind(HOST_URL, objetoRemoto);
            System.out.println("Servidor online e no aguardo....");
            

            // 2. Inicia o Servidor HTTP nativo do Java na porta 8080
            HttpServer serverHTTP = HttpServer.create(new InetSocketAddress(8080), 0);

            // Cria um contexto que intercepta qualquer requisição
            serverHTTP.createContext("/", new RedirectHandler());
            serverHTTP.setExecutor(null);
            serverHTTP.start();
            System.out.println("Servidor Web de redirecionamento pronto em " + enderecoServidor);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}

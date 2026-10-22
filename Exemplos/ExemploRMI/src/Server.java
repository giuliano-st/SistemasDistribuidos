import java.rmi.*;
import java.rmi.registry.*;

public class Server {
    String HOST_URL = "rmi://localhost/GeraPessoa";
    
    public Server() {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            GeraPessoa objetoRemoto = new GeraPessoa();
            Naming.bind(HOST_URL, objetoRemoto);
            System.out.println("Servidor online e a espera de clientes...");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
}
    public static void main(String[] args) {
        new Server();
    }
}

import java.rmi.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String args[]) {
        try {
            IGeraPessoa objetoRemoto = (IGeraPessoa) Naming.lookup("rmi://10.104.12.40/GeraPessoa");
            String nome = JOptionPane.showInputDialog(null,"Entre com um nome completo");
            String cpf = JOptionPane.showInputDialog(null,"Entre com seu cpf sem pontuação");
            
            Pessoa p = objetoRemoto.gerarPessoa(nome, cpf);
            
            JOptionPane.showMessageDialog(null, p);
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}

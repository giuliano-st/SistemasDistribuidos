import java.io.Serializable;
import java.util.Objects;



public class Pessoa implements Serializable{
    String cpf;
    String nome;
    String email;

    public Pessoa(String nome, String cpf) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = gerarEmail();
    }
    
    private String gerarEmail() {
        String nomes[];
        nomes = this.nome.split(" ");
        return (nomes[0] + "." + nomes[nomes.length-1] + "@ufn.edu.br").toLowerCase();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        return Objects.equals(this.cpf, other.cpf);
    }

    @Override
    public String toString() {
        return "Pessoa{" + "cpf=" + cpf + ", nome=" + nome + ", email=" + email + '}';
    }
    
    
}

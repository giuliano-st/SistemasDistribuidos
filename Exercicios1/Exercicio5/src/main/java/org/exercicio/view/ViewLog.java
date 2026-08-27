package org.exercicio.view;

public class ViewLog {
    public void exibir(int[] totalErros) {
        System.out.println("Total de erros:");
        System.out.println("Erros de conexão com o banco (Erro 0): "+ totalErros[0]);
        System.out.println("Erros de montagem de volume (Erro 1): "+ totalErros[1]);
        System.out.println("Erros de execução de migrações (Erro 2): "+ totalErros[2]);
        System.out.println("Erros no buffer de memória (Erro 3): "+ totalErros[3]);
    }
}

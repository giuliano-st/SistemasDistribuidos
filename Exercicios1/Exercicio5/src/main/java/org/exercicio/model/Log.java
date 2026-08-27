package org.exercicio.model;

import java.util.List;

public class Log implements Runnable {
    private List<String> linhas;
    private int[] erros;

    public Log(List<String> linhas) {
        this.linhas = linhas;
        this.erros = new int[4];
    }

    @Override
    public void run() {
        for (String linha : linhas) {
            String[] colunas = linha.split(",");

            if (colunas.length >= 3) {
                int tipoDeErro = Integer.parseInt(colunas[2]);

                if (tipoDeErro >= 0 && tipoDeErro <= 3) {
                    this.erros[tipoDeErro]++;
                }
            }
        }
    }
    public int[] getErros() {
        return this.erros;
    }
}
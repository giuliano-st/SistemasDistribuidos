package utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import model.Sessao;

public class Util {

    public static String carregarArquivoEmHashMap(
            HashMap<String, Sessao> sessoes,
            StringBuilder dadosSessoes) {

        Path caminho = Paths.get("src", "utilidades", "telephony_sessions.txt");

        try {
            List<String> linhas = Files.readAllLines(caminho);

            for (String linha : linhas) {
                dadosSessoes.append(linha)
                            .append(System.lineSeparator());

                if (!linha.trim().isEmpty()) {
                    String[] dadosLinha = linha.split(";");

                    if (dadosLinha.length >= 2) {
                        Sessao sessao = new Sessao(
                                dadosLinha[0],
                                dadosLinha[1]
                        );

                        sessoes.put(sessao.getNumeroCelular(), sessao);
                    }
                }
            }

            return "";

        } catch (IOException e) {
            return "Erro ao processar o arquivo: " + e.getMessage();
        }
    }
}
package org.exercicio.controller;

import org.exercicio.model.Log;
import org.exercicio.view.ViewLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class LogController {

    private ViewLog view;

    public LogController(ViewLog view) {
        this.view = view;
    }

    public void iniciar(String arquivo) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Log> logs = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        List<String> pedaco = new ArrayList<>();

        while ((linha = reader.readLine()) != null) {
            pedaco.add(linha);

            if (pedaco.size() == 50) {
                Log log = new Log(pedaco);
                logs.add(log);
                executor.execute(log);

                pedaco = new ArrayList<>();
            }
        }

        if (!pedaco.isEmpty()) {
            Log log = new Log(pedaco);
            logs.add(log);
            executor.execute(log);
        }
        reader.close();

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        int[] totalErros = new int[4];

        for (Log log : logs) {
            int[] resumoErros = log.getErros();

            totalErros[0] += resumoErros[0];
            totalErros[1] += resumoErros[1];
            totalErros[2] += resumoErros[2];
            totalErros[3] += resumoErros[3];
        }
        view.exibir(totalErros);
    }
}

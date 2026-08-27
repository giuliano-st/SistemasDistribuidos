package org.exercicio;

import org.exercicio.controller.LogController;
import org.exercicio.view.ViewLog;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws Exception {
        ViewLog view = new ViewLog();
        LogController logController = new LogController(view);

        logController.iniciar("C:\\Users\\laboratorio\\Documents\\GitHub\\SistemasDistribuidos\\Exercicios1\\Exercicio5\\src\\main\\java\\org\\exercicio\\erro.log");
    }
}

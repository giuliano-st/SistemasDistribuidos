package Atividade1.Modulo2.controller;

import Atividade1.Modulo2.service.FranquiaService;
import Atividade1.Modulo2.view.FranquiaView;

public class FranquiaController {
    private FranquiaView view;
    private FranquiaService service;

    public FranquiaController(FranquiaView view, FranquiaService service) {
        this.view = view;
        this.service = service;
    }

    public void calcularFaturamentos() {

        try {
            double faturamentoTotal = service.calcularFaturamentoTotal(4, 10000);
            view.exibirFaturamentoFinal(faturamentoTotal);

        } catch (InterruptedException e) {
            view.exibirMensagem("Erro!");
        }
    }
}

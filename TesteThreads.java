class MinhaTarefa implements Runnable { //permite o uso de memória compartilhada
    int quantidade;
    public MinhaTarefa(int quantidade) {
        this.quantidade = quantidade;
    }
    
    @Override
    public void run() {
        //aqui está o código a ser concomitado
        Thread t = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            System.out.println("Executando na Thread: " + t.getName() + " | ID: " + t.getId());
        }
        
    }
}

public class TesteThreads {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MinhaTarefa(10), "Tarefa-1");
        Thread t2 = new Thread(new MinhaTarefa(20), "Tarefa-2");

        t1.start();
        t2.start();
    }
}
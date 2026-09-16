class T1 extends Thread {
    public void run() {
        for(int i = 0; i < 900; i++) {
            System.out.println("Thread 1 executando!");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

class T2 extends Thread {
    public void run() {
        for(int i = 0; i < 900; i++) {
            System.out.println("Thread 2 em ação!");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

public class Exemplo1 {

    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.start();

        T2 t2 = new T2();
        t2.start();
    }
}
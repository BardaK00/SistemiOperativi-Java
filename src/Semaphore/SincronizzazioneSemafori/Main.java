package Semaphore.SincronizzazioneSemafori;

public class Main {
    public static void main(String[] args) {
        //istanzio runnable
        ThreadP1 r1 = new ThreadP1("A");
        ThreadP2 r2 = new ThreadP2("B");

        Thread p1 = new Thread(r1);
        Thread p2 = new Thread(r2);

        p1.start();
        p2.start();

    }
}

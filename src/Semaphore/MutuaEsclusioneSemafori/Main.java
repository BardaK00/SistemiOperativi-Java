package Semaphore.MutuaEsclusioneSemafori;

public class Main {
    // creo il semafoto binario con 1 permesso

    public static void main(String [] args) {
        //istanzio i 2 runnable p1 e p2
        AbstractThreadME r1 = new ThreadME("A");
        AbstractThreadME r2 = new ThreadME("B");

        //creo i 2 thread effettivi
        Thread p1 = new Thread(r1);
        Thread p2 = new Thread(r2);

        //eseguo i 2 thread in mutua eclusione
        p1.start();
        p2.start();
    }
}

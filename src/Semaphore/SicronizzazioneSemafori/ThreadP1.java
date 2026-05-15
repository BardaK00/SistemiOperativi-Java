package Semaphore.SicronizzazioneSemafori;

public class ThreadP1 extends AbstractThreadSync implements Runnable {

    public ThreadP1(String n) {
        super(n);
    }

    @Override
    public void run() {
        var sem = super.getSem();
        System.out.println(this.getName());
        sem.release();
    }
}

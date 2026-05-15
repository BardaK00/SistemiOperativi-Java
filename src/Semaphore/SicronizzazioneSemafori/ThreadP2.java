package Semaphore.SicronizzazioneSemafori;

public class ThreadP2 extends AbstractThreadSync implements Runnable {

    public ThreadP2(String n) {
        super(n);
    }

    @Override
    public void run() {
        var sem = super.getSem();
        try{
            sem.acquire();
        }catch(Exception e){e.printStackTrace();
        }finally{System.out.println(this.getName());}

    }
}

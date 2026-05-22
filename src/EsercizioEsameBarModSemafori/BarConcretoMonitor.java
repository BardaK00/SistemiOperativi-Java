package EsercizioEsameBarModSemafori;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarConcretoMonitor implements Bar {
    private ReentrantLock l = new ReentrantLock();
    private Condition cassaVuota = l.newCondition();
    private Condition banconeMaxCapienza = l.newCondition();
    private int banconeLen = 4;
    private boolean cassaDisp = true;

    @Override
    public int scegli() {
        l.lock();
        try {
            // priorità alla cassa se libera
            if (cassaDisp)
                return 0;
            // se il bancone ha posto disponibile
            if (banconeLen > 0)
                return 1;
            if (l.getWaitQueueLength(cassaVuota) <=  l.getWaitQueueLength(banconeMaxCapienza))
                return 0;
            else
                return 1;
        }finally {
            l.unlock();
        }
    }

    @Override
    public void inizia(int i) {
        l.lock();
        try {
            if (i == 1) {
                while (banconeLen < 1) banconeMaxCapienza.await();
                System.out.println("il cliente ha inziato a bere");
                banconeLen--;
            } else {
                while (!(cassaDisp)) cassaVuota.await();
                System.out.println("il cliente ha inziato a pagare");
                cassaDisp = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
            //l'esecuzione dell'azione effettiva quindi il tempo per aspettare di bere e pagare viene fatto dopo aver
            //unlockato il lock
            try{TimeUnit.SECONDS.sleep(5);}catch(Exception e){e.printStackTrace();}
        }
    }

    @Override
    public void finisci(int i) {
        l.lock();
        if (i == 1) {
            System.out.println("il cliente ha smesso di bere");
            banconeLen++;
            banconeMaxCapienza.signal();
        } else {
            System.out.println("il cliente ha smesso di pagare");
            cassaDisp = true;
            cassaVuota.signal();
        }
        l.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Bar b = new BarConcretoMonitor();
        for (int i = 0; i < 100; i++) {
            new ThreadCliente(b).start();
        }
    }
}

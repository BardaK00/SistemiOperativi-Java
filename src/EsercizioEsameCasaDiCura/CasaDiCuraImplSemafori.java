package EsercizioEsameCasaDiCura;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CasaDiCuraImplSemafori implements CasaDiCura{
    Semaphore salaPreparazione = new Semaphore(3,true);
    Semaphore salaOperazione = new Semaphore(1);

    Semaphore iniziaOperazione = new Semaphore(0);
    Semaphore operazioneTerminata = new Semaphore(0);
    Semaphore pazientePuoUscire = new Semaphore(0);


    @Override
    public void chiamaEIniziaOperazione() throws InterruptedException {
        iniziaOperazione.acquire();
        System.out.println("il paziente" + Thread.currentThread().getName() + " sta per essere operato");
        operazioneTerminata.release();
    }

    @Override
    public void fineOperazione() throws InterruptedException {
        operazioneTerminata.acquire();
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10,20+1));
        System.out.println("il paziente:"+ Thread.currentThread().getName()+" è stato operato");
        System.out.println("avanti il prossimo");
        pazientePuoUscire.release();
        salaOperazione.release();
    }

    @Override
    public void pazienteEntra() throws InterruptedException {
        salaPreparazione.acquire();
        System.out.println("il paziente:" + Thread.currentThread().getName() + "è entrato in sala preparazione");
        salaOperazione.acquire();
        System.out.println("il paziente" + Thread.currentThread().getName() + "è entrato in sala operatoria, posto libero in sala preparazione");
        salaPreparazione.release();
        iniziaOperazione.release();

    }

    @Override
    public void pazienteEsci() throws InterruptedException {
        pazientePuoUscire.acquire();
        System.out.println("il paziente"+Thread.currentThread().getName() + "è uscito dalla sala operatoria");

    }

    static void main() {
        CasaDiCura cc = new CasaDiCuraImplSemafori();
        cc.test(cc);
    }
}

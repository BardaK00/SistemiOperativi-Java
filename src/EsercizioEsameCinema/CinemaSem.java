package EsercizioEsameCinema;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class CinemaSem extends Cinema{
    private int bigliettiConsegnati = 0;
    private int uscitoSala = 0;
    private Semaphore mutexCassa = new Semaphore(1,true); //mutex della cassa con fair = true quindi FIFO
    private Semaphore bigliettoAcquistato = new Semaphore(0);
    private Semaphore puoIniziareFilm = new Semaphore(0);
    private Semaphore filmTerminato = new Semaphore(0);
    private Semaphore mutexUscita = new Semaphore(1);

    public CinemaSem(int n) {
        super(n); //numeroClienti
    }

    @Override
    void acquistaBiglietto() throws InterruptedException {
        mutexCassa.acquire();
        System.out.println("il cliente:"+ Thread.currentThread().getName() + " ha acquistato un biglietto");
        bigliettoAcquistato.release();

    }

    @Override
    boolean consegnaBiglietto() throws InterruptedException {
        bigliettoAcquistato.acquire();
        System.out.println("L'addetto ha consegnato il biglietto al cliente col posto " + new Random().nextInt(1,61));
        bigliettiConsegnati++;
        if(bigliettiConsegnati == numeroClienti){//tutti i clienti hanno ricevuto un biglietto
            System.out.println("tutti i clienti possiedono il biglietto");
            puoIniziareFilm.release(numeroClienti);
            return true;
        }
        mutexCassa.release();
        return false;
    }

    @Override
    void vediFilm() throws InterruptedException {
        puoIniziareFilm.acquire();
        System.out.println("cliente "+Thread.currentThread().getName() + " sta guardando il film");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("il cliente: " +Thread.currentThread().getName()+"ha terminato la visione e sta uscendo dalla sala");
        mutexUscita.acquire();
        uscitoSala++;
        if(uscitoSala == numeroClienti){filmTerminato.release();}
        mutexUscita.release();
    }

    @Override
    void chiudiCinema() throws InterruptedException {
        filmTerminato.acquire();
        System.out.println("il film è terminato ,l'addetto sta chiudendo la sala");
        exit(0);

    }

    static void main() {
        int nClienti = 20;
        Cinema c = new CinemaSem(nClienti);
        c.test(c,nClienti);

    }
}

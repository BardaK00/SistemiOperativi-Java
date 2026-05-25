package EsercizioEsamePizzeria;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzeriaImplSemafori implements Pizzeria{
    //semaforo utilizzato per far "sedere i clienti"
    private final Semaphore postiDisponibili = new Semaphore(5);
    //necessario atomicInteger per modifiche concorrenziali non avevo voglia di fare un altro semaforo
    private AtomicInteger  conteggioPostiOccupati =new AtomicInteger();
    //indica che tutti i posti sono stati occupati quindi rispeglia pizzaiolo
    private final Semaphore postiOccupati = new Semaphore(0);
    //indica che il tempo di attesa della pizza è finita
    private final Semaphore pizzaPronta = new Semaphore(0);
    //necessario per far sedere uno alla volta i thread al tavolo senza causare problemi di concorrenza
    private final Semaphore mutex = new Semaphore(1);
    //indica ai thread in attesa per mangiare che la pizza è stata servita e posson mangiare
    private final Semaphore pizzaServita = new Semaphore(0);

    @Override
    public void mangiaPizza() throws InterruptedException {
        postiDisponibili.acquire();
        mutex.acquire();
        System.out.println("il cliente:"+ Thread.currentThread().getName()+ " si è seduto al tavolo");
        conteggioPostiOccupati.addAndGet(1);
        if(conteggioPostiOccupati.get() == 5){
            postiOccupati.release();
        }
        mutex.release();

    }

    @Override
    public void pizzaMangiata() throws InterruptedException {
        pizzaServita.acquire();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("il cliente:"+ Thread.currentThread().getName() + " ha finito di mangiare la pizza");
        conteggioPostiOccupati.addAndGet(-1);

        if(conteggioPostiOccupati.get()== 0){postiDisponibili.release(5);System.out.println("tutti i clienti si sono alzati dal tavolo");}



    }

    @Override
    public void preparaPizza() throws InterruptedException {
        postiOccupati.acquire();
        System.out.println("inizio preparazione pizza");
        TimeUnit.SECONDS.sleep(5);
        pizzaPronta.release();
        System.out.println("fine preparazione pizza");


    }

    @Override
    public void pizzaPronta() throws InterruptedException {
        pizzaPronta.acquire();
        pizzaServita.release(5);
        System.out.println("la pizza è servita al tavolo");


    }

    static void main() {
        Pizzeria p = new PizzeriaImplSemafori();
        p.test(p);
    }
}

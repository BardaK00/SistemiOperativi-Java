package EsercizioEsameMuseo;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MuseoSem extends MuseoC{
    //lista che contiene i visitatore in fila per la sala dell'ermellino
    private LinkedList<Visitatore> listaFifo = new LinkedList<>();
    static int contSa = 0;
    static int contDa = 0;
    private Semaphore salaErmellino = new Semaphore(5,true);
    //40 persone possono entrare contemporaneamente nella sala archeologica, true per gestire la coda con fairness quindi fifo
    private Semaphore salaArcheologica = new Semaphore(40,true);
    private Semaphore puoCambiareSala = new Semaphore(0);

    private Semaphore mutex = new Semaphore(1);

    private Semaphore mutexdb = new Semaphore(1);//semaforo per debug
    //serve per accedere a 2 variabili intere che contano quanti utenti entrano in una nell'altra sala

    public MuseoSem(int n) {
        super(n);
    }

    @Override
    void visitaSA() throws InterruptedException {
        salaArcheologica.acquire();
        System.out.println("il visitatore:" + Thread.currentThread().getName() + " è entrato nella SA");
        TimeUnit.SECONDS.sleep(new Random().nextInt(10,21));
        mutexdb.acquire();
        contSa ++;//debug
        mutexdb.release();


    }

    @Override
    void terminaVisitaSA() throws InterruptedException {
        System.out.println("il visitatore" + Thread.currentThread().getName() + " ha terminato la propria visita");
        mutex.acquire();
        listaFifo.add((Visitatore)Thread.currentThread()); //aggiunge il thread facendo il casting a Visitatore per gestire la coda Fifo
        mutex.release();
        salaArcheologica.release();
        puoCambiareSala.release();


    }

    @Override
    void visitaSD() throws InterruptedException {
        puoCambiareSala.acquire();
        Visitatore t = (Visitatore) Thread.currentThread();
        while (true) {
            mutex.acquire();
            boolean check = !listaFifo.isEmpty() && t.equals(listaFifo.getFirst());
            mutex.release();
            if (check) {
                salaErmellino.acquire();
                mutex.acquire();
                listaFifo.removeFirst();
                mutex.release();
                System.out.println("il cliente" + t.getName() + " è entrato nella sala dell'ermellino");
                TimeUnit.SECONDS.sleep(new Random().nextInt(2, 7));
                mutexdb.acquire();
                contDa++;//debug
                mutexdb.release();
                break;
            } else {
                puoCambiareSala.release();
            }
        }
    }

    @Override
    void terminaVisitaSD() {
        System.out.println("il cliente" + Thread.currentThread().getName() + " è uscito dalla sala dell'ermellino");
        salaErmellino.release();
    }

    static void main() throws InterruptedException {
        int vis = 10;
        MuseoC m = new MuseoSem(vis);
        m.test(m,vis);
        System.out.println(contDa);
        System.out.println(contSa);
    }
}

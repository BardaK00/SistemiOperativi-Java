package EsercizioEsameStabilimentoBalneare;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class StabilimentoImplSemafori extends Stabilimento {
    private int numeroBagnanti;
    private int[] scelte;

    public StabilimentoImplSemafori(int n){
        numeroBagnanti = n;
        scelte = new int[numeroBagnanti];}

    //SYNCH SEMAPHORE
    private Semaphore sceltaEffettuata = new Semaphore(0);
    private Semaphore preparazioneCompletata = new Semaphore(0);
    private Semaphore pagamentiEffettutati = new Semaphore(0);
    //MUTEX SEMAPHORE
    private Semaphore mutex = new Semaphore(1);
    private Semaphore mutexCassa = new Semaphore(1,true);


    private int contPagamento = 0;
    private int cont = 0;


    @Override
    void scegliAccesso() throws InterruptedException {
        mutex.acquire();
        int scelta =  new Random().nextInt(0,2);
        int index = cont;
        scelte[index] = scelta;
        System.out.println("il cliente" + Thread.currentThread().getName() + " ha scelto:" + scelte[index]);
        cont++;
        if(cont == numeroBagnanti){sceltaEffettuata.release();}//l'ultimo avvisa il gestore gesto che può procedere con la preparazione delle postazioni
        mutex.release();

    }

    @Override
    void preparaPostazioni() throws InterruptedException {
        sceltaEffettuata.acquire();
        System.out.println("il Gestore sta preparando le postazioni");
        for(int i = 0;i<scelte.length;i++){
            if(scelte[i]==0){
                System.out.println("il cliente:" + i + " ha scelto LETTINO , la preparazione dura 2 secondi");
                TimeUnit.SECONDS.sleep(2);
            }else{
                System.out.println("il cliente:" + i + " ha scelto OMBRELLONE , la preparazione dura 2 secondi");
                TimeUnit.SECONDS.sleep(3);
            }
        }
        System.out.println("il gestore ha completato la sistemazione delle postazioni");
        preparazioneCompletata.release();
    }

    @Override
    void paga() throws InterruptedException {
        preparazioneCompletata.acquire();
        mutexCassa.acquire();
        System.out.println("il cliente" + Thread.currentThread().getName() + " ha iniziato a pagare");
        if(scelte[contPagamento] == 0){
            System.out.println("il cliente" + Thread.currentThread().getName() + " ha pagato 10 euro");
        }else{
            System.out.println("il cliente" + Thread.currentThread().getName() + " ha pagato 15 euro");
        }
        contPagamento++;
        if(contPagamento == numeroBagnanti)pagamentiEffettutati.release();
        mutexCassa.release();
        preparazioneCompletata.release();

    }

    @Override
    void chiusura() throws InterruptedException {
        pagamentiEffettutati.acquire();
        System.out.println("tutti i bagnanti hanno pagato e hanno lascialo lo stabilimento");
        System.out.println("il Gestore ha chiuso lo stabilimento");
    }

    static void main() {

        int clienti = 10;
        Stabilimento s = new StabilimentoImplSemafori(clienti);
        Gestore g = new Gestore(s);
        g.start();
        for (int i = 0;i<clienti;i++){
            new Cliente(s).start();
        }

    }
}

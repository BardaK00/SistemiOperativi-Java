package EsercizioEsameAeroporto;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AeroportoImplSemafori extends Aeroporto{
    private Semaphore mutexBancone = new Semaphore(1,true);
    int bagagliDaDeporre = 0;

    private Semaphore bagagliDeposti = new Semaphore(0);

    private Semaphore registrazioneCompletata = new Semaphore(0);

    private Semaphore clienteTerminato = new Semaphore(0);

    @Override
    void deponeBagagli(int n) throws InterruptedException {
        mutexBancone.acquire();
        System.out.println("il cliente:" + Thread.currentThread().getName() + " ha deposto i suoi:" + n + " bagagli");
        TimeUnit.SECONDS.sleep(2L * n);
        bagagliDaDeporre = n;
        bagagliDeposti.release();
    }

    @Override
    void pesaERegistra() throws InterruptedException {
        bagagliDeposti.acquire();
        System.out.println("l'addetto " + Thread.currentThread().getName() + " ha iniziato a pesare i bagagli");
        for(int i = 0;i<bagagliDaDeporre;i++){
            TimeUnit.SECONDS.sleep(3);
            System.out.println("il bagaglio numero:" + (i+1) +  " è stato pesato e registrato");

        }
        bagagliDaDeporre = 0;
        System.out.println("tutti i bagagli sono stati registrati con successo, il cliente può riceve la sua carta d'imbarco");
        registrazioneCompletata.release();
    }

    @Override
    void riceviCartaImbarco() throws InterruptedException {
        registrazioneCompletata.acquire();
        System.out.println("check-in passeggero:"+ Thread.currentThread().getName() + " completato");
        clienteTerminato.release();
    }

    @Override
    void prossimoPasseggero() throws InterruptedException {
        clienteTerminato.acquire();
        System.out.println("l'addetto ha chiamato il prossimo cliente in fila");
        mutexBancone.release();
    }

    static void main() {
        Aeroporto a = new AeroportoImplSemafori();
        a.test(a);
    }
}

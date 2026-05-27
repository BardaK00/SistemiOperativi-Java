package EsercizioEsameCasello;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaselloImplMonitor extends Casello{
    private int incassoTotale = 0;
    private Lock[] l = new Lock[porte];

    public CaselloImplMonitor(int n, int t) {
        super(n, t);
    }

    @Override
    void entraPortaFifo(int porta) throws InterruptedException {
        l[porta-1].lock();
        System.out.println("il veicolo:" + Thread.currentThread().getName() + "è entrato nel casello autostradale");
    }

    @Override
    void pagaCasello(int kilometri) throws InterruptedException {
        System.out.println("il veicolo ha pagato:"+(kilometri*tariffa));
        TimeUnit.SECONDS.sleep(4);
        incassoTotale += kilometri * tariffa;// con monitor l'incassototale viene aggioranto in modo thread safe e atomico

    }

    @Override
    void lasciaPorta(int porta) {
        System.out.println("il veicolo "+ Thread.currentThread().getName() + "ha terminato il suo pagamento e ha lasciato la propria porta");
        l[porta-1].unlock();
    }

    @Override
    void popolaArray() {
        for(int i = 0;i<porte;i++){
            l[i] = new ReentrantLock(true);
        }
    }

    @Override
    int incassoTotale() {
        return incassoTotale;
    }
    static void main() throws InterruptedException {
        int x = 20;
        int porte = 4;
        int tariffa = 10;
        Casello c = new CaselloImplMonitor(porte,tariffa);
        c.popolaArray();
        ArrayList<Veicolo> ar = new ArrayList<>();
        for (int i = 0;i<x;i++){
            ar.add(new Veicolo(c));
        }

        for(Veicolo ca : ar)ca.start();
        for(Veicolo ca:ar)ca.join();

        System.out.println("la giornata lavorativa è finita con un incasso di:"+c.incassoTotale() +" dobloni");
    }
}

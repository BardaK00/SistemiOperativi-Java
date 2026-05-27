package EsercizioEsameCasello;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CaselloImplSemafori extends Casello{
    private int incassoTotale = 0;
    private Semaphore mutexIncasso = new Semaphore(1,true);
    private Semaphore[] porteCasello = new Semaphore[porte];

    public CaselloImplSemafori(int n, int t) {
        super(n, t);
        //n porte
        //t tariffe
    }



    @Override
    public void entraPortaFifo(int porta) throws InterruptedException {
        porteCasello[porta-1].acquire();
        System.out.println("il veicolo:" + Thread.currentThread().getName() + "è entrato nel casello autostradale");

    }

    @Override
    public void pagaCasello(int km) throws InterruptedException {
        System.out.println("il veicolo ha pagato:"+(km*tariffa));
        TimeUnit.SECONDS.sleep(4);
        mutexIncasso.acquire();
        incassoTotale += km * tariffa;
        mutexIncasso.release();
    }

    @Override
    public void lasciaPorta(int porta) {
        System.out.println("il veicolo "+ Thread.currentThread().getName() + "ha terminato il suo pagamento e ha lasciato la propria porta");
        porteCasello[porta-1].release();
    }

    public void popolaArray(){
        for(int i = 0;i<porte;i++){
            porteCasello[i] = new Semaphore(1,true);
        }
    }

    public int incassoTotale(){
        return incassoTotale;
    }

    static void main() throws InterruptedException {
        int x = 20;
        int porte = 4;
        int tariffa = 10;
        Casello c = new CaselloImplSemafori(porte,tariffa);
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

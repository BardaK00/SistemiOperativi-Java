package EsercizioEsameAziendaAgricola;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AziendaAgricolaImplSemafori implements AziendaAgricola{
    private int numeroSacchettiMagazzino = 200;
    private int costoSacchetto = 3;
    private int incassiTotali = 0;

    //puoRitirare e mutex gestiscono le cose su magazzino e cassa
    private Semaphore puoRitirare = new Semaphore(0,true);
    private Semaphore mutex = new Semaphore(1,true);

    private Semaphore nessunRitiroInCorso = new Semaphore(1);
    private Semaphore pagamentoTerminato = new Semaphore(0);
    private Semaphore ritiroTerminato = new Semaphore(0);
    private Semaphore necessarioRefill = new Semaphore(0);
    private Semaphore verificaRefill = new Semaphore(0);
    private Semaphore puoPagare = new Semaphore(0);

    @Override
    public void iniziaPagamento(int numeroSacchetti) throws InterruptedException {
        mutex.acquire();
        if(numeroSacchettiMagazzino < numeroSacchetti) {
            verificaRefill.release();
            puoPagare.acquire();
        }

        System.out.println("il cliente "+Thread.currentThread().getName() + " ha iniziato il pagamento dei suoi " +numeroSacchetti + " sacchi" );
        numeroSacchettiMagazzino -= numeroSacchetti;
        incassiTotali += numeroSacchetti*costoSacchetto;

        mutex.release();
        pagamentoTerminato.release();


    }

    @Override
    public void terminaPagamento() throws InterruptedException {

        pagamentoTerminato.acquire();
        System.out.println("il pagamento è avvenuto con successo");
        puoRitirare.release();


    }

    @Override
    public void iniziaRitiro(int numeroSacchetti) throws InterruptedException {
        puoRitirare.acquire();
        nessunRitiroInCorso.acquire();
        System.out.println(Thread.currentThread().getName() + "ha iniziato il ritiro dei sacchi");

        for(int i = 0;i< numeroSacchetti;i++){
            TimeUnit.SECONDS.sleep(3); //3 secondi di attesa per ogni sacchetto
            System.out.println("sacco " + i + " appena ritirato, ne mancano "+ (numeroSacchetti-i));
        }
        ritiroTerminato.release();
    }

    @Override
    public void terminaRitiro() throws InterruptedException {
        ritiroTerminato.acquire();
        System.out.println("il cliente" + Thread.currentThread().getName() + " ha terminato il ritiro dei suoi sacchetti");
        nessunRitiroInCorso.release();
    }

    public void verificaNecessarioRefill() throws InterruptedException {
        verificaRefill.acquire();
        necessarioRefill.release();
    }
    @Override
    public void refillSacchetti() throws InterruptedException {
        necessarioRefill.acquire();
        System.out.println("è iniziato il refill dei sacchetti, ci vorrà poco tempo");
        TimeUnit.SECONDS.sleep(10);
        numeroSacchettiMagazzino = 200;
        System.out.println("il refill è stato effettuato con successo");
        puoPagare.release();
    }
    public int incassiTotali(){
        return incassiTotali;
    }
    public static void main(String[] args) throws InterruptedException {
        AziendaAgricola aa = new AziendaAgricolaImplSemafori();
        aa.test(aa);
    }


}

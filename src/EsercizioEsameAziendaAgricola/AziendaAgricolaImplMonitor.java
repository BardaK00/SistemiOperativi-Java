package EsercizioEsameAziendaAgricola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaImplMonitor implements AziendaAgricola{
    private int numeroSacchettiMagazzino = 200;
    private int costoSacchetto = 3;
    private int incassiTotali = 0;

    private Lock l = new ReentrantLock(true);
    private Lock lRit = new ReentrantLock(true);


    private Condition sacchettiTerminati = lRit.newCondition();
    private boolean nonBastanoSacchetti = false;

    private Condition refillEffettuato = lRit.newCondition();

    @Override
    public void iniziaPagamento(int n) throws InterruptedException {
        l.lock();
        try{
        System.out.println("il cliente " + Thread.currentThread().getName() + " ha iniziato a pagare");
        incassiTotali += n * costoSacchetto;}finally{l.unlock();}
        //lascio lock e unlock per l'accesso a incassitotali in mutua esclusione
    }

    @Override
    public void terminaPagamento() throws InterruptedException {
        l.lock();
        try {
            System.out.println("il cliente" + Thread.currentThread().getName() + " ha terminato il pagamento");
        }finally{l.unlock();}
    }

    @Override
    public void iniziaRitiro(int n) throws InterruptedException {
        lRit.lock();
        try {
            System.out.println("il cliente " + Thread.currentThread().getName() + " puo' iniziare a ritirare");

            if(numeroSacchettiMagazzino - n < 0){
                System.out.println("i numeri dei sacchetti non bastano è necessario un refill");
                sacchettiTerminati.signal();
                nonBastanoSacchetti = true;
                while(nonBastanoSacchetti)refillEffettuato.await();
            }
            for(int i = 1;i<n+1;i++){
                TimeUnit.SECONDS.sleep(3);
                System.out.println("il cliente ha ritirato il sacco:" + i + " ne mancano:" + (n-i));
            }
            // nel lock necessario perchè un thread alla volta può ritirare dal magazzino sennò si fotte tutto

            numeroSacchettiMagazzino -= n;
        }finally{lRit.unlock();}


    }

    @Override
    public void terminaRitiro() throws InterruptedException {
        lRit.lock();
        try{
            System.out.println("il cliente ha terminato il suo ritiro ed è uscito dall'azienza");

        }finally{lRit.unlock();}

    }

    @Override
    public void refillSacchetti() throws InterruptedException {
        lRit.lock();
        try {
            while (!(nonBastanoSacchetti)) sacchettiTerminati.await();
            System.out.println("inizio refill");
            TimeUnit.SECONDS.sleep(10);
            numeroSacchettiMagazzino = 200;
            System.out.println("i sacchi sono stati refillati");
            nonBastanoSacchetti=false;
            refillEffettuato.signalAll();
        }finally{lRit.unlock();}
    }

    @Override
    public void verificaNecessarioRefill() throws InterruptedException {

    }

    @Override
    public int incassiTotali() {
        return incassiTotali;
    }

    static void main() throws InterruptedException {
        AziendaAgricola aa = new AziendaAgricolaImplMonitor();
        aa.test(aa);
    }
}

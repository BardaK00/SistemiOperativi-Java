package EsercizioEsameAulaUniversitaria;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AulaUniversitariaImplMonitor extends AulaUniversitaria{
    private Lock l = new ReentrantLock(true);
    private Condition puoIniziare = l.newCondition();
    private Condition possonoConsegnare = l.newCondition();
    private int numeroSeduti = 0;
    private boolean esameTerminato = false;

    public AulaUniversitariaImplMonitor(int n) {
        super(n); //numeroStudenti
    }

    @Override
    void prendiPosto() throws InterruptedException {
        l.lock();
        try{
            System.out.println("lo studente:" + Thread.currentThread().getName() + " si è seduto");
            numeroSeduti++;
            if(numeroSeduti == numeroStudenti)puoIniziare.signal();
        }finally{
            l.unlock();
        }
    }

    @Override
    void consegnaCompito() throws InterruptedException {
        l.lock();
        try{
            while(!(esameTerminato))possonoConsegnare.await();
            System.out.println("lo studente:"+ Thread.currentThread().getName() + " ha consegnato il compito");
        }finally{l.unlock();}
    }

    @Override
    void iniziaEsame() throws InterruptedException {
        l.lock();
        try{
            while(numeroSeduti != numeroStudenti)puoIniziare.await();
            System.out.println("il docente:" + Thread.currentThread().getName() + " ha fatto iniziare l'esame");
            TimeUnit.SECONDS.sleep(5);
        }finally{l.unlock();}

    }

    @Override
    void fineEsame() throws InterruptedException {
        l.lock();
        try{
            esameTerminato = true;
            System.out.println("il docente ha dichiarato la fine dell'esame");
            possonoConsegnare.signalAll();

        }finally{l.unlock();}
    }



    static void main() {
        AulaUniversitaria au = new AulaUniversitariaImplMonitor(45);
        au.test(au);
    }
}

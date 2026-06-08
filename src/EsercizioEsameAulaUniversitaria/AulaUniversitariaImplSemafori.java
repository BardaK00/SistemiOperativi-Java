package EsercizioEsameAulaUniversitaria;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AulaUniversitariaImplSemafori extends AulaUniversitaria{
    private Semaphore mutex = new Semaphore(1,true);
    int numeroStudentiSeduti = 0;

    private Semaphore esameTerminato = new Semaphore(0);
    private Semaphore puoIniziare = new Semaphore(0);
    private Semaphore possonoConsegnare = new Semaphore(0);

    public AulaUniversitariaImplSemafori(int n) {
        super(n); //numeroStudenti
    }

    @Override
    void prendiPosto() throws InterruptedException {
        mutex.acquire();
        System.out.println("lo studente:" + Thread.currentThread().getName() + " si è seduto");
        numeroStudentiSeduti ++;
        if(numeroStudentiSeduti == numeroStudenti){
            puoIniziare.release();
        }
        mutex.release();
    }

    @Override
    void consegnaCompito() throws InterruptedException {
        possonoConsegnare.acquire();
        mutex.acquire();
        System.out.println("lo studente:"+ Thread.currentThread().getName() + " ha consegnato il compito");
        mutex.release();
        possonoConsegnare.release();
    }

    @Override
    void iniziaEsame() throws InterruptedException {
        puoIniziare.acquire();
        System.out.println("il docente:" + Thread.currentThread().getName() + " ha fatto iniziare l'esame");
        TimeUnit.SECONDS.sleep(15);
        esameTerminato.release();

    }

    @Override
    void fineEsame() throws InterruptedException {
        esameTerminato.acquire();
        System.out.println("il docente ha dichiarato la fine dell'esame");
        possonoConsegnare.release();

    }

    static void main() {
        AulaUniversitaria au = new AulaUniversitariaImplSemafori(47);
        au.test(au);
    }
}

package EsercizioEsameAulaUniversitaria;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AulaUniversitariaSemaforiCodeSeparate extends AulaUniversitaria{
    private int[] numeroStudentiPerTracce;
    LinkedList<Thread> codaA = new LinkedList<>();
    LinkedList<Thread> codaB = new LinkedList<>();
    LinkedList<Thread> codaC = new LinkedList<>();
    LinkedList<Thread> codaD = new LinkedList<>();
    LinkedList<Thread> codaTutti = new LinkedList<>();
    private int numeroStudentiSeduti = 0;

    private Semaphore mutex = new Semaphore(1,true);
    private Semaphore esameTerminato = new Semaphore(0);
    private Semaphore puoIniziare = new Semaphore(0);
    private Semaphore possonoConsegnare = new Semaphore(0);

    public AulaUniversitariaSemaforiCodeSeparate(int n) {
        super(n);
    }


    @Override
    void prendiPosto() throws InterruptedException {
        mutex.acquire();
        int traccia = new Random().nextInt(1,5);
        numeroStudentiSeduti ++;
        switch(traccia){
            case 1:codaA.add(Thread.currentThread());break;
            case 2:codaB.add(Thread.currentThread());break;
            case 3:codaC.add(Thread.currentThread());break;
            case 4:codaD.add(Thread.currentThread());break;
        }
        codaTutti.add(Thread.currentThread());
        System.out.println("studente:" + Thread.currentThread().getName() + " si è seduto e ha ottenuto la traccia:" + traccia);
        if(numeroStudentiSeduti == numeroStudenti){
            System.out.println("tutti gli studenti hanno preso posto");
            puoIniziare.release();
        }
        mutex.release();



    }

    @Override
    void consegnaCompito() throws InterruptedException {
        possonoConsegnare.acquire();
        boolean consegnato = false;
        while (!consegnato) {
            mutex.acquire();
            Thread me = Thread.currentThread();

            if (!codaA.isEmpty() && codaA.getFirst().equals(me)) {
                System.out.println("lo studente:" + me.getName() + " ha consegnato il compito A");
                codaA.removeFirst();
                codaTutti.remove(me);
                consegnato = true;
            }
            else if (codaA.isEmpty() && !codaB.isEmpty() && codaB.getFirst().equals(me)) {
                System.out.println("lo studente:" + me.getName() + " ha consegnato il compito B");
                codaB.removeFirst();
                codaTutti.remove(me);
                consegnato = true;
            }
            else if (codaA.isEmpty() && codaB.isEmpty() && !codaC.isEmpty() && codaC.getFirst().equals(me)) {
                System.out.println("lo studente:" + me.getName() + " ha consegnato il compito C");
                codaC.removeFirst();
                codaTutti.remove(me);
                consegnato = true;
            }
            else if (codaA.isEmpty() && codaB.isEmpty() && codaC.isEmpty() && !codaD.isEmpty() && codaD.getFirst().equals(me)) {
                System.out.println("lo studente:" + me.getName() + " ha consegnato il compito D");
                codaD.removeFirst();
                codaTutti.remove(me);
                consegnato = true;
            }
            mutex.release();

            if (!consegnato) {
                possonoConsegnare.release();
                possonoConsegnare.acquire();
            }
        }

        possonoConsegnare.release();
    }

    @Override
    void iniziaEsame() throws InterruptedException {
        puoIniziare.acquire();
        System.out.println("il docente:" + Thread.currentThread().getName() + " ha fatto iniziare l'esame");
        TimeUnit.SECONDS.sleep(3);
        esameTerminato.release();

    }

    @Override
    void fineEsame() throws InterruptedException {
        esameTerminato.acquire();
        System.out.println("il docente ha dichiarato la fine dell'esame");
        possonoConsegnare.release();

    }

    static void main() {
        AulaUniversitaria au = new AulaUniversitariaSemaforiCodeSeparate(47);
        au.test(au);
    }
}

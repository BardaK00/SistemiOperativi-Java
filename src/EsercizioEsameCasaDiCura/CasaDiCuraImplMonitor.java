package EsercizioEsameCasaDiCura;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CasaDiCuraImplMonitor implements CasaDiCura{
    Lock l = new ReentrantLock();
    Condition puoChiamare = l.newCondition();
    boolean puoEntrare = false;

    Condition nonPuoEntrareSalaPreparazione = l.newCondition();
    int nPazientiSalaPrep = 0;

    Condition nonPuoEntrareSalaOperatoria = l.newCondition();
    int nPazientiSalaOper = 0;

    Condition puoIniziareOperazione = l.newCondition();
    boolean startOperazione = false;
    boolean operazioneConclusa = false;

    Condition operazioneTerminata = l.newCondition();


    @Override
    public void chiamaEIniziaOperazione() throws InterruptedException {
        l.lock();
        try{
            while(nPazientiSalaPrep < 3){
                puoChiamare.await();
            }
            while(nPazientiSalaOper >= 1 && puoEntrare){
                nonPuoEntrareSalaOperatoria.await();
            }
            System.out.println("il dottore ha chiamato un paziente in sala operatoria");
            nPazientiSalaOper++;
            System.out.println("si è liberato un posto in sala Preparazione");
            nPazientiSalaPrep --;
            nonPuoEntrareSalaPreparazione.signal();
            puoIniziareOperazione.signal();
            startOperazione=true;
        }finally{l.unlock();}
    }

    @Override
    public void fineOperazione() throws InterruptedException {
        l.lock();
        try {
            while (!(startOperazione)) {
                puoIniziareOperazione.await();
            }
        }finally{
            l.unlock();
        }

        System.out.println("l'operazione è iniziata");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10,20+1));
        System.out.println("l'operazione è terminata");

        l.lock();
        try {
            operazioneConclusa = true;
            operazioneTerminata.signal();
        } finally {
            l.unlock();
        }


    }

    @Override
    public void pazienteEntra() throws InterruptedException {
        l.lock();
        try{
            while(nPazientiSalaPrep >= 3){
                nonPuoEntrareSalaPreparazione.await();
            }
            nPazientiSalaPrep++;
            System.out.println("il paziente:"+ Thread.currentThread().getName()+" è entrato in sala preparazione");
            puoEntrare = true;
            if(nPazientiSalaPrep == 3){
                puoChiamare.signal();
            }

        }finally{
            l.unlock();
        }

    }

    @Override
    public void pazienteEsci() throws InterruptedException {
        l.lock();
        try{
            while(!(operazioneConclusa)){
                operazioneTerminata.await();
            }
        }finally{
            l.unlock();
        }

        l.lock();
        try{
            System.out.println("il paziente" + Thread.currentThread().getName() + " sta uscendo dalla sala operatoria");
            nPazientiSalaOper--;
            startOperazione = false;
            operazioneConclusa = false;
            puoEntrare = false;
            nonPuoEntrareSalaOperatoria.signalAll();
        }finally{l.unlock();}
    }

    static void main() {
        CasaDiCura cc = new CasaDiCuraImplMonitor();
        cc.test(cc);
    }
}

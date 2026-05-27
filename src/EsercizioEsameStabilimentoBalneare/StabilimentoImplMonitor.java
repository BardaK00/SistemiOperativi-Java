package EsercizioEsameStabilimentoBalneare;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StabilimentoImplMonitor extends Stabilimento{
    private int numeroBagnanti;
    private int[] scelte;

    private Lock l = new ReentrantLock();
    private int cont = 0;
    private int postazioniSistemate = 0;
    private int contPagamento = 0;

    private Condition tuttiHannoScelto = l.newCondition();
    private Condition possonoPagare = l.newCondition();
    private Condition puoChiudere = l.newCondition();
    public StabilimentoImplMonitor (int n){
        numeroBagnanti = n;
        scelte = new int[n];
    }

    @Override
    void scegliAccesso() throws InterruptedException {
        l.lock();
        try{
        scelte[cont] =new Random().nextInt(0,2); ;
        System.out.println("il cliente" + Thread.currentThread().getName() + " ha scelto:" + scelte[cont]);
        cont++;
        if(cont == numeroBagnanti)tuttiHannoScelto.signal();
        }finally{l.unlock();}


    }

    @Override
    void preparaPostazioni() throws InterruptedException {
        l.lock();
        try{
            while(cont != numeroBagnanti )tuttiHannoScelto.await();
            System.out.println("il Gestore sta preparando le postazioni");
            for(int i = 0;i<scelte.length;i++){
                if(scelte[i]==0){
                    System.out.println("il cliente:" + i + " ha scelto LETTINO , la preparazione dura 2 secondi");
                    TimeUnit.SECONDS.sleep(2);
                }else{
                    System.out.println("il cliente:" + i + " ha scelto OMBRELLONE , la preparazione dura 2 secondi");
                    TimeUnit.SECONDS.sleep(3);
                }
                postazioniSistemate++;
            }
            System.out.println("il gestore ha completato la sistemazione delle postazioni");
            possonoPagare.signal();
        }finally{l.unlock();}
    }

    @Override
    void paga() throws InterruptedException {
        l.lock();
        try{
            while(!(postazioniSistemate == numeroBagnanti))possonoPagare.await();
            System.out.println("il cliente" + Thread.currentThread().getName() + " ha iniziato a pagare");
            if(scelte[contPagamento] == 0){
                System.out.println("il cliente" + Thread.currentThread().getName() + " ha pagato 10 euro");
            }else{
                System.out.println("il cliente" + Thread.currentThread().getName() + " ha pagato 15 euro");
            }
            contPagamento++;
            possonoPagare.signal();
            if(contPagamento == numeroBagnanti)puoChiudere.signal();
        }finally{l.unlock();}
    }

    @Override
    void chiusura() throws InterruptedException {
        l.lock();
        try{
            while(contPagamento != numeroBagnanti)puoChiudere.await();
            System.out.println("tutti i bagnanti hanno pagato e hanno lascialo lo stabilimento");
            System.out.println("il Gestore ha chiuso lo stabilimento");
        }finally{l.unlock();}
    }


    static void main() {

        int clienti = 10;
        Stabilimento s = new StabilimentoImplMonitor(clienti);
        Gestore g = new Gestore(s);
        g.start();
        for (int i = 0;i<clienti;i++){
            new Cliente(s).start();
        }

    }
}

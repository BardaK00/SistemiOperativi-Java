package EsercizioEsameBiblioteca;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.exit;

public class BibliotecaImplMonitor extends Biblioteca{
    private Lock l = new ReentrantLock();
    private LinkedList<Thread> llTesserati = new LinkedList<>();
    private LinkedList<Thread> llNonTesserati = new LinkedList<>();
    private Random ran = new Random();

    private Condition clientiSistemati = l.newCondition();
    private int prestitiRichiesti = 0;

    private Condition clienteGestito = l.newCondition();
    private boolean isGestito = false;

    private Condition clienteUscito = l.newCondition();
    private boolean chiamareProssimo = false;

    private int numeroUsciti = 0;

    private String nomeThread = "";
    public BibliotecaImplMonitor(int n) {
        super(n);
    }

    @Override
    void richiediPrestito() throws InterruptedException {
        l.lock();
        try{
            int sceltaLibro = ran.nextInt(1000,10000);
            int isTesserato = ran.nextInt(0,2);
            if(isTesserato == 0){
                System.out.println("il cliente "+Thread.currentThread().getName()+ " TESSERATO ha scelto il libro:"+ sceltaLibro);
                llTesserati.add(Thread.currentThread());
                prestitiRichiesti++;}
            else{
                System.out.println("il cliente "+Thread.currentThread().getName()+ " NON TESSERATO ha scelto il libro:"+ sceltaLibro);
                llNonTesserati.add(Thread.currentThread());
                prestitiRichiesti++;
            }
            if(prestitiRichiesti == super.numeroClienti)clientiSistemati.signal();

        }finally{

            l.unlock();
        }


    }

    @Override
    void registraPrestito() throws InterruptedException {
        l.lock();
        try{
            while(prestitiRichiesti != super.numeroClienti)clientiSistemati.await();
            Thread cliente;
            if(!(llTesserati.isEmpty())) {
                cliente = llTesserati.removeFirst();
                System.out.println("il bibliotecario ha gestito la richiesta di:" + cliente.getName() + " TESSERATO");
                nomeThread = cliente.getName();
                isGestito = true;
                clienteGestito.signalAll();
            }else if(llTesserati.isEmpty() && !llNonTesserati.isEmpty()){
                cliente = llNonTesserati.removeFirst();
                System.out.println("il bibliotecario ha gestito la richiesta di:" + cliente.getName()+ " NON TESSERATO");
                nomeThread = cliente.getName();
                isGestito = true;
                clienteGestito.signalAll();
            }

        }finally{l.unlock();}
    }

    @Override
    void esci() throws InterruptedException {
        l.lock();
            try {
                while (!isGestito || !(Thread.currentThread().getName().equals(nomeThread))) clienteGestito.await();
                System.out.println("il cliente  "+ nomeThread +  " è uscito dalla biblioteca");
                numeroUsciti++;
                nomeThread = "";
                isGestito = false;
                chiamareProssimo = true;
                clienteUscito.signal();
            } finally {
                l.unlock();
            }
    }

    @Override
    void prossimoUtente() throws InterruptedException {
        l.lock();
        if(numeroUsciti == super.numeroClienti)exit(0);
        try{
            while(!chiamareProssimo)clienteUscito.await();
            System.out.println("il bibliotecario ha chiamato un nuovo utente");

            chiamareProssimo=false;
            clientiSistemati.signal();
        }finally{
            l.unlock();
        }
    }

    static void main() {
        Biblioteca b = new BibliotecaImplMonitor(25);
        b.test(b);
    }
}

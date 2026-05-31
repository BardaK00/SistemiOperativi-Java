package EsercizioEsameBiblioteca;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;


import static java.lang.System.exit;

public class BibliotecaImplSemafori extends Biblioteca{
    private Random ran = new Random();
    private LinkedList< Thread> llTesserati = new LinkedList<>();
    private LinkedList< Thread> llNonTesserati = new LinkedList<>();

    private Semaphore mutex = new Semaphore(1,true);

    private Semaphore prestitoRichiesto = new Semaphore(0);

    private Semaphore prestitoGestito= new Semaphore(0,true);

    private Semaphore clienteUscito = new Semaphore(0);
    private int richiesteGestite = 0;

    private String nomeThread = "";
    public BibliotecaImplSemafori(int n) {
        super(n);
    }

    @Override
    void richiediPrestito() throws InterruptedException {
        mutex.acquire();

        int isTesserato = ran.nextInt(0,2);
        int libroScelto = ran.nextInt(1000,10000);

        if(isTesserato == 0){
            llTesserati.add(Thread.currentThread());
        }else{
            llNonTesserati.add(Thread.currentThread());
        }
        System.out.println("il cliente ha scelto di prenotare il libro:" + libroScelto);
        mutex.release();

        if(llTesserati.size()+llNonTesserati.size() == super.numeroClienti)prestitoRichiesto.release();
    }

    @Override
    void registraPrestito() throws InterruptedException {
        prestitoRichiesto.acquire();
        Thread cliente;
        if (!(llTesserati.isEmpty())) {
            cliente = llTesserati.removeFirst();
            System.out.println("il bibliotecario ha gestito un prestito di un TESSERATO:" + cliente.getName());
            System.out.println("il cliente può uscire...");
            richiesteGestite++;
            nomeThread = cliente.getName();
            prestitoGestito.release();

        }else if (!llNonTesserati.isEmpty()) {
            cliente = llNonTesserati.removeFirst();
            System.out.println("il bibliotecario ha gestito un prestito di un NON TESSERATO:" + cliente.getName());

            System.out.println("il cliente può uscire...");
            richiesteGestite++;
            nomeThread = cliente.getName();
            prestitoGestito.release();
        }

    }

    @Override
    void esci() throws InterruptedException {
        boolean uscito = false;
        while (!uscito) {
            prestitoGestito.acquire();
            mutex.acquire();
            if (Thread.currentThread().getName().equals(nomeThread)) {
                System.out.println("il cliente " + nomeThread + " è uscito dalla biblioteca");
                nomeThread = "";
                uscito = true;
                mutex.release();
                System.out.println("il bibliotecario può chiamare un nuovo utente");
                clienteUscito.release();
            } else {
                prestitoGestito.release();
                mutex.release();
            }
        }
    }
    @Override
    void prossimoUtente() throws InterruptedException {
        clienteUscito.acquire();
        if(richiesteGestite == super.numeroClienti){
            System.out.println("Tutti i clienti sono stati gestiti");
            exit(0);
        }
        System.out.println("è stato chiamato un nuovo utente");
        prestitoRichiesto.release();
    }

    static void main() {
        Biblioteca b = new BibliotecaImplSemafori(25);
        b.test(b);
    }
}

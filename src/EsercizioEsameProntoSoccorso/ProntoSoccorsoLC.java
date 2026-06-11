package EsercizioEsameProntoSoccorso;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProntoSoccorsoLC extends ProntoSoccorso{
    private Lock l = new ReentrantLock();

    private Condition clienteAttesa = l.newCondition();
    private boolean prossimoChiamabile = true;

    private Paziente p = null;
    private Condition clienteTerminato = l.newCondition();
    private Condition prossimoPaziente = l.newCondition();

    private LinkedList<Paziente> codiceVerde = new LinkedList<>();
    private LinkedList<Paziente> codiceGiallo = new LinkedList<>();
    private LinkedList<Paziente> codiceRosso = new LinkedList<>();
    private LinkedList<Paziente> pazienteTerminato = new LinkedList<>();
    @Override
    void iniziaVisita() throws InterruptedException {
        l.lock();
        int codice = -1;
        try {
            while(codiceGiallo.isEmpty() && codiceVerde.isEmpty() && codiceRosso.isEmpty()) {
                clienteAttesa.await();
            }

            while(!prossimoChiamabile)prossimoPaziente.await();
            // verifica se ci sono prima pazienti in codice rosso
            if(!codiceRosso.isEmpty()){
                p = codiceRosso.removeFirst();
                System.out.println("inziata visita per:" + p.getName() + " in codice rosso");
                codice = 1;
            }else if(codiceRosso.isEmpty() && !codiceGiallo.isEmpty()){
                p = codiceGiallo.removeFirst();
                System.out.println("inziata visita per:" + p.getName() + " in codice giallo");
                codice=2;
            }else if(codiceRosso.isEmpty() && codiceGiallo.isEmpty() && !codiceVerde.isEmpty()){
                p = codiceVerde.removeFirst();
                System.out.println("inziata visita per:" + p.getName() + " in codice verde");
                codice=3;
            }
        } finally{
            l.unlock();
        }


        switch(codice){
            case 1: TimeUnit.SECONDS.sleep(10);
                break;
            case 2: TimeUnit.SECONDS.sleep(5);
                break;
            case 3: TimeUnit.SECONDS.sleep(3);
                break;
        }
        l.lock();
        try {
            prossimoChiamabile = false;
        }finally{l.unlock();}

    }

    @Override
    void terminaVisita() {
        l.lock();
        try {
            if (p != null) {
                System.out.println("La visita è terminata. Il cliente " + p.getName() + " può uscire.");
                pazienteTerminato.add(p);
                clienteTerminato.signalAll();
                p = null;
            }
        } finally {
            l.unlock();
        }
    }

    @Override
    void accediPaziente() {
        l.lock();
        try{
            Paziente t = (Paziente) Thread.currentThread();
            switch(t.getCodice()){
                case "verde":codiceVerde.add(t);
                    System.out.println("entrato codice verde");
                    clienteAttesa.signal();
                break;

                case "giallo":codiceGiallo.add(t);
                    System.out.println("entrato codice giallo");
                    clienteAttesa.signal();
                break;

                case "rosso" :codiceRosso.add(t);
                    System.out.println("entrato codice rosso");
                    clienteAttesa.signal();
                    break;
            }
        }finally{
            l.unlock();
        }
    }

    @Override
    void esciPaziente() throws InterruptedException {
        l.lock();
        try {
            Paziente p = (Paziente) Thread.currentThread();

            while (!pazienteTerminato.contains(p)) {
                clienteTerminato.await();
            }
            pazienteTerminato.remove(p);
            System.out.println("Il paziente: " + p.getName() + " è uscito dal Pronto Soccorso.");
            prossimoChiamabile = true;
            prossimoPaziente.signal();
        } finally {
            l.unlock();
        }
    }

    static void main() {
        ProntoSoccorso ps = new ProntoSoccorsoLC();
        ps.test(ps,10);

    }
}

package EsercizioEsameUfficioPostale;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UfficioPostaleLC extends UfficioPostale{
    private Lock l = new ReentrantLock();
    private Condition finitiA = l.newCondition();
    private Condition finitiB = l.newCondition();
    private Condition finitiC = l.newCondition();


    private Condition listaAVuota=  l.newCondition();
    private Condition listaBVuota=  l.newCondition();
    private Condition listaCVuota=  l.newCondition();

    private Condition deveAspettare = l.newCondition();
    private Condition eseguiOperazione = l.newCondition();

    private boolean chiamaA = false;
    private boolean chiamaB = false;
    private boolean chiamaC = false;

    private int codiceA = 50;
    private int codiceB = 50;
    private int codiceC = 50;

    private LinkedList<Cliente> listaA = new LinkedList<>();
    private LinkedList<Cliente> listaB = new LinkedList<>();
    private LinkedList<Cliente> listaC = new LinkedList<>();

    @Override
    boolean ritiraTicket(String operazione) throws InterruptedException {
        l.lock();
        try{
            System.out.println("il cliente:"+ Thread.currentThread().getName() + " ha ritirato un ticket con operazione:"+ operazione);
            switch(operazione){
                case "a":
                    if(codiceA > 0){
                        codiceA--;
                        listaA.add((Cliente)Thread.currentThread());
                        listaAVuota.signal();
                        return true;
                    }else{
                        while(codiceA == 0)finitiA.await();
                    }break;
                case "b":
                    if(codiceB > 0){
                        codiceB--;
                        listaB.add((Cliente)Thread.currentThread());
                        listaBVuota.signal();
                        return true;
                    }else{
                        while(codiceB == 0)finitiB.await();
                    }break;
                case "c":
                    if(codiceC > 0){
                        codiceC--;
                        listaC.add((Cliente)Thread.currentThread());
                        listaCVuota.signal();
                        return true;
                    }else{
                        while(codiceC == 0)finitiC.await();
                    }break;
            }}finally{
            l.unlock();
        }
        return false;
    }

    @Override
    void attendiSportello(String operazione) throws InterruptedException {
        l.lock();
        System.out.println("il cliente:"+ Thread.currentThread().getName() +"è in attesa allo sportello:" + operazione);
        try{
            switch(operazione){
                case "a": chiamaA = true;break;
                case "b": chiamaB = true;break;
                case "c": chiamaC = true;break;
            }
            while(chiamaA || chiamaB || chiamaC)deveAspettare.await();
            System.out.println("il cliente ha terminato allo sportello, sta uscendo");
        }finally{l.unlock();}
    }

    @Override
    void prossimoCliente() throws InterruptedException {
        l.lock();
        try{
            String operazione = "";
            Impiegato i = (Impiegato)Thread.currentThread();
             switch(i.tipoOperazione){
                 case "a":
                     while(listaA.isEmpty())listaAVuota.await();
                     if (chiamaA){
                         Cliente c = listaA.removeFirst();
                         System.out.println("è stato chiamato il cliente:" + c.getName());
                         deveAspettare.signal();
                     }break;
                 case "b":
                     while(listaB.isEmpty())listaBVuota.await();
                     if (chiamaB){
                         Cliente c = listaB.removeFirst();
                         System.out.println("è stato chiamato il cliente:" + c.getName());
                         deveAspettare.signal();
                     }break;
                 case "c":
                     while(listaC.isEmpty())listaCVuota.await();
                     if (chiamaC ){
                         Cliente c = listaC.removeFirst();
                         System.out.println("è stato chiamato il cliente:" + c.getName());
                         deveAspettare.signal();
                     }break;
             }
            }finally{
            l.unlock();
        }
    }

    @Override
    void eseguiOperazione() throws InterruptedException {
        l.lock();
        String operazione = "";
        try{
            System.out.println("l'impiegato:" + Thread.currentThread().getName() + " ha chiamato il prossimo cliente");
            Impiegato i = (Impiegato)Thread.currentThread();
            switch(i.tipoOperazione){
                case "a":while(!chiamaA){
                    eseguiOperazione.await();}
                    break;
                case "b":while(!chiamaB){
                    eseguiOperazione.await();}
                    break;
                case "c":while(!chiamaC){
                    eseguiOperazione.await();}
                    break;
            }
            }finally{l.unlock();}
        switch(operazione){
            case "a":chiamaA = false; TimeUnit.SECONDS.sleep(6);break;
            case "b": chiamaB= false;TimeUnit.SECONDS.sleep(4);break;
            case "c":chiamaC = false; TimeUnit.SECONDS.sleep(2);break;
        }
    }

    static void main() {
        int clienti = 100;
        UfficioPostale up = new UfficioPostaleLC();
        up.test(up,clienti);
    }
}
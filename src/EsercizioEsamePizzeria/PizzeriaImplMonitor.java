package EsercizioEsamePizzeria;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PizzeriaImplMonitor implements Pizzeria{
    Lock l = new ReentrantLock();
    Condition puoSedersi = l.newCondition();
    Condition tavoloPieno = l.newCondition();
    Condition pizzaPronta = l.newCondition();
    Condition pizzaServita = l.newCondition();



    private boolean pServitaBool = false;
    private boolean pizzaFinita = false;

    int personeFinito = 0;
    int postiOccupati = 0;

    @Override
    public void mangiaPizza() throws InterruptedException {
        l.lock();
        try{
            while(postiOccupati==5 ){puoSedersi.await();}
            postiOccupati++;
            System.out.println("il cliente "+ Thread.currentThread().getName() + " si è seduto");
            if(postiOccupati== 5){tavoloPieno.signal();System.out.println("invio signal pizzaiolo");}
            System.out.println(postiOccupati);
        }finally{l.unlock();}

    }

    @Override
    public void pizzaMangiata() throws InterruptedException {

        l.lock();
        try {
            while (!(pServitaBool)) pizzaServita.await();
            System.out.println("il cliente:" + Thread.currentThread().getName() + " ha inziato a mangiare la pizza");
        } finally {
            l.unlock();}
        Random r = new Random();
        TimeUnit.SECONDS.sleep(r.nextInt(10));
        System.out.println("il cliente:" + Thread.currentThread().getName() + " ha finito di mangiare la pizza");
        l.lock();
        try{
        personeFinito++;
        if (personeFinito== 5) {
            pServitaBool = false;
            personeFinito= 0;
            pizzaFinita = false;
            postiOccupati= 0;

            tavoloPieno.signal();
            puoSedersi.signalAll();
        }
        }finally{l.unlock();}
    }

    @Override
    public void preparaPizza() throws InterruptedException {
        l.lock();
        try{
            while(postiOccupati< 5 || pizzaFinita){
                tavoloPieno.await();
            }
        }finally{l.unlock();}

        System.out.println("inizio preparazione pizza");
        TimeUnit.SECONDS.sleep(5);

        l.lock();
        try{
            pizzaFinita = true;
            pizzaPronta.signal();
        }finally{l.unlock();}


    }

    @Override
    public void pizzaPronta() throws InterruptedException {
        l.lock();
        try {
            while (!(pizzaFinita)) pizzaPronta.await();
            System.out.println("la pizza è pronta");
            pServitaBool = true;
            pizzaServita.signalAll();
        }finally{l.unlock();}
    }

    public static void main(String []args) {

        Pizzeria p = new PizzeriaImplMonitor();
        p.test(p);
    }
}

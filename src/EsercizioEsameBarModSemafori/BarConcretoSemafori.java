package EsercizioEsameBarModSemafori;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BarConcretoSemafori implements Bar{
    private Random ran = new Random();
    private static final int payTime = 2;
    private static final int drinkTime = 5;


    protected Semaphore semBancone = new Semaphore(4,true);
    protected Semaphore semCassa = new Semaphore(1,true);



    public int scegli() {

        // cassa libera
        if(semCassa.availablePermits() > 0)
            return 0;

        // bancone libero
        if(semBancone.availablePermits() > 0)
            return 1;

        // entrambe occupate -> scegli fila più corta
        if(semBancone.getQueueLength() < semCassa.getQueueLength())
            return 1;

        // preferenza alla cassa se uguali
        return 0;
    }


    public void finisci(int i) {
        if(i == 0)semCassa.release();
        else semBancone.release();
    }

    public void inizia(int i) {
        if(i != 0 && i != 1)throw new IllegalArgumentException();
        if(i == 0){
            try{
                semCassa.acquire();
                TimeUnit.SECONDS.sleep(ran.nextInt(payTime));
                System.out.println("il cliente sta pagando");
            }
            catch(Exception e){e.printStackTrace();}
        }else{
            try{
                semBancone.acquire();
                TimeUnit.SECONDS.sleep(ran.nextInt(drinkTime));
                System.out.println("il cliente sta bevendo");
            }
            catch(Exception e){e.printStackTrace();}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BarConcretoSemafori b = new BarConcretoSemafori();
        for(int i = 0;i<100;i++){
            new ThreadCliente(b).start();
        }




    }
}

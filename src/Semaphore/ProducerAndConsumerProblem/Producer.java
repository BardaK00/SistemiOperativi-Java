package Semaphore.ProducerAndConsumerProblem;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Producer implements Runnable{
    private Buffer buffer;
    private static final int MAX_RANDOM = 10;
    private Random ran = new Random();

    public Producer(Buffer b){
        buffer = b;
    }

    @Override
    public void run() {
        try{
            while(true) {
                int r = ran.nextInt(MAX_RANDOM);
                TimeUnit.MILLISECONDS.sleep(100); //funzione produci
                buffer.put(r);
            }
        }catch(Exception e){e.fillInStackTrace();}
    }

}

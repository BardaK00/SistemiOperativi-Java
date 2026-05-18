package Semaphore.ProducerAndConsumerProblem;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable{
    private Buffer buffer;
    public Consumer(Buffer b){
        buffer = b;
    }

    @Override
    public void run() {
        try{
            while(true){
                int i =buffer.get();
                this.consuma(i);
            }
        }catch(Exception e){e.fillInStackTrace();}
    }

    private void consuma(int i) throws InterruptedException {
        System.out.println(i);
        TimeUnit.SECONDS.sleep(1);
    }
}


package Semaphore.ProducerAndConsumerProblem;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer{
    private Semaphore ciSonoElementi = new Semaphore(0);
    private Semaphore ciSonoPostiVuoti;
    private Semaphore mutex = new Semaphore(1);


    public BufferSem(int dim) {
        super(dim);
        ciSonoPostiVuoti = new Semaphore(dim);
    }

    @Override
    public int get() throws InterruptedException {
        ciSonoElementi.acquire();
        mutex.acquire();
        int o = buffer[out];
        out = (out + 1 )%buffer.length;
        mutex.release();
        ciSonoPostiVuoti.release();
        return o;
    }

    @Override
    public void put(int i) throws InterruptedException {
        ciSonoPostiVuoti.acquire();
        mutex.acquire();
        buffer[in] = i;
        in = (in+1)%buffer.length;
        mutex.release();
        ciSonoElementi.release();
    }


    public static void main() {
        int dim = 10;
        Buffer buffer = new BufferSem(dim);
        int numProd = 10;
        int numCons = 10;

        for(int i = 0; i< numCons;i++){
            new Thread(new Consumer(buffer)).start();
        }

        for(int i = 0; i< numProd;i++){
            new Thread(new Producer(buffer)).start();
        }


    }
}

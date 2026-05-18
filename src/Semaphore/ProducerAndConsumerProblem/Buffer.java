package Semaphore.ProducerAndConsumerProblem;

public abstract class Buffer {
    protected int[] buffer;
    protected int in = 0;
    protected int out = 0;


    public Buffer(int dim){
        buffer = new int[dim];
    }

    public abstract int get() throws InterruptedException;
    public abstract void put(int i) throws InterruptedException;
}

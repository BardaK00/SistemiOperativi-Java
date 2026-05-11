package SommaNumeriNaturali;

public class ThreadSomma extends Thread{
    private int from;
    private int to;
    private int sum = 0;
    public ThreadSomma(int f, int t){
        from=f;
        to=t;
    }

    public int getSum() throws InterruptedException {
        this.join();
        return sum;
    }

    public void run() {
        for(int i = from;i<=to;i++){
            sum += i;
        }

    }
}

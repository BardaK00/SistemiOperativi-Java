package SommaNumeriNaturali;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        int n= 100;
        int ret = 0;
        ThreadSomma t1 = new ThreadSomma(1,n/2-1);
        ThreadSomma t2 = new ThreadSomma(n/2,n);
        t1.start();
        t2.start();
        ret = t1.getSum() + t2.getSum();
        System.out.println("la somma dei primi " + n + " numeri naturali è:" + ret);

    }
}

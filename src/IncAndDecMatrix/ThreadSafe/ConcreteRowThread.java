package IncAndDecMatrix.ThreadSafe;

import IncAndDecMatrix.NonThreadSafe.ThreadNonSafeAbstract;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteRowThread extends ThreadSafeAbstract implements Runnable{
    private int row ;
    public ConcreteRowThread(AtomicInteger[][] m, int r, int operazioni) {
        super(m,operazioni);
        row = r;
    }

    @Override
    public void run(){

        for(int i = 0;i<super.matrix[row].length;i++){
            for(int j = 0;j<super.nOperazioni;j++){
                super.matrix[row][i].decrementAndGet();
            }
        }
    }
}

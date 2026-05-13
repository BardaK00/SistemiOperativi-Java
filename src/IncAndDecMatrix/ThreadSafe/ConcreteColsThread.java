package IncAndDecMatrix.ThreadSafe;

import IncAndDecMatrix.NonThreadSafe.ThreadNonSafeAbstract;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteColsThread extends ThreadSafeAbstract implements Runnable{
    private int col ;
    public ConcreteColsThread(AtomicInteger[][] m, int c, int operazioni) {
        super(m,operazioni);
        col = c;
    }

    @Override
    public void run(){

            for(int i = 0;i<super.matrix.length;i++){
                for(int j = 0;j<super.nOperazioni;j++){
                    super.matrix[i][col].incrementAndGet();
            }
        }
    }
}
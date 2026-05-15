package Semaphore.IncAndDecMatrixSemaphore;

import java.util.concurrent.Semaphore;

public abstract class AbstractThread{
    private int nOperazioni;
    protected static int[][] matrix ;
    private static Semaphore sem = new Semaphore(1);
    public AbstractThread(int [][] m,int op){matrix = m;nOperazioni = op;}

    public Semaphore getSem(){return sem;}

    public int[][] getMatrice(){return matrix;}

    public int getOp(){return nOperazioni;}
}

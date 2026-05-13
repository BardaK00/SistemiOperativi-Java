package IncAndDecMatrix.NonThreadSafe;

public class ConcreteRowThread extends ThreadNonSafeAbstract implements Runnable{
    private int row ;
    public ConcreteRowThread(int[][] m, int r,int operazioni) {
        super(m,operazioni);
        row = r;
    }

    @Override
    public void run(){

        for(int i = 0;i<super.matrix[row].length;i++){
            for(int j = 0;j<super.nOperazioni;j++){
                super.matrix[row][i] --;
            }
        }
    }
}

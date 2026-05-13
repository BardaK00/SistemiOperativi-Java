package IncAndDecMatrix.NonThreadSafe;

public class ConcreteColsThread extends ThreadNonSafeAbstract implements Runnable{
    private int col ;
    public ConcreteColsThread(int[][] m, int c,int operazioni) {
        super(m,operazioni);
        col = c;
    }

    @Override
    public void run(){

            for(int i = 0;i<super.matrix.length;i++){
                for(int j = 0;j<super.nOperazioni;j++){
                    super.matrix[i][col] ++;
            }
        }
    }
}
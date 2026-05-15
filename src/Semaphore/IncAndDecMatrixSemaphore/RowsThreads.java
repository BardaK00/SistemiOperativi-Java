package Semaphore.IncAndDecMatrixSemaphore;

public class RowsThreads extends AbstractThread implements Runnable{

    private int index;
    public RowsThreads(int[][] m,int index,int operazioni) {
        super(m,operazioni);
        this.index= index;
    }

    private int[][] matReference = this.getMatrice();

    @Override
    public void run() {
        var sem = super.getSem();
        try {
            for (int j = 0; j < super.getOp(); j++) {
                sem.acquire();
                for (int i = 0; i < matReference[index].length; i++) {
                    matReference[index][i]--;
                }
                sem.release();
            }
        } catch (Exception e) {
                e.printStackTrace();}
        }
    }

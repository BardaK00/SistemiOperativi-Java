package MinAndMaxMatrice;

public class MaxThreads extends Thread{
    private int[] row;
    private int maxRow;

    public MaxThreads(int[] partRow){
        this.row = partRow ;
    }

    public void run(){
        int max = -1;
        for (int k : row) {
            if (k > max) max = k ;
        }
        maxRow= max;
    }

    public int getMax(){return maxRow;}
}

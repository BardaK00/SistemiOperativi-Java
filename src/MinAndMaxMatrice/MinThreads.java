package MinAndMaxMatrice;

public class MinThreads extends Thread{
    private int[] cols;
    private int minCols;

    public MinThreads(int[] partCol){
        this.cols = partCol;
    }

    public void run(){
        int min = 2100000000;
        for (int col : cols) {
            if (col < min) min = col;
        }
        minCols = min;
    }

    public int getMin() {return minCols;}
}

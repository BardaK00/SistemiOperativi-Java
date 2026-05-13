package IncAndDecMatrix.NonThreadSafe;

public class ThreadNonSafeAbstract{
    protected int[][] matrix;
    protected int nOperazioni;

    public ThreadNonSafeAbstract(int [][]m,int n){
        matrix = m;
        nOperazioni = n;
    }

    public int[][] getMatrice(){
        return matrix;
    }

    public String toString(){
        var sb = new StringBuilder();
        for(int i = 0;i<matrix.length;i++){
            sb.append("[");
            for(int j= 0;j<matrix[i].length;j++){
                sb.append(matrix[i][j]+ " ");
            }
            sb.append("]");
            sb.append("\n");
        }
        return sb.toString();
    }

}

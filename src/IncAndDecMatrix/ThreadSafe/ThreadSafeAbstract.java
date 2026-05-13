package IncAndDecMatrix.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeAbstract {
    protected AtomicInteger[][] matrix;
    protected int nOperazioni;

    public ThreadSafeAbstract(AtomicInteger[][] m, int n) {
        this.matrix = m;
        this.nOperazioni = n;
    }

    public AtomicInteger[][] getMatrice() {
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            sb.append("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j].get()).append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
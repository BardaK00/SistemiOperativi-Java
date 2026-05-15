package Semaphore.IncAndDecMatrixSemaphore;

import java.util.ArrayList;

public class Main {
    private static int n=4;
    private static int m=5;
    private static int operazioni = 10000;
    public static void main(String[] args) throws InterruptedException {
        //istanzio matrice a 0 su m e n
        var mat = new int[n][m];

        var threadsRows = new ArrayList<RowsThreads>();
        var threadsCols = new ArrayList<ColsThread>();
        //istanzio i runnable su righe e colonne
        for(int i = 0;i<n;i++){threadsRows.add(new RowsThreads(mat,i,operazioni));}
        for(int i = 0;i<m;i++){threadsCols.add(new ColsThread(mat,i,operazioni));}

        //avvio i thread
        var threadsR = new ArrayList<Thread>();
        for(RowsThreads at: threadsRows){threadsR.add(new Thread(at));}
        for(ColsThread ct: threadsCols){threadsR.add(new Thread(ct));}

        for(Thread t :threadsR){t.start();}
        for(Thread t :threadsR){t.join();}

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println();
        }



    }
}

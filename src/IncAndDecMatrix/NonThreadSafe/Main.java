package IncAndDecMatrix.NonThreadSafe;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // definiamo dimensioni (n righe, m colonne)
        int n = 3;
        int m = 4;
        //definiamo numero di operazioni
        int operazioni = 100000;
        int[][] matrice = new int[n][m]; // Inizializzata a tutti 0

        // creiamo l'oggetto base per stampare la matrice all'inizio
        ThreadNonSafeAbstract base = new ThreadNonSafeAbstract(matrice,operazioni);
        System.out.println("Matrice Iniziale:");
        System.out.println(base.toString());
        System.out.println("--------------------------------------");

        List<Thread> threads = new ArrayList<>();

        // lanciamo N thread per le righe (fanno --)
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(new ConcreteRowThread(matrice, i,operazioni));
            threads.add(t);
            t.start();
        }

        //lanciamo M thread per le colonne (fanno ++)
        for (int j = 0; j < m; j++) {
            Thread t = new Thread(new ConcreteColsThread(matrice, j,operazioni));
            threads.add(t);
            t.start();
        }

        // aspettiamo che tutti i thread finiscano (join)
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Matrice Finale:");
        System.out.println(base.toString());
    }
}
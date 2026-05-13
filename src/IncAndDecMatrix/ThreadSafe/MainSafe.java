package IncAndDecMatrix;

import IncAndDecMatrix.ThreadSafe.ConcreteColsThread;
import IncAndDecMatrix.ThreadSafe.ConcreteRowThread;
import IncAndDecMatrix.ThreadSafe.ThreadSafeAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainSafe {
    public static void main(String[] args) {
        // definiamo dimensioni (n righe, m colonne)
        int n = 3;
        int m = 4;
        // definiamo numero di operazioni
        int operazioni = 100000;

        // inizializziamo la matrice di AtomicInteger
        AtomicInteger[][] matrice = new AtomicInteger[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrice[i][j] = new AtomicInteger(0);
            }
        }

        // creiamo l'oggetto base per stampare la matrice all'inizio
        ThreadSafeAbstract base = new ThreadSafeAbstract(matrice, operazioni);
        System.out.println("Matrice Iniziale (Safe):");
        System.out.println(base.toString());
        System.out.println("--------------------------------------");

        List<Thread> threads = new ArrayList<>();

        // lanciamo N thread per le righe (usano decrementAndGet)
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(new ConcreteRowThread(matrice, i, operazioni));
            threads.add(t);
            t.start();
        }

        // lanciamo M thread per le colonne (usano incrementAndGet)
        for (int j = 0; j < m; j++) {
            Thread t = new Thread(new ConcreteColsThread(matrice, j, operazioni));
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

        System.out.println("Matrice Finale (Safe):");
        System.out.println(base.toString());
    }
}
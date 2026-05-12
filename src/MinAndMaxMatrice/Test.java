package MinAndMaxMatrice;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private static int m = 3;
    private static int n = 3;

    private static MaxThreads[] maxThread = new MaxThreads[n];
    private static MinThreads[] minThread = new MinThreads[m];

    private static int[][] a = new int[][]{
            {12, 8, 4},
            {9, 7, 5},
            {6, 2, 1}
    };

    private static int[] getRigheMatrice(int index) {
        int[] ret = new int[m];
        for (int i = 0; i < m; i++) {
            ret[i] = a[index][i];
        }
        return ret;
    }

    private static int[] getColsMatrice(int index) {
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = a[i][index];
        }
        return ret;
    }

    private static void istantiateThread() {

        for (int i = 0; i < n; i++) {
            maxThread[i] = new MaxThreads(getRigheMatrice(i));
        }

        for (int i = 0; i < m; i++) {
            minThread[i] = new MinThreads(getColsMatrice(i));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        istantiateThread();

        //starto i thread
        for (MaxThreads t : maxThread) t.start();
        for (MinThreads t : minThread) t.start();

        System.out.println("startati i threads");

        //effettuo la join
        for (MaxThreads t : maxThread) t.join();
        for (MinThreads t : minThread) t.join();

        List<Integer> maxRighe = new ArrayList<>();
        List<Integer> minColonne = new ArrayList<>();

        for (MaxThreads t : maxThread) maxRighe.add(t.getMax());
        for (MinThreads t : minThread) minColonne.add(t.getMin());

        System.out.println(maxRighe);
        System.out.println(minColonne);
        // ora le 2 arraylist conterranno i valori minimi e massimi di ogni colonna e riga rispettivamente

        int riga = -1;
        int colonna = -1;
        int valore = -1;
        boolean trovato = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
            // verifica per ogni valore di a se soddisfa sia la condizione di essere massimo, che quella di essere minimo
                //se si aggiorna gli indici da returnare e il suo valore
                if (a[i][j] == maxRighe.get(i) &&
                        a[i][j] == minColonne.get(j)) {

                    riga = i;
                    colonna = j;
                    valore = a[i][j];
                    trovato = true;
                }
            }
            if (trovato)break;
        }

        System.out.println("valore trovato: " + valore + " in posizione i:" + riga + " j:" + colonna);
    }
}
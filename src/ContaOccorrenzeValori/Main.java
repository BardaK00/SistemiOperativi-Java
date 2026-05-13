package ContaOccorrenzeValori;

import java.util.*;


public class Main {
    //creo una matrice fittizia per la prova del programma
    public static int [][] matrice = new int[][]{
            {3, 3, 3, 7, 7},
            {7, 7, 7, 7, 7},
            {7, 7, 7, 7, 7},
            {7, 7, 7, 7, 7}
    };
    public static void main(String[] args) throws InterruptedException {
        //indico numero di righe
        int n = 4;
        //inizializzo i valori da voler verificare
        int x = 3;
        int y=7;

        List<ThredConcreto> arrayRunnable = new ArrayList<>();
        List<Thread> arrayThread = new ArrayList<>();
        //inizializzo i thread per le righe e li metto in una linked list
        for(int i = 0;i<n;i++) {
            arrayRunnable.add(new ThredConcreto(matrice, x, y, i));
        }
        for(Runnable r:arrayRunnable)arrayThread.add(new Thread(r));

        for(Thread t:arrayThread){
            t.start();
        }
        for(Thread t:arrayThread){
            t.join();
        }

        boolean res = true;
        for(ThredConcreto r:arrayRunnable) {
            // fa l'and di tutti i result delle singole righe , se vero allora x > y, senno y > x
            res &= r.getResult();
        }
        if(res){
            System.out.println("x > y");
        }else{
            System.out.println("x < y");
        }





    }
}

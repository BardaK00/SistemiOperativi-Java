package EsercizioEsameCasello;

import java.util.Random;

public abstract class Casello {
    protected int porte;
    protected int tariffa;

    public Casello(int n , int t){
        tariffa = t;
        porte = n;
    }

    public int percorriChilometri(){
        return new Random().nextInt(50,100);
    }

    //metodi astratti
     public int scegliPorta(){
        return new Random().nextInt(1,porte+1);
     }

    abstract void entraPortaFifo(int porta) throws InterruptedException;
    abstract void pagaCasello(int kilometri) throws InterruptedException;
    abstract void lasciaPorta(int porta);
    abstract void popolaArray();
    abstract int incassoTotale();

}

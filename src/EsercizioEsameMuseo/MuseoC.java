package EsercizioEsameMuseo;

public abstract class MuseoC {
    private int numeroVisitatori;

    public MuseoC(int n){
        numeroVisitatori = n;
    }

    abstract void visitaSA() throws InterruptedException;
    abstract void terminaVisitaSA() throws InterruptedException; //si mette in coda FIFo per Dama
    abstract void visitaSD() throws InterruptedException; //estrae da fifo per Dama
    abstract void terminaVisitaSD();

    public void test(MuseoC m,int n) throws InterruptedException {
        Visitatore[] array = new Visitatore[n];
        for(int i = 0;i<n;i++){
            array[i]=new Visitatore(m);
        }
        for(Visitatore v:array)v.start();
        for(Visitatore v:array)v.join();
    }
}

package EsercizioEsameCinema;

public abstract class Cinema {
    protected int numeroClienti;
    public Cinema(int n){
        numeroClienti = n;
    }
    abstract void acquistaBiglietto() throws InterruptedException;
    abstract boolean consegnaBiglietto() throws InterruptedException;
    abstract void vediFilm() throws InterruptedException;
    abstract void chiudiCinema() throws InterruptedException;

    public void test(Cinema c,int n){
        new Addetto(c).start();
        System.out.println("startato addetto");

        for(int i = 0;i<n;i++){
            new Cliente(c).start();
        }

    }
}

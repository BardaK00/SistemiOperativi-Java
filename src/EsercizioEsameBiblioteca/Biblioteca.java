package EsercizioEsameBiblioteca;

public abstract class Biblioteca {
    protected  int numeroClienti;
    public Biblioteca(int n){
        numeroClienti = n;
    }
    abstract void richiediPrestito() throws InterruptedException;
    abstract void registraPrestito() throws InterruptedException;
    abstract void esci() throws InterruptedException;
    abstract void prossimoUtente() throws InterruptedException;
    public void test(Biblioteca b){
        Bibliotecario bib = new Bibliotecario(b);
        bib.start();
        for(int i = 0;i<b.numeroClienti;i++){
            new Utente(b).start();
        }

    }
}

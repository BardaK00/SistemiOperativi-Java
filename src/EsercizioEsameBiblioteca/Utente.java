package EsercizioEsameBiblioteca;

public class Utente extends Thread{
    private Biblioteca b;
    public Utente(Biblioteca b1){
            b=b1;}
    public void run(){
        try {
            b.richiediPrestito();
            b.esci();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



package EsercizioEsameBiblioteca;

public class Bibliotecario extends Thread{
    private Biblioteca b;
    public Bibliotecario(Biblioteca b1){
        b=b1;
    }

    public void run(){
        try {
            while(true) {
                b.registraPrestito();
                b.prossimoUtente();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

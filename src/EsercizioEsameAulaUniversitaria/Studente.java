package EsercizioEsameAulaUniversitaria;

public class Studente extends Thread{
    private AulaUniversitaria au;

    public Studente (AulaUniversitaria a){au=a;}

    public void run(){

        try {
            au.prendiPosto();
            au.consegnaCompito();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

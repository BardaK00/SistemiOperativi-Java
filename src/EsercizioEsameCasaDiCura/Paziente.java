package EsercizioEsameCasaDiCura;

public class Paziente extends Thread{
    CasaDiCura c;

    public Paziente(CasaDiCura casa){
        c = casa;
    }

    public void run(){
        try {
            c.pazienteEntra();
            c.pazienteEsci();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

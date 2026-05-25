package EsercizioEsameCasaDiCura;

public class Medico extends Thread{
    private CasaDiCura c;

    public Medico(CasaDiCura casa){
        c=casa;
    }

    public void run(){
        while(true){
            try {
                c.chiamaEIniziaOperazione();
                c.fineOperazione();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

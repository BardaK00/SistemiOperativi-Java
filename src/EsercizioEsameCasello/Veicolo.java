package EsercizioEsameCasello;

import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread{
    private Casello c;
    public Veicolo(Casello cas){c=cas;}

    public void run(){
        int km = c.percorriChilometri();
        System.out.println("il veicolo ha sta percorrendo:" + km + "km quindi arriverà tra:" + (km*4) + " secondi ");
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch(Exception e){e.printStackTrace();}

        int p = c.scegliPorta();
        System.out.println("il veicolo " + this.getName() + " ha scelto la porta:" + p);
        try {
            c.entraPortaFifo(p);
            c.pagaCasello(km);
            c.lasciaPorta(p);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

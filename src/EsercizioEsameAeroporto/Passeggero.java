package EsercizioEsameAeroporto;

import java.util.Random;

public class Passeggero extends Thread{
    private Aeroporto a;
    public Passeggero( Aeroporto ar){
        a = ar;
    }

    public void run(){
        try{
            a.deponeBagagli(new Random().nextInt(1,4));
            a.riceviCartaImbarco();
        }catch(Exception e){e.printStackTrace();}
    }
}

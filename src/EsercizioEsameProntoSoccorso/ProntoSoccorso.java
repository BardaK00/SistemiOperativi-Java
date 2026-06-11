package EsercizioEsameProntoSoccorso;

import java.util.Random;

public abstract class ProntoSoccorso {
    abstract void iniziaVisita() throws InterruptedException;
    abstract void terminaVisita();
    abstract void accediPaziente();
    abstract void esciPaziente() throws InterruptedException;

    public void test(ProntoSoccorso p,int pazienti){
        var m = new Medico(p);
        m.setDaemon(true);
        m.start();
        System.out.println("startato medico");
        for(int i = 0;i<pazienti;i++){
            int codice = new Random().nextInt(1,4);
            switch(codice){
                case 1:new Paziente("rosso",p).start();
                    break;

                case 2:new Paziente("giallo",p).start();
                    break;

                case 3:new Paziente("verde",p).start();
                    break;

            }
        }
    }
}

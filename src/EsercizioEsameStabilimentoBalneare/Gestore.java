package EsercizioEsameStabilimentoBalneare;

public class Gestore extends Thread{
    Stabilimento st;

    public Gestore(Stabilimento s){st = s;}

    public void run(){
        try {
            st.preparaPostazioni();
            st.chiusura();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

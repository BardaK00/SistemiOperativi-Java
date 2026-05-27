package EsercizioEsameStabilimentoBalneare;

public class Cliente extends Thread{
    Stabilimento st;

    public Cliente(Stabilimento s){st = s;}

    public void run(){
        try {
            st.scegliAccesso();
            st.paga();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

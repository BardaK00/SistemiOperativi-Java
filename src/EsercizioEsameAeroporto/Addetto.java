package EsercizioEsameAeroporto;

public class Addetto extends Thread{
    private Aeroporto a;
    public Addetto( Aeroporto ar){
        a = ar;
    }

    public void run(){
        try{
            while (true) {
                a.pesaERegistra();
                a.prossimoPasseggero();
            }
        }catch(Exception e){e.printStackTrace();}
    }
}

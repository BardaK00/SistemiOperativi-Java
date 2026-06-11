package EsercizioEsameProntoSoccorso;

public class Medico extends Thread {
    private ProntoSoccorso ps;

    public Medico(ProntoSoccorso p){ps=p;}

    public void run(){
        try{
            while(true) {
                ps.iniziaVisita();
                ps.terminaVisita();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

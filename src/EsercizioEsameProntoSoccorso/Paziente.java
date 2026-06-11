package EsercizioEsameProntoSoccorso;

public class Paziente extends Thread{
    private String codice;
    private ProntoSoccorso ps;

    public Paziente(String s,ProntoSoccorso p){
        codice = s;
        ps = p;
    }

    public void run(){
        try{
            ps.accediPaziente();
            ps.esciPaziente();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getCodice(){
        return codice;
    }
}

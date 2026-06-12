package EsercizioEsameUfficioPostale;

public class Impiegato extends Thread{
    private UfficioPostale up ;
    protected String tipoOperazione;

    public Impiegato(UfficioPostale u,String tipo){
        up = u;
        tipoOperazione = tipo;
    }

    public void run(){
        try {
            while (true) {
                up.prossimoCliente();
                up.eseguiOperazione();
            }
        }catch(Exception e){e.printStackTrace();}
    }


}

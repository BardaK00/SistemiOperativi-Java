package EsercizioEsameAulaUniversitaria;

public class Docente extends Thread {
    private AulaUniversitaria au;

    public Docente(AulaUniversitaria a){
        au = a;
    }

    public void run(){
        try{
            au.iniziaEsame();
            au.fineEsame();
        }catch(Exception e){e.printStackTrace();}
    }
}

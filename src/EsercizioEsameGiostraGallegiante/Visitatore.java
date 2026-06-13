package EsercizioEsameGiostraGallegiante;

public class Visitatore extends Thread{
    private GiostraGallegiante gg;

    public Visitatore(GiostraGallegiante g){gg=g;}

    public void run(){
        try{
           gg.visSaliUno();
           gg.visScendiUno();
        }catch(Exception e){e.printStackTrace();}
    }
}

package EsercizioEsameGiostraGallegiante;

public class Assistente extends Thread{
    private GiostraGallegiante gg;

    public Assistente(GiostraGallegiante g){
        gg= g;
    }

    public void run(){
        try{
            while(true) {
                gg.giroCompleto = false;
                gg.discesaTerminata = false;
                while (true) {
                    if (gg.giroCompleto) {
                        break;
                    }
                    gg.assFaiSalireUno();
                }
                while (true) {
                    if (gg.discesaTerminata) break;
                    gg.assFaiScendereUno();

                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
}

package EsercizioEsameCinema;

public class Addetto extends Thread{
    Cinema c;
    public Addetto(Cinema cin){
        c = cin;
    }
    public void run(){
        try{
            boolean finito = false;
            while(!finito) {
                boolean a = c.consegnaBiglietto();
                if(a==true)finito = true;
            }
            c.chiudiCinema();
        }catch(Exception e){e.printStackTrace();}
    }
}

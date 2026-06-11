package EsercizioEsameCinema;

public class Cliente extends Thread{
    Cinema c;
    int posto;
    public Cliente(Cinema cin){
        c = cin;
    }
    public void run(){
        try{
            c.acquistaBiglietto();
            c.vediFilm();
        }catch(Exception e){e.printStackTrace();}
    }
    public void setPosto(int n){posto = n;}
}

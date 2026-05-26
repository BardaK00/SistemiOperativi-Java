package EsercizioEsameAziendaAgricola;

public class Magazziniere extends Thread{
    private AziendaAgricola aa;

    public Magazziniere(AziendaAgricola az){aa=az;}

    public void run(){
        while(true){
            try{
                aa.verificaNecessarioRefill();
                aa.refillSacchetti(); // finchè non ce ne sarà bisogno il thread magaziniere rimarra in stato di waiting
            }catch(Exception e){e.printStackTrace();}
        }
    }
}

package EsercizioEsameAziendaAgricola;

public class Cliente extends Thread{
    private AziendaAgricola aa;

    public Cliente(AziendaAgricola az){aa = az;}

    public void run(){
        int n = aa.scegliNumeroSacchetti();
        try{
            aa.iniziaPagamento(n);
            aa.terminaPagamento();
            aa.iniziaRitiro(n);
            aa.terminaRitiro();
        }catch(Exception r){r.printStackTrace();}
    }
}

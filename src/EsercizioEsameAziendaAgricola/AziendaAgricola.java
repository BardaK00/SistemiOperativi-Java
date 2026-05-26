package EsercizioEsameAziendaAgricola;

import java.util.LinkedList;
import java.util.Random;

public interface AziendaAgricola {
    default int scegliNumeroSacchetti(){
        return new Random().nextInt(1,11);
    };

    void iniziaPagamento(int n) throws InterruptedException;
    void terminaPagamento() throws InterruptedException;
    void iniziaRitiro(int n) throws InterruptedException;
    void terminaRitiro() throws InterruptedException;
    void refillSacchetti() throws InterruptedException;
    void verificaNecessarioRefill() throws InterruptedException;
    int incassiTotali();
    default void test(AziendaAgricola aa) throws InterruptedException {
        LinkedList<Cliente> ll = new LinkedList<>();
        new Magazziniere(aa).start();
        for (int i = 0;i<5;i++){
            ll.add(new Cliente(this));
        }
        for(Cliente c:ll)c.start();
        for(Cliente c:ll)c.join();
        System.out.println("tutti i clienti hanno finito e l'azienda agricola ha incassato:" + this.incassiTotali());

    }
}

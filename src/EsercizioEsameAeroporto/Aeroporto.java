package EsercizioEsameAeroporto;

public abstract class Aeroporto {
    abstract void deponeBagagli(int n) throws InterruptedException;
    abstract void pesaERegistra() throws InterruptedException;
    abstract void riceviCartaImbarco() throws InterruptedException;
    abstract void prossimoPasseggero() throws InterruptedException;

    public void test(Aeroporto a){
        new Addetto(a).start();
        for(int i = 0;i<10;i++){
            new Passeggero(a).start();
        }
    }
}

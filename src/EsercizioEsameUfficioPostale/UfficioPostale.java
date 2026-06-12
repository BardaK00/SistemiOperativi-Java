package EsercizioEsameUfficioPostale;

public abstract class UfficioPostale {
    abstract boolean ritiraTicket(String operazione) throws InterruptedException;
    abstract void attendiSportello(String operazione) throws InterruptedException;
    abstract void prossimoCliente() throws InterruptedException;
    abstract void eseguiOperazione() throws InterruptedException;

    public void test(UfficioPostale p,int n){
        new Impiegato(p,"a").start();
        new Impiegato(p,"b").start();
        new Impiegato(p,"c").start();
        for(int i = 0;i<n;i++){
            new Cliente(p).start();
        }
    }
}

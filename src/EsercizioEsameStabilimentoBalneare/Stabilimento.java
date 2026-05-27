package EsercizioEsameStabilimentoBalneare;

public abstract class Stabilimento {
    abstract void scegliAccesso() throws InterruptedException;
    abstract void preparaPostazioni() throws InterruptedException;
    abstract void paga() throws InterruptedException;
    abstract void chiusura() throws InterruptedException;
}

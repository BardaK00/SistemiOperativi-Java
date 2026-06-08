package EsercizioEsameAulaUniversitaria;

public abstract class AulaUniversitaria {
    protected int numeroStudenti;

    public AulaUniversitaria(int n) {
        numeroStudenti = n;
    }

    abstract void prendiPosto() throws InterruptedException;

    abstract void consegnaCompito() throws InterruptedException;

    abstract void iniziaEsame() throws InterruptedException;

    abstract void fineEsame() throws InterruptedException;


    public void test(AulaUniversitaria au){
        new Docente(au).start();
        System.out.println("docente startato");
        for(int i = 0;i<au.numeroStudenti;i++){
            new Studente(au).start();
        }
    }
}

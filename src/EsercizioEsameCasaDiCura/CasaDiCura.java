package EsercizioEsameCasaDiCura;

public interface CasaDiCura {
    void chiamaEIniziaOperazione() throws InterruptedException; //paziente entra in sala operazione e inizia operazione

    void fineOperazione() throws InterruptedException;//operazione conclusa, paziente può uscire, non deve attendere che esca per farne entrare un'altro

    void pazienteEntra() throws InterruptedException;//sospende finchè non entra in sala operatoria

    void pazienteEsci() throws InterruptedException;//finita operazione può uscire

    default void test(CasaDiCura cc) {
        new Medico(this).start();
        for(int i = 0;i<20;i++){
            new Paziente(this).start();
        }
    }
}

package EsercizioEsameGiostraGallegiante;

public abstract class GiostraGallegiante {
    protected boolean giroCompleto = false;
    protected boolean discesaTerminata = false;
    protected int numeroClienti;
    public GiostraGallegiante(int n ){numeroClienti = n;}
    abstract void visSaliUno() throws InterruptedException;
    abstract void visScendiUno() throws InterruptedException;
    abstract void assFaiSalireUno() throws InterruptedException;
    abstract void assFaiScendereUno() throws InterruptedException;
    public void test(GiostraGallegiante g){
        var ass = new Assistente(g);
        ass.setDaemon(true);
        ass.start();
        for(int i = 0;i<g.numeroClienti;i++){
            new Visitatore(g).start();
        }
    }
}

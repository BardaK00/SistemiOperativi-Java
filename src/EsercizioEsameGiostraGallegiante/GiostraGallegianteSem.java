package EsercizioEsameGiostraGallegiante;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class GiostraGallegianteSem extends GiostraGallegiante{
    private Semaphore mutexMolo = new Semaphore(1);
    private Semaphore[] zattere = new Semaphore[5];//array di 5 semafori che rappresentano le zattere
    private Semaphore ciSonoTutti = new Semaphore(0);
    private Semaphore possonoScendere = new Semaphore(0);
    private Semaphore scendeVisitatore = new Semaphore(0);
    private Semaphore sonoSceso = new Semaphore(0);
    private Semaphore zatteraVuota = new Semaphore(7);

    private int indiceZattera = 0;
    private int visitatoriSaliti = 0;

    public GiostraGallegianteSem(int n) {
        super(n);
        for(int i = 0;i<5;i++){
            zattere[i] = new Semaphore(7,true); //ogni zattera contiente 7 permessi di accesso
        }

    }

    @Override
    void visSaliUno() throws InterruptedException {
        mutexMolo.acquire();
        System.out.println("il visitatore:"+ Thread.currentThread().getName() + " è approdato al molo");
        mutexMolo.release();

        zatteraVuota.acquire();
        zattere[indiceZattera].acquire(); // il visitatore si mette in attesa
        System.out.println("il visitatore è entrato sulla zattera");

        mutexMolo.acquire();
        visitatoriSaliti++;
        mutexMolo.release();
        if(visitatoriSaliti == 7)ciSonoTutti.release();
    }

    @Override
    void visScendiUno() throws InterruptedException {
        scendeVisitatore.acquire();
        zattere[indiceZattera].release();
        sonoSceso.release();
    }

    @Override
    void assFaiSalireUno() throws InterruptedException {
        ciSonoTutti.acquire();
        if(visitatoriSaliti == 7){
            System.out.println("tutti i visitatori sono a bordo è possibile partire");
            System.out.println("la giostra si sta spostando");
            TimeUnit.SECONDS.sleep(10);
            possonoScendere.release();
            giroCompleto=true;
            System.out.println("la giostra ha terminato il suo movimento");
        }
    }

    @Override
    void assFaiScendereUno() throws InterruptedException {
        possonoScendere.acquire();
        if(visitatoriSaliti != 0){
            scendeVisitatore.release();
            sonoSceso.acquire();
            System.out.println("visitatore sceso");
            possonoScendere.release();
            visitatoriSaliti--;
        }else{
            discesaTerminata = true;
            System.out.println("tutti i visitatori sono scesi:");
            zatteraVuota.release(7);
        }
    }

    static void main() {
        GiostraGallegiante g = new GiostraGallegianteSem(14);
        g.test(g);
    }
}
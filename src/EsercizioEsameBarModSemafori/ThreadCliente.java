package EsercizioEsameBarModSemafori;

import java.util.concurrent.TimeUnit;

public class ThreadCliente extends Thread{
    private BarConcreto bar;

    public ThreadCliente(BarConcreto b){
        bar = b;
    }

    @Override
    public void run() {
        int i = bar.scegli();
        if(i == 0)
            System.out.println(getName() + " ha scelto CASSA");
        else
            System.out.println(getName() + " ha scelto BANCONE");

        if (i == 0){
            try{
                bar.semCassa.acquire();
                bar.inizia(i);
                bar.finisci(i);
                TimeUnit.SECONDS.sleep(5);
                bar.semBancone.acquire();
                bar.inizia(i+1);
                bar.finisci(i+1);
            }
            catch(Exception e){e.printStackTrace();}

        }else{
            try{
                bar.semBancone.acquire();
                bar.inizia(i);
                bar.finisci(i);
                TimeUnit.SECONDS.sleep(5);
                bar.semCassa.acquire();
                bar.inizia(i-1);
                bar.finisci(i-1);
            }
            catch(Exception e){e.printStackTrace();}

        }



    }
}

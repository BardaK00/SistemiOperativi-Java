package EsercizioEsameBarModSemafori;

import java.util.concurrent.TimeUnit;

public class ThreadCliente extends Thread{
    private Bar bar;

    public ThreadCliente(Bar b){bar = b;}
    public ThreadCliente(BarConcretoSemafori b){
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
                bar.inizia(i); //acquire
                bar.finisci(i); //release
                TimeUnit.SECONDS.sleep(5);
                bar.inizia(i+1);//acquire
                bar.finisci(i+1);//release
            }
            catch(Exception e){e.printStackTrace();}

        }else{
            try{
                bar.inizia(i);
                bar.finisci(i);
                TimeUnit.SECONDS.sleep(5);
                bar.inizia(i-1);
                bar.finisci(i-1);
            }
            catch(Exception e){e.printStackTrace();}

        }



    }
}

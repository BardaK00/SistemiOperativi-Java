package Semaphore.MutuaEsclusioneSemafori;

import java.util.concurrent.Semaphore;

public abstract class AbstractThreadME implements Runnable{
    private String name;
    private static Semaphore sem = new Semaphore(1);
    public AbstractThreadME(String n){name = n;}

    public void run(){
        try{
            sem.acquire();
            System.out.println(name);

        }catch(Exception e) {e.printStackTrace();
        }finally{sem.release();}
    }
}

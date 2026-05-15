package Semaphore.SincronizzazioneSemafori;

import java.util.concurrent.Semaphore;

public class AbstractThreadSync {
    private String name;
    private static Semaphore sem = new Semaphore(0);

    public AbstractThreadSync(String n){name = n;}

    public String getName(){return name;}
    public Semaphore getSem(){return sem;}
}

package ProdottoScalareVettori;

import java.util.Arrays;

public class ProdScalareThread extends Thread{
    private int[] partialVectorA;
    private int[] partialVectorB;

    private long ret = 0;

    public ProdScalareThread(int[]a, int[]b){
        partialVectorA = a;
        partialVectorB = b;

    }
    public void run(){
        for(int i = 0;i<partialVectorA.length;i++){
            ret += ((long) partialVectorA[i] * partialVectorB[i]);
        }
    }

    public long getProdottoScalareParziale() throws InterruptedException{
        this.join();
        System.out.println("terminata esecuzione thread"+ this.getName());
        return ret;
    }
}

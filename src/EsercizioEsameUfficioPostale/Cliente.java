package EsercizioEsameUfficioPostale;

import java.util.Scanner;
import java.util.Random;

public class Cliente extends Thread{
    private UfficioPostale up;
    private String ticket = null;

    public Cliente(UfficioPostale u){up = u;}

    public void run(){
        int ran =new Random().nextInt(1,4);
        switch(ran){
            case 1:ticket = "a";
            break;
            case 2:ticket = "b";
            break;
            case 3:ticket = "c";
            break;
        }
        try{
            up.ritiraTicket(ticket);
            up.attendiSportello(ticket);
        }catch(Exception e){e.printStackTrace();}
    }

}

package EsercizioEsamePizzeria;

public class Pizzaiolo extends Thread{
    private Pizzeria p;

    public Pizzaiolo(Pizzeria p){
        this.p=p;
    }

    public void run(){
        try {
            while(true) {
                p.preparaPizza();
                p.pizzaPronta();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

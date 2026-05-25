package EsercizioEsamePizzeria;

public class Cliente extends Thread{
    private Pizzeria p;

    public Cliente(Pizzeria p){
        this.p=p;
    }

    public void run(){
        try {
            p.mangiaPizza();
            p.pizzaMangiata();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

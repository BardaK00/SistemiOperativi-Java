package EsercizioEsamePizzeria;

public interface Pizzeria {
    void mangiaPizza() throws InterruptedException;
    void pizzaMangiata() throws InterruptedException;
    void preparaPizza() throws InterruptedException;
    void pizzaPronta() throws InterruptedException;
    default void test(Pizzeria pizz){
        Pizzeria p = pizz;
        Pizzaiolo pizzaiolo = new Pizzaiolo(p);
        pizzaiolo.start();
        for(int i = 0;i<100;i++){
            new Cliente(p).start();
        }
    }
}

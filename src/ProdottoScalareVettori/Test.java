package ProdottoScalareVettori;

import java.util.Arrays;

public class Test {
    private static long ret = 0;
    private static int[] vectorA = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    private static int[] vectorB = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    private static int m = 5;
    private static ProdScalareThread[] threadArray = new ProdScalareThread[m];
    private static int contA = 0;
    private static int contB = 0;

    private static int[] getPortionVector(int [] array){
        int[] ret = new int[array.length/m];
        for(int i = 0;i<array.length / m;i++){
            if(array == vectorA) {
                ret[i] = array[contA];
                contA ++;
            }else{
                ret[i] = array[contB];
                contB++;
            }

        }
        return ret;
    }

    public static void instantiateThread(){
        for(int i = 0;i<m;i++){
            threadArray[i] = new ProdScalareThread(getPortionVector(vectorA), getPortionVector(vectorB));
            System.out.println(threadArray[i]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        instantiateThread();


        for(int i = 0;i<m;i++){
            threadArray[i].start();
            System.out.println("startato il thread:"+threadArray[i]);
        }


        for(int i = 0;i<m;i++){
            ret += threadArray[i].getProdottoScalareParziale();
        }
        System.out.println("il prodotto scalare dei due vettori è: " + ret);

    }
}

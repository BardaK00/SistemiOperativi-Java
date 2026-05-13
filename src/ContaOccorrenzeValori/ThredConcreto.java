package ContaOccorrenzeValori;

public class ThredConcreto extends ThreadAbstract implements Runnable{
    private int r;
    private int occorrenzeX = 0;
    private int occorrenzeY = 0;
    public ThredConcreto(int[][] m, int x, int y,int row) {
        super(m, x, y);
        this.r = row;
    }

    @Override
    public void run() {
        for(int i= 0;i<m[r].length;i++){
            if(m[r][i] == super.x)occorrenzeX++;
            if(m[r][i] == super.y)occorrenzeY++;

        };

    }

    public boolean getResult(){
        return occorrenzeX > occorrenzeY;
    }
}

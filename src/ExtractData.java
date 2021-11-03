import java.io.*;
import java.util.*;
public class ExtractData {
    private int[] temp = new int[500];
    int tempcounter = -1;
    private int[] arrmin = new int[500];
    private int[] arrmax = new int[500];
    private double[] arravg = new double[500];
    private int counter;
    private final String AVLS = "AVLSearchTestResults/Search";
    private final String AVLI = "AVLInsertTestResults/Insertion";
    private final String BSTS = "BSTSearchTestResults/Search";
    private final String BSTI = "BSTInsertTestResults/Insertion";
    public String filename;


    public ExtractData(int preFname, int n)
    {
        filename = "";
        switch (n)
        {
            case 1:{
                filename = AVLI;
                break;
            }
            case 2:{
                filename = AVLS;
                break;
            }
            case 3:{
                filename = BSTI;
                break;
            }
            case 4:{
                filename = BSTS;
                break;
            }
        }
        try{
            Scanner sc = new Scanner(new FileReader(filename+"TestFortest"+preFname+".txt"));
            while(sc.hasNext())
            {
                tempcounter++;
                temp[tempcounter]= Integer.parseInt(sc.nextLine());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void reset()
    {
        temp = new int[500];
        tempcounter =-1;
    }
    public void getMax()
    {
        int max = -1;
        for(int i =0;i<=tempcounter; i++ )
        {
            if(max<temp[i])
            {
                max = temp[i];
            }
        }
        arrmax[counter]= max;
    }
    public void getMin()
    {
        int min = 99999;
        for(int i =0;i<=tempcounter; i++ )
        {
            if(min>temp[i])
            {
                min = temp[i];
            }
        }
        arrmin[counter]= min;
    }
    public void getAvg() {

        int sum = 0;
        for (int i = 0; i <= tempcounter; i++) {
            sum += temp[i];
        }

        double avg = (double)sum/(tempcounter+1);
        arravg[counter]= avg;
    }
    public void printToFiles()
    {
        try{
            PrintWriter fw1;
            PrintWriter fw2;
            PrintWriter fw3;
                fw1 = new PrintWriter(new FileWriter(filename+"Averages.txt", true));
                fw2 = new PrintWriter(new FileWriter(filename+"Max.txt", true));
                fw3 = new PrintWriter(new FileWriter(filename+"Min.txt", true));
                fw1.println(arravg[counter]);
                fw2.println(arrmax[counter]);
                fw3.println(arrmin[counter]);
                fw1.close();
                fw2.close();
                fw3.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int k = 1; k<=4; k++){
            for (int i = 1; i <=500; i++) {
                ExtractData ed = new ExtractData(i, k);
                ed.counter = i-1;
                ed.getAvg();
                ed.getMax();
                ed.getMin();
                ed.printToFiles();
                ed.reset();
            }
        }
    }
}

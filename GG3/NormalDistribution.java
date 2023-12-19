package GG3;

import java.math.*;
import java.util.Random;


public class NormalDistribution {
    private int mean,standardDeviation;


    public NormalDistribution(int mean,int standardDeviation){
        this.mean=mean;
        this.standardDeviation=standardDeviation;
    }
    public double getTime(){
        Random random=new Random();
        int time = (int) Math.round(mean + standardDeviation * random.nextGaussian());
        if (time<=0){
            return 1;
        }
        return time;
    }



}

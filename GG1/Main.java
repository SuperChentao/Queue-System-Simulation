package GG1;

import java.util.ArrayList;

public class Main {
    static final double StartTime = 0.0;
    static final double TimeIncrement = 1;
    static final int MaxNumberOfCustomer = 4627;



    public static void main(String[] args) throws Exception {
        NormalDistribution customerArrivalDis=new NormalDistribution(300,18);
        ServiceSystem serviceSystem[]=new ServiceSystem[3];

        double clocks[]=new double[3];
        double totalWaitTime=0;
        double totalSystemTime=0;
        ArrayList<Integer> queueLengthPerSec=new ArrayList<Integer>();
//        Simulation start
        for(int i=0;i<3;i++) {
            double clock = StartTime;
            int numberOfCustomer=0;
            int orderOfCustomer=1;
            double lastArrivalTime=0.0;
            
            serviceSystem[i]=new ServiceSystem();
            while(numberOfCustomer<MaxNumberOfCustomer/3){
                serviceSystem[i].leave();
//                dealing with new customer comming
                if (serviceSystem[i].arrive(lastArrivalTime)){
                    Customer customer=new Customer(orderOfCustomer,clock);
                    numberOfCustomer++;
                    orderOfCustomer++;
                    lastArrivalTime=clock;
                    serviceSystem[i].setInterArrivalTime(customerArrivalDis);
                    if (!serviceSystem[i].isFull()){
                        serviceSystem[i].service(customer);
                    }else{
                        serviceSystem[i].addToQueue(customer);
                    }
                }
//                dealing with the situation that no customer comming
                if ((!serviceSystem[i].isFull())&&(!serviceSystem[i].witingQueueIsEmpty())){
                	serviceSystem[i].serviceTheQueue();
                }
                clock+=TimeIncrement;
                serviceSystem[i].setClock(clock);
            }

            //clean the waiting queue
            while(!serviceSystem[i].witingQueueIsEmpty()){
                serviceSystem[i].leave();
                if(!serviceSystem[i].isFull()){
                    serviceSystem[i].serviceTheQueue();
                }
                clock++;
                serviceSystem[i].setClock(clock);
            }
            //clean the Server
            while(serviceSystem[i].isServing()){
                serviceSystem[i].leave();
                clock++;
                serviceSystem[i].setClock(clock);
            }


            clocks[i]=clock;
            
           

            
//            System.out.println("Waiting time for customer");
            for (Customer customer:serviceSystem[i].getServingRecord()){
                double waitingTime=customer.getQueueEnd()-customer.getQueueStart();
                    totalWaitTime+=waitingTime;
//                    System.out.println("Waiting time for customer"+customer.getOrderOfCustomer()+": "+waitingTime+"sec");
            }
//            System.out.println("");
//            System.out.println("In system time for customer");
            for (Customer customer:serviceSystem[i].getServingRecord()){
                double inSystemTime=customer.getLeaveTime()-customer.getArrivalTime();
                totalSystemTime+=inSystemTime;
//                System.out.println("In system time for customer"+customer.getOrderOfCustomer()+": "+inSystemTime+"sec");
            }
        }



        
        double maxClock=clocks[0]>=clocks[1] ? clocks[0] : clocks[1];
        maxClock=maxClock>=clocks[2] ? maxClock : clocks[2];
        
        System.out.println("\nTotal system time of queue 1 for "+MaxNumberOfCustomer+" customers: "+clocks[0] +" sec");
        System.out.println("Total system time of queue 2 for "+MaxNumberOfCustomer+" customers: "+clocks[1]+" sec");
        System.out.println("Total system time of queue 3 for "+MaxNumberOfCustomer+" customers: "+clocks[2]+" sec");
        
        System.out.println("\nTotal system running time for "+MaxNumberOfCustomer+" customers: "+(int)maxClock+" sec");
        System.out.println("Average system time for each customer: "+(int)(totalSystemTime/MaxNumberOfCustomer) + " sec");
        System.out.println("Total waiting time: "+(int)totalWaitTime + " sec");
        System.out.printf("Average waiting time: %.2f sec \n", totalWaitTime/MaxNumberOfCustomer);
        System.out.printf("Average waiting length: %.3f person\n", totalWaitTime/maxClock);
        System.out.printf("Average queue length: %.3f person\n", totalWaitTime/maxClock+1);
        
        System.out.printf("Good Service ultilization: %.3f",
        		((double)serviceSystem[0].getTotalServiceTime()
        				+(double)serviceSystem[1].getTotalServiceTime()
        				+(double)serviceSystem[2].getTotalServiceTime())/maxClock/3);

    }
}
package GG3;

import java.util.ArrayList;

public class Main {
    static final double StartTime = 0.0;
    static final double TimeIncrement = 1;
    static final int MaxNumberOfCustomer = 4627;



    public static void main(String[] args) throws Exception {
        NormalDistribution customerArrivalDis=new NormalDistribution(100,6);
        ServiceSystem serviceSystem=new ServiceSystem();
        double clock = StartTime;
        int numberOfCustomer=0;
        int orderOfCustomer=1;
        double lastArrivalTime=0.0;
        int totalWaitTime=0;
        int totalSystemTime=0;
//        Simulation start
        while(numberOfCustomer<MaxNumberOfCustomer){
            serviceSystem.leave();
//            dealing with new customer comming
            if (serviceSystem.arrive(lastArrivalTime)){
                Customer customer=new Customer(orderOfCustomer,clock);
                numberOfCustomer++;
                orderOfCustomer++;
                lastArrivalTime=clock;
                serviceSystem.setInterArrivalTime(customerArrivalDis);
                if (!serviceSystem.isFull()){
                    serviceSystem.service(customer);
                }else{
                    serviceSystem.addToQueue(customer);
                }
            }
//            dealing with the situation that no customer comming
            if ((!serviceSystem.isFull())&&(!serviceSystem.waitingQueueIsEmpty())){
                serviceSystem.serviceTheQueue();
            }
            clock+=TimeIncrement;
            serviceSystem.setClock(clock);

        }
//        clean the waiting queue
        while(!serviceSystem.waitingQueueIsEmpty()){
            serviceSystem.leave();
            if(!serviceSystem.isFull()){
                serviceSystem.serviceTheQueue();
            }
            clock++;
            serviceSystem.setClock(clock);
        }
        //        clean the servers
        while (serviceSystem.isServing()){
            serviceSystem.leave();
            clock++;
            serviceSystem.setClock(clock);
        }

        System.out.println("Waiting time for customer");
        for (Customer customer:serviceSystem.getServingRecord()){
            double waitingTime=customer.getQueueEnd()-customer.getQueueStart();
            totalWaitTime+=waitingTime;
            System.out.println("Waiting time for customer"+customer.getOrderOfCustomer()+": "+waitingTime+"sec");
        }
        System.out.println("");
        System.out.println("In system time for customer");
        for (Customer customer:serviceSystem.getServingRecord()){
            double inSystemTime=customer.getLeaveTime()-customer.getArrivalTime();
            totalSystemTime+=inSystemTime;
            System.out.println("In system time for customer"+customer.getOrderOfCustomer()+": "+inSystemTime+"sec");
        }
        
        System.out.println("\nTotal system running time for "+MaxNumberOfCustomer+" customers: "+(int)clock+" sec");
        System.out.printf("Average system time for each customer: %.2f sec\n",(double)totalSystemTime/MaxNumberOfCustomer);
        System.out.println("Total waiting time: "+totalWaitTime + " sec");
        System.out.printf("Average waiting time: %.2f sec\n",(double)totalWaitTime/MaxNumberOfCustomer);
        System.out.printf("Average waiting length: %.3f person\n", totalWaitTime/clock);
        System.out.printf("Average queue length: %.3f person\n", totalWaitTime/clock+1);
        System.out.printf("Service ultilization: %.3f\n",(double)serviceSystem.getTotalServiceTime()/3/clock);

    }
}
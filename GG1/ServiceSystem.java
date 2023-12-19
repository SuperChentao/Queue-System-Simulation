package GG1;

import java.util.ArrayList;
import java.util.Queue;

public class ServiceSystem {
    private Server server1;
    private WaitingQueue<Customer> waitingQueue;
    private boolean serving;
    private double clock;
    private double interArrivalTime;

    private Customer[][] serviceRoom;
    private ArrayList<Customer> servingRecord;

    public ServiceSystem(){
        this.server1=new Server();
        this.waitingQueue=new WaitingQueue<Customer>();
        this.serving=false;
        this.clock=0;
        this.interArrivalTime=0;
        this.serviceRoom=new Customer[3][1];
        this.servingRecord=new ArrayList<Customer>();
    }

    public boolean isFull() {
        return (server1.isServing());
    }

    public void setClock(double clock) {
        this.clock = clock;
    }

    public boolean arrive(double lastArrival){
        if (clock-lastArrival==interArrivalTime){
            return true;
        }
        return false;
    }
    public void addToQueue(Customer customer){
        customer.setQueueStart(clock);
        waitingQueue.enqueue(customer);
    }
    public void setInterArrivalTime(NormalDistribution customerDis){
        this.interArrivalTime= customerDis.getTime();

    }
    public void service(Customer customer) throws Exception {
        if (isFull()){
            throw new Exception("service rome is full");
        }
        while(!isFull()&&!waitingQueue.isEmpty()) {
            Customer firstOfQueue = waitingQueue.dequeue();
            firstOfQueue.setQueueEnd(clock);
            getServing(firstOfQueue);

        }
        if(isFull()){
            customer.setQueueStart(clock);
            this.waitingQueue.enqueue(customer);
        }else {
            getServing(customer);
        }

    }
    public void leave(){
        Server[] servers={server1};
        for(Server server:servers){
            if(server.isServing()){
                if (clock-server.getStartServingTime()==server.getServiceTime()) {
                    Customer leaveCustomer = server.getCustomer();
                    server.setCustomer(null);
                    leaveCustomer.setLeaveTime(clock);
                    this.servingRecord.add(leaveCustomer);
                    server.setServing(false);
                }
            }
        }
    }
    public boolean witingQueueIsEmpty(){
        return this.waitingQueue.isEmpty();
    }
    public void serviceTheQueue() throws Exception{
        if (isFull()){
            throw new Exception("service rome is full");
        }
        Customer firstOfQueue=waitingQueue.dequeue();
        firstOfQueue.setQueueEnd(clock);
        getServing(firstOfQueue);
    }

    public ArrayList<Customer> getServingRecord() {
        return servingRecord;
    }
    public void getServing(Customer customer ){
        if (server1.getCustomer() == null) {
            server1.setCustomer(customer);
            server1.setServing(true);
            server1.setServiceTime();
            this.server1.setStartServingTime(clock);
        } else {
            throw new RuntimeException("all service room is full");
        }
    }
    
    public int getTotalServiceTime() {
    	return server1.getTotalServiceTime();
    }

    public boolean isServing() {
        return server1.isServing();
    }
}

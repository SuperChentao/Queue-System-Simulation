package GG3;

import java.util.ArrayList;
import java.util.Queue;

public class ServiceSystem {
    private Server server1;
    private Server server2;
    private Server server3;
    private WaitingQueue<Customer> waitingQueue;
    private boolean serving;
    private double clock;
    private double interArrivalTime;

    private Customer[][] serviceRoom;
    private ArrayList<Customer> servingRecord;

    public ServiceSystem(){
        this.server1=new Server();
        this.server2=new Server();
        this.server3=new Server();
        this.waitingQueue=new WaitingQueue<Customer>();
        this.serving=false;
        this.clock=0;
        this.interArrivalTime=0;
        this.servingRecord=new ArrayList<Customer>();
    }

    public boolean isFull() {
        return (server1.isServing()&&server2.isServing()&&server3.isServing());
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
        Server[] servers={server1,server2,server3};
        for(Server server:servers){
            if(server.isServing()){
                if (clock -server.getStartServingTime()==server.getServiceTime()) {
                    Customer leaveCustomer = server.getCustomer();
                    server.setCustomer(null);
                    leaveCustomer.setLeaveTime(clock);
                    this.servingRecord.add(leaveCustomer);
                    server.setServing(false);
                }
            }
        }
    }
    public boolean waitingQueueIsEmpty(){
        return this.waitingQueue.isEmpty();
    }
    public void serviceTheQueue() throws Exception{
        if (isFull()){
            throw new Exception("service rome is full");
        }
        while(!isFull()&&!waitingQueueIsEmpty()) {
            Customer firstOfQueue = waitingQueue.dequeue();
            firstOfQueue.setQueueEnd(clock);
            getServing(firstOfQueue);
        }
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
        } else if (server2.getCustomer() == null) {
            server2.setCustomer(customer);
            server2.setServing(true);
            server2.setServiceTime();
            this.server2.setStartServingTime(clock);
        } else if (server3.getCustomer() == null) {
            server3.setCustomer(customer);
            server3.setServing(true);
            server3.setServiceTime();
            this.server3.setStartServingTime(clock);
        } else {
            throw new RuntimeException("all service room is full");
        }
    }
    
    public int getTotalServiceTime() {
    	return this.server1.getTotolServiceTime()+this.server2.getTotolServiceTime()+this.server3.getTotolServiceTime();
    }
    public int getQueueLength(){
        return this.waitingQueue.size();
    }
    public boolean isServing() {
        return server1.isServing()||server2.isServing()||server3.isServing();
    }
}

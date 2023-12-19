package GG1;

public class Server {
    private double serviceTime;
    private double startServingTime;
    private Customer customer;
    private NormalDistribution serviceTimeDis;
    private int totalServiceTime=0;
    private boolean serving;

    public Server(){
        this.serviceTime=0;
        this.startServingTime=0;
        this.customer=null;
        this.serviceTimeDis=new NormalDistribution(271,18);
        this.serving=false;

    }

    public void setServiceTime() {
        this.serviceTime = serviceTimeDis.getTime();
        totalServiceTime+=this.serviceTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getStartServingTime() {
        return startServingTime;
    }

    public void setStartServingTime(double startServingTime) {
        this.startServingTime = startServingTime;
    }
    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isServing() {
        return serving;
    }

    public void setServing(boolean serving) {
        this.serving = serving;
    }
    
    public int getTotalServiceTime() {
    	return this.totalServiceTime;
    }
}

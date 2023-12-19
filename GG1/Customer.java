package GG1;

public class Customer {
    private int orderOfCustomer;
    private double arrivalTime,leaveTime,queueStart,queueEnd;

    public Customer(int orderOfCustomer,double arrivalTime){
        this.orderOfCustomer=orderOfCustomer;
        this.arrivalTime=arrivalTime;
        this.leaveTime=0;
        this.queueStart=0;
        this.queueEnd=0;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getLeaveTime() {
        return leaveTime;
    }

    public int getOrderOfCustomer() {
        return orderOfCustomer;
    }

    public double getQueueEnd() {
        return queueEnd;
    }

    public double getQueueStart() {
        return queueStart;
    }

    public void setLeaveTime(double leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setQueueEnd(double queueEnd) {
        this.queueEnd = queueEnd;
    }

    public void setQueueStart(double queueStart) {
        this.queueStart = queueStart;
    }
}


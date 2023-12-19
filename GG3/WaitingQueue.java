package GG3;

import java.util.ArrayList;

public class WaitingQueue<T> {
    private ArrayList<T> queue;


    public WaitingQueue(){
        this.queue=new ArrayList<T>();

    }
    public void enqueue(T customer){
        this.queue.add(customer);

    }
    public T dequeue(){
        if(queue.isEmpty()){
            throw new NullPointerException("Queue is empty");
        }

        return this.queue.remove(0);

    }
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    public int size(){
        return queue.size();
    }
}

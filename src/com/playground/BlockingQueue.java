package com.playground;

public class BlockingQueue<T> {

    T[]array;
    Object lock = new Object();
    int capacity;
    int size;
    int head=0;
    int tail=0;

    public  BlockingQueue(int cap){
        array=(T[]) new Object[cap];
        this.capacity = cap;
    }

    public void enQueue(T data) throws InterruptedException{
        synchronized(lock){
            while(size==capacity){
                lock.wait();
            }
            //reset the tail
            if(tail==capacity){
                tail=0;
            }

            array[tail]=data;
            tail++;
            size++;
            lock.notifyAll();
        }

    }

    public T deQueue() throws InterruptedException {
        T data = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }

            if (head == capacity) {
                head = 0;
            }

            data = array[head];
            array[head] = null;
            head++;
            size--;
            lock.notifyAll();
            return data;

        }
    }

}

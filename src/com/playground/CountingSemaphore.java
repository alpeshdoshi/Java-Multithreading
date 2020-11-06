package com.playground;

public class CountingSemaphore {
    int usedPermit = 0;
    int maxPermit;

    public CountingSemaphore(int maxPermit){
        this.maxPermit = maxPermit;
    }

    public synchronized void acquire() throws InterruptedException{

        while(usedPermit == maxPermit){
            wait();
        }

        usedPermit++;
        notify();

    }

    public synchronized void release() throws InterruptedException{
        while(usedPermit==0){
            wait();
        }

        usedPermit--;
        notify();
    }
}

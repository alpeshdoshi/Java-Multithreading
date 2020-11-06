package com.playground;

public class ReadWriteLock {
    //this variable will track if any writer is within the block when readers tries to acquire it. Readers should wait if there's a writer writing.
    boolean isWriter = false;
    //count to track how many readers have acquired read lock. writer should wait if there's a reader reading.
    int readers=0;

    //API
    public synchronized void acquireReadLock() throws  InterruptedException{
        //if there's a writer writing then reader should wait
        while(isWriter){
            wait();
        }

        //readers acquired lock so increment the count;
        readers++;



    }

    public synchronized void releaseReadLock(){
        readers--;
        notify();

    }

    public synchronized void acquireWriteLock() throws  InterruptedException{
        //before acquiring write lock check if any other writer thread is writing or if there are any readers available- if not then wait.
        while(isWriter || readers!=0){
            wait();
        }

        isWriter=true;


    }

    public synchronized void releaseWriteLock() throws  InterruptedException{

        isWriter=false;
        notify();

    }
}

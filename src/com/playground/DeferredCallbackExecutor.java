package com.playground;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DeferredCallbackExecutor {
    PriorityQueue<Callback> q = new PriorityQueue<Callback>(new Comparator<Callback>() {
        @Override
        public int compare(Callback o1, Callback o2) {
            return (int) (o1.executeAt - o2.executeAt);
        }
    });

    //lock to guard critical sections
    ReentrantLock lock = new ReentrantLock();

    //condition on which execution thread will wait
    Condition newCallbackArrived=lock.newCondition();

    private long findSleepDuration(){
        long currentTime=System.currentTimeMillis();
        return q.peek().executeAt-currentTime;
    }

    //executor thread will call this to execute callback
    public void start() throws InterruptedException{
        long sleepFor = 0;

        while(true){
            //lock the critical section
            lock.lock();

            //if no item in the queue then wait indefinitely for one to arrive
            while(q.size() == 0){
                newCallbackArrived.await();
            }

            //loop till the callback have been executed
            while(q.size() !=0){

                //find the minimum time execution thread should sleep for before the callback is due
                sleepFor = findSleepDuration();

                //if the callback is due then break from the loop and start executing the callback
                if(sleepFor <=0){
                    break;
                }

                //sleep until the earliest due callback can be executed
                newCallbackArrived.await(sleepFor, TimeUnit.MILLISECONDS);

            }

            //Because we have a min-heap the first element of the queue
            // is necessarily the one which is due.
            Callback callback=q.poll();
            System.out.println("Executed at " +System.currentTimeMillis()/1000 + "required at " +callback.executeAt/1000 + ": message:" +callback.message);

            //unlock critical section
            lock.unlock();
        }

    }

    //consumer thread will call this to register callback
    public void registerCallback(Callback callback){

        lock.lock();
        q.add(callback);
        newCallbackArrived.signal();
        lock.unlock();

    }

/*represents the class which holds callback*/
    static class Callback {
        long executeAt;
        String message;

        public Callback(long executeAfter, String message) {
            this.executeAt = System.currentTimeMillis() + executeAfter*1000;
            this.message = message;
        }
    }


    public static void runTestTenCallbacks() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread service = new Thread(new Runnable() {
            public void run() {
                try {
                    deferredCallbackExecutor.start();
                } catch (InterruptedException ie) {

                }
            }
        });

        service.start();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Callback cb = new Callback(1, "Hello this is " + Thread.currentThread().getName());
                    deferredCallbackExecutor.registerCallback(cb);
                }
            });
            thread.setName("Thread_" + (i + 1));
            thread.start();
            allThreads.add(thread);
            Thread.sleep(1000);
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}

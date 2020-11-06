package com.playground;

public class Main {

    public static void main(String[] args) throws Exception {
        //Blocking Queue
        /*BlockingQueue<Integer> blockingQueue = new BlockingQueue<Integer>(5);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        blockingQueue.enQueue(i);
                        System.out.println("enqueued:" + i);
                    }
                } catch (InterruptedException ex) {

                }


            }

        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("Thread 2 deQueued:" + blockingQueue.deQueue());
                    }
                } catch (InterruptedException ex) {

                }


            }

        });

        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        blockingQueue.enQueue(i);
                        System.out.println("Thread 3 enQueued:" + i);
                    }
                } catch (InterruptedException ex) {

                }


            }

        });

        t1.start();
        Thread.sleep(4000);
        t2.start();
        t2.join();
        t3.start();
        t1.join();
        t3.join();

    }*/

        //com.playground.TokenBucketFilter

        //TokenBucketFilter.runTestMaxTokenIs1();

        //DeferredCallbackExecutor.runTestTenCallbacks();

        //semaphore
        /*final CountingSemaphore cs = new CountingSemaphore(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i< 5; i++){
                        cs.acquire();
                        System.out.println("Ping " +i);
                    }
                }catch (InterruptedException ex){

                }


            }
        });



        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i< 5; i++){
                        cs.release();
                        System.out.println("Pong " +i);
                    }
                }catch (InterruptedException ex){

                }


            }
        });

        t2.start();
        t1.start();
        t1.join();t2.join();*/

        //ReadWriteLock

      /*  final ReadWriteLock readWriteLock = new ReadWriteLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("Attempting to acquire write lock in t1: " +System.currentTimeMillis());
                    readWriteLock.acquireWriteLock();
                    System.out.println("write lock acquired t1: " + +System.currentTimeMillis());
                    //simulates write lock being held indefinitely
                    for(;;){
                        Thread.sleep(500);
                    }
                }catch (InterruptedException ex){

                }


            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("Attempting to acquire write lock in t2: " +System.currentTimeMillis());
                    readWriteLock.acquireWriteLock();
                    System.out.println("write lock acquired t2: " + +System.currentTimeMillis());
                }catch (InterruptedException ex){

                }


            }
        });

        Thread tReader1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    readWriteLock.acquireReadLock();
                    System.out.println("Read lock acquired: " + System.currentTimeMillis());

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Read lock about to release: " + System.currentTimeMillis());
                readWriteLock.releaseReadLock();
                System.out.println("Read lock released: " + System.currentTimeMillis());
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();*/

        UnisexBathroom.runTest();
    }
}

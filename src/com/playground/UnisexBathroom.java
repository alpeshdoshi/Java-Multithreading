package com.playground;

import java.util.concurrent.Semaphore;

public class UnisexBathroom {

    static String WOMEN = "women";
    static String MEN = "Men";
    static String NONE = "none";
    String inUSeBy = NONE;
    int empInBathroom=0;

    Semaphore semaphore = new Semaphore(3);

    public void useBathroom(String name) throws InterruptedException{
        System.out.println(name +" using bathroom. Current employees in bathroom= " +empInBathroom);
        Thread.sleep(10000);
        System.out.println(name +" done using bathroom");
    }

    public void maleUseBathroom(String name) throws InterruptedException{

        synchronized (this){
            while(inUSeBy.equals(WOMEN)){
                // The wait call will give up the monitor associated
                // with the object, giving other threads a chance to
                // acquire it.
                this.wait();
            }
            semaphore.acquire();
            empInBathroom++;
            inUSeBy = MEN;
        }

        useBathroom(name);
        semaphore.release();

        synchronized (this){
            empInBathroom--;
            if(empInBathroom==0) inUSeBy=NONE;
            // Since we might have just updateded the value of
            // inUseBy, we should notifyAll waiting threads
            this.notifyAll();
        }

    }

    public void femaleUseBathroom(String name) throws InterruptedException{

        synchronized (this){
            while(inUSeBy.equals(MEN)){
                this.wait();
            }
            semaphore.acquire();
            empInBathroom++;
            inUSeBy=WOMEN;
        }

        useBathroom(name);
        semaphore.release();

        synchronized (this){
            empInBathroom--;
            if(empInBathroom==0) inUSeBy=NONE;
            this.notifyAll();
        }

    }

    public static void runTest() throws InterruptedException{
        final UnisexBathroom unisexBathroom = new UnisexBathroom();

        Thread female1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    unisexBathroom.femaleUseBathroom("Lisa");
                }catch (InterruptedException ex){

                }
            }
        });
        Thread male1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    unisexBathroom.maleUseBathroom("John");
                }catch (InterruptedException ex){

                }
            }
        });
        Thread male2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    unisexBathroom.maleUseBathroom("Anil");
                }catch (InterruptedException ex){

                }
            }
        });
        Thread male3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    unisexBathroom.maleUseBathroom("Kundan");
                }catch (InterruptedException ex){

                }
            }
        });

        Thread male4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    unisexBathroom.maleUseBathroom("sonu");
                }catch (InterruptedException ex){

                }
            }
        });

        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();
    }
}

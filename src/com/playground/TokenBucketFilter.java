package com.playground;

import java.util.HashSet;
import java.util.Set;

//rate limiting using token bucket filter. this algorithm is used for shaping network traffic flow. It is also known as leaky bucket algorithm
//implementing rate limiting using a naive token bucket filter algorithm
public class TokenBucketFilter {
    private int MAX_TOKEN;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleToken = 0;

    public TokenBucketFilter(int maxSize){
        this.MAX_TOKEN=maxSize;
    }

    synchronized void getToken() throws InterruptedException{
        // Divide by a 1000 to get granularity at the second level.
        possibleToken+= (System.currentTimeMillis()-lastRequestTime)/1000;

        if(possibleToken>MAX_TOKEN){
            possibleToken=MAX_TOKEN;
        }


        if(possibleToken==0){
            Thread.sleep(1000);
        }else{
            possibleToken--;
        }
        lastRequestTime=System.currentTimeMillis();

        System.out.println("Granted " +Thread.currentThread().getName()+ "token at " + System.currentTimeMillis()/1000);
    }

    public static void runTestMaxTokenIs1() throws InterruptedException{
        Set<Thread> allThreads = new HashSet<>();
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(1);
        for(int i=0; i<10; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        tokenBucketFilter.getToken();
                    }catch (InterruptedException ie) {
                        System.out.println("We have a problem");
                    }

                }
            });
            thread.setName("Thread_" + (i+1));
            allThreads.add(thread);

        }

        for(Thread t: allThreads){
            t.start();
        }

        for(Thread t: allThreads){
            t.join();
        }
    }
}

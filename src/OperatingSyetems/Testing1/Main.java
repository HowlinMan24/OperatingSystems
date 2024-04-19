package OperatingSyetems.Testing1;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // List of threads
        var threads = new HashSet<SequenceIncrementThread>();
        // Integer wrapper
        IntegerWrapper integerWrapper = new IntegerWrapper();
        IntegerWrapper integerWrapper2 = new IntegerWrapper();
        IntegerWrapper integerWrapper3 = new IntegerWrapper();

        /*
        If we put multiple thread to increment the same element we need to sync the code so it will not top at ex.100 for both thread
        when in theory it should end on 200
         */
        threads.add(new SequenceIncrementThread(integerWrapper, 0));
        threads.add(new SequenceIncrementThread(integerWrapper, 0));
        threads.add(new SequenceIncrementThread(integerWrapper2, 0));


        // Fill up the threads
//        for (int i = 0; i < 1000; i++) {
//            threads.add(new SequenceIncrementThread(integerWrapper, 0));
//        }
        // Start all the threads ion the set
        for (Thread t : threads) {
            t.start();
        }
        // "Sync" all the thread --> this is for execution
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Print the exection
        System.out.println(integerWrapper.getCounter());
        System.out.println(integerWrapper2.getCounter());
        System.out.println(integerWrapper3.getCounter());


    }

}

class IntegerWrapper {

    private static int counter = 0;
    /*
    If we use any of this we need to have static in-front because if we do nopt each one thread
    will create its on Lock/Semaphore
     */
    private static Lock lock = new ReentrantLock();
    /*
    Semaphore with value 1 work like a mutex(door) either open or closed
     */
    private static Semaphore semaphore = new Semaphore(1);

    // When you put static infront it means the variable becomes "Atomic"
    private void forceContentSwitch() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Public need to be synced because any thread can access it
//    public void increment() { // We put this if we have a static element and we put in on the whole class
//        lock.lock();
//        synchronized (IntegerWrapper.class) {
//            int newCounter = counter;
//            newCounter++;
//            forceContentSwitch();
//            counter = newCounter;
//        }
//        lock.unlock();;
//    }

    /*
    We use lock at the start point and the end point where
    the critical domain ends or at the very end. We can either use Lock or Semaphore
     */
    public void increment() throws InterruptedException {
//        semaphore.acquire();
        lock.lock();
        int newCounter = counter;
        newCounter++;
        forceContentSwitch();
        counter = newCounter;
        lock.unlock();
//        semaphore.release();
    }

    // Private method don't need any synchronization because it for its thread only
    private void privateIncrement() {
        int newCounter = counter;
        newCounter++;
        forceContentSwitch();
        counter = newCounter;
    }

    // No need to sync the get method because we don't make any changes to the variables
    public int getCounter() {
        return counter;
    }

    //Any write process(Smth that makes a change to the variables) needs to be synced
    public void setCounter(int counter1) {
        counter = counter1;
    }

}

class SequenceIncrementThread extends Thread {

    private IntegerWrapper integerWrapper;
    private int localIntField = 0;
    private int sharedIntField;

    SequenceIncrementThread(IntegerWrapper integerWrapper, int sharedIntField) {
        this.integerWrapper = integerWrapper;
        this.sharedIntField = sharedIntField;
    }

    private void privateFieldIncrement() {
        this.localIntField++;
    }

    public void sharedFieldIncrement() {
        this.sharedIntField++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                integerWrapper.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
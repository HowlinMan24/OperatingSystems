package OperatingSyetems.ret;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int[] array;
    private int size;
    private int totalOccurences;
    private int maxPerThread;
    private int threadsWorking;


    // TODO here implement locks and semaphores

    Lock lock = new ReentrantLock();
    Semaphore semaphore = new Semaphore(0);


    public SharedArray(int[] array, int size) {
        this.array = array;
        this.size = size;
    }

    // TODO Set how many threads will work
    public void setThreadsWorking(int threadsWorking) {
        // This locks the resource for the current thread so another thread won't come and
        // overtake, also known as race condition
        this.threadsWorking = threadsWorking;
    }

    // TODO sync the thread --> this gets called from the main to add to the
    //  total found occurrences of the target
    public void threadDone(int threadFound) {
        // This gets called because we are working on a shared element among all the threads
        // and we have this lock so a race condition won't happen
        lock.lock();
        this.totalOccurences += threadFound;  //inkrementira
//        if (this.maxPerThread < totalOccurences) {
//            this.maxPerThread = totalOccurences;
//        }
        lock.unlock();
        semaphore.release();
    }

    public int getTotalOccurences() throws InterruptedException {
        semaphore.acquire(threadsWorking); // This stops each thread till it takes all of them so it can send them all at once
        semaphore.release(threadsWorking);
        return totalOccurences;
    }

    public int checkMax() throws InterruptedException {
        semaphore.acquire(threadsWorking);
        semaphore.release(threadsWorking);
        return maxPerThread;
    }

    public int getMax() {
        return maxPerThread;
    }

    public int[] getArray() {
        return array;
    }
}


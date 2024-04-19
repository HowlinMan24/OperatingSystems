package OperatingSyetems.ret;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random rand = new Random();

        int n = 10000;
        int[] ar = new int[n];
        for (int i = 0; i < n; i++) {
            ar[i] = rand.nextInt(150);
        }

        SharedArray sa = new SharedArray(ar, n);      //inicijalizacija na spodelen resurs
        int threads = 5;
        sa.setThreadsWorking(threads);

        int searchFor = 137; // TODO find the target

        // TODO make an array or list from threads and divide n into chunks and give each thread a chunk

        ArrayList<ParallelSearchThread> threadsList = new ArrayList<>();
        int chunks = n / threads; // This makes chunks for each thread to run on

        // TODO initialize the threads

        for (int i = 0; i < threads; i++) {
            threadsList.add(new ParallelSearchThread(sa, i * chunks, (i * chunks) + chunks, searchFor, i + 1));
        }

        for (ParallelSearchThread th : threadsList) {
            th.start(); // --> start the threads
        }


        // TODO print out the total occurrences

        try {
            System.out.println("Total found: " + sa.getTotalOccurences());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // TODO "sync" the strings before printing

        for (ParallelSearchThread th : threadsList) {
            th.join();
        }

    }
}



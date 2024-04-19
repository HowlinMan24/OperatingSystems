package OperatingSyetems.ret;

public class ParallelSearchThread extends Thread {

    SharedArray arr;
    int start;
    int end;
    int searching;

    int numberThread;

    //inicijalizacija
    public ParallelSearchThread(SharedArray arr, int start, int end, int searching, int numberThread) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searching = searching;
        this.numberThread = numberThread;
    }

    @Override
    public void run() {
        super.run();

        // TODO here get the shred array amongst all the threads

        int[] array = arr.getArray(); // --> This gets the array from the SharedArray class
        // TODO loop through the array to find if any number maches the target number

        int localCounter = 0; // This thing does not need sync because is local for each thread
        for (int i = start; i < end; i++) {
            if (array[i] == searching) {
                localCounter++;
            }
        }
        // TODO finish the thread

        System.out.println("For the tread in the chunk " + numberThread + " the number of occurrences of the target number is --> " + localCounter);
        arr.threadDone(localCounter);
        // This gives the number of occurrences to the array in the chunk of which it works in

        // TODO check if we found our max number

        if (arr.getMax() == localCounter) {
            System.out.println("I found the local max --> Thread number: " + numberThread);
        }


    }
}


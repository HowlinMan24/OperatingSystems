package OperatingSyetems.Solo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {

    private int sumMatrix;
    private int n;
    private int m;
    private int[][] matrix;
    private int maxElementPerThread;
    private int maxSum;
    private int indexMaxThread;

    Lock lock = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    public SharedArray(int[][] matrix, int n, int m) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
        this.maxSum = 0;
        this.sumMatrix = 10;
        this.indexMaxThread = -1;
    }

    public void threadDone(int sumRow, int localMax, int maxthread) {
        lock.lock();
        this.sumMatrix += sumRow;
        if (this.maxElementPerThread < localMax) {
            this.maxElementPerThread = localMax;
        }
        if (maxSum < sumRow) {
            this.maxSum = sumRow;
            this.indexMaxThread = maxthread;
        }
        lock.unlock();
    }

    void setMaxElementPerThread(int maxElementPerThread) {
        this.maxElementPerThread = maxElementPerThread;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getMaxSum() {
        return sumMatrix;
    }

    public int getMaxElement() {
        return maxElementPerThread;
    }

    public int getIndex() {
        return indexMaxThread + 1;
    }

    public int getSumMax() {
        return maxSum;
    }

}

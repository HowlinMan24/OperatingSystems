package OperatingSyetems.SoloZtko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMatrix {
    private String[][] matrixLetters;
    private int rowsLength;
    private int columnLength;

    private int totalLetters;
    List<String> listLetterL = new ArrayList<>();
    List<String> listLetterS = new ArrayList<>();

    private int indexMostLetterL;
    private int indexMostLetterS;
    private int numThreads;
    private static Lock lock = new ReentrantLock();
    private static Semaphore semaphore = new Semaphore(0);

    public SharedMatrix(String[][] matrixLetters, int rowsLength, int columnLength) {
        this.matrixLetters = matrixLetters;
        this.rowsLength = rowsLength;
        this.columnLength = columnLength;
        this.totalLetters = 0;
        this.indexMostLetterL = -1;
        this.indexMostLetterS = -1;
        this.numThreads = rowsLength;
    }

    public void theadDone(List<String> letterL, List<String> letterS, int numberThread) {
        lock.lock();
        this.totalLetters += (letterL.size() + letterS.size());
        if (letterL.size() > listLetterL.size()) {
            listLetterL = letterL;
            indexMostLetterL = numberThread;
        }
        if (letterS.size() > listLetterS.size()) {
            listLetterS = letterS;
            indexMostLetterS = numberThread;
        }
        lock.unlock();
        semaphore.release();
    }


    public String[][] getMatrixLetters() {
        return matrixLetters;
    }

    public int getRowsLength() {
        return rowsLength;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public int getIndexMostLetterL() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return indexMostLetterL;
    }

    public int getIndexMostLetterS() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return indexMostLetterS;
    }

    public List<String> getListLetterL() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return listLetterL;
    }

    public List<String> getListLetterS() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return listLetterS;
    }

    public int getListLSize() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return listLetterL.size();
    }

    public int getListSSize() throws InterruptedException {
        semaphore.acquire(numThreads);
        semaphore.release(numThreads);
        return listLetterS.size();
    }

}

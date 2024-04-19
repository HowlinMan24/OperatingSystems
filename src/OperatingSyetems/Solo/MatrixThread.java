package OperatingSyetems.Solo;

public class MatrixThread extends Thread {

    int numThread;
    SharedArray matrix;
    int rowLength;

    MatrixThread(SharedArray matrix, int rowLength, int numberThread) {
        this.matrix = matrix;
        this.rowLength = rowLength;
        this.numThread = numberThread;
    }

    @Override
    public void run() {
        super.run();

        int[][] matrixShared = matrix.getMatrix();

        int localsum = 0;
        int localMax = -1;
        for (int i = 0; i < rowLength; i++) {
            localsum += matrixShared[numThread][i];
            if (matrixShared[numThread][i] > localMax) {
                localMax = matrixShared[numThread][i];
            }
        }

        System.out.println("This thread is number " + (numThread + 1) + " and has a sum for its row --> " + localsum + " and it has a local max of --> " + localMax);
        matrix.threadDone(localsum, localMax,this.numThread);


    }
}

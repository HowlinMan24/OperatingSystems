package OperatingSyetems.Solo;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int n = 5;
        int m = 6;
        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = random.nextInt(150);
            }
        }

        SharedArray matrixShared = new SharedArray(matrix, n, m);

        MatrixThread[] threads = new MatrixThread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new MatrixThread(matrixShared, m, i);

        }

        for (Thread th : threads) {
            th.start();
        }

        for (Thread th : threads) {
            th.join();
        }

        System.out.println("The sum of all rows combined --> " + matrixShared.getMaxSum());
        System.out.println("The max element from the whole matrix --> " + matrixShared.getMaxElement());
        System.out.println("The sum of the thread with the max sum is " + matrixShared.getIndex() + " with the sum " + matrixShared.getSumMax());

    }

}

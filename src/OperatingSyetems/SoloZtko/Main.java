package OperatingSyetems.SoloZtko;

import java.util.Random;

/**
 * Make a tread system that foes throufht the amtrix and cocnatenates two strings
 * one containg only letters S or L and at the end print them out with thier length and find out
 * in which tread there is the most of S or L
 * Avoid the / chars
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        int m = 50;
        String[][] matrixLetters = new String[n][m];
        String[] letterSelection = new String[]{"S", "L", "V"};
        Random random = new Random();


        // Initialize the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixLetters[i][j] = letterSelection[random.nextInt(0, 3)];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrixLetters[i][j] + " ");
            }
            System.out.println();
        }

        SharedMatrix sharedMatrix = new SharedMatrix(matrixLetters, n, m);
        StringThread[] threads = new StringThread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new StringThread(sharedMatrix, m, i);
        }

        for (Thread th : threads)
            th.start();

        for (Thread th : threads)
            th.join();

        System.out.println("The result of most L or S in the string is " + sharedMatrix.getListLSize() + " - " + sharedMatrix.getListSSize());
        System.out.println("The list with the most letters S is in row --> " + (sharedMatrix.getIndexMostLetterS() + 1));
        System.out.println("The list with the most letters L is in row --> " + (sharedMatrix.getIndexMostLetterL() + 1));


    }

}

package OperatingSyetems.SoloZtko;

import java.util.ArrayList;
import java.util.List;

public class StringThread extends Thread {


    private SharedMatrix matrixLetters;
    private int rowEnd;
    private int numberThread;

    public StringThread(SharedMatrix matrixLetters, int rowEnd, int numberThreadInMatrix) {
        this.matrixLetters = matrixLetters;
        this.rowEnd = rowEnd;
        this.numberThread = numberThreadInMatrix;
    }

    @Override
    public void run() {
        super.run();

        // Get matrix
        String[][] matrix = matrixLetters.getMatrixLetters();

        List<String> letterS = new ArrayList<>();
        List<String> letterL = new ArrayList<>();

//        int localCounterS = 0;
//        int localCounterL = 0;

        for (int i = 0; i < rowEnd; i++) {
            if (matrix[numberThread][i].equals("V"))
                continue;
            if (matrix[numberThread][i].equals("L")) {
                letterL.add(matrix[numberThread][i]);
//                localCounterL++;
            } else {
                letterS.add(matrix[numberThread][i]);
//                localCounterS++;
            }
        }

        matrixLetters.theadDone(letterL, letterS, numberThread);

        System.out.println("The list with index " + (numberThread + 1) + " with letters S is: " + letterS + " with size --> " + letterS.size());
        System.out.println("The list with " + (numberThread + 1) + " with letters L is: " + letterL + " with size --> " + letterL.size());


    }
}

package com.semsari.fatemeh.firstapp.faribank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CostumScanner {

    private static CostumScanner instance = new CostumScanner();
    private Scanner scanner;

    private CostumScanner() {
        scanner = new Scanner(System.in);
    }

    public static CostumScanner getInstance() {
        return instance;
    }

    public Integer nextInt() {
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                isCorrect = true;
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                isCorrect = false;
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                System.out.println("please enter an integer:");
                scanner.nextLine();
            }
        }
        return -1;
    }

    public Double nextDouble() {
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                isCorrect = true;
                double number = scanner.nextDouble();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                isCorrect = false;
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                System.out.println("please enter a double:");
                scanner.nextLine();
            }
        }
        return (double) -1;
    }

    public String nextLine() {
        return scanner.nextLine();
    }
}

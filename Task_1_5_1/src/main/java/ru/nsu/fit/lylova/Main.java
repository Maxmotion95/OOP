package ru.nsu.fit.lylova;

import java.util.Scanner;

/**
 * Class Main with function main which is the implementation of the calculator.
 */
public class Main {
    /**
     * Implementation of calculator. <br>
     * List of calculator's functions:
     * <ul>
     *     <li>addition</li>
     *     <li>cosine</li>
     *     <li>division</li>
     *     <li>logarithm</li>
     *     <li>multiplication</li>
     *     <li>pow</li>
     *     <li>sine</li>
     *     <li>square root</li>
     *     <li>subtraction</li>
     * </ul>
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();

        Calculator calculator = new Calculator();
        double result;
        try {
            result = calculator.calculateExpression(expression);
        } catch (Exception e) {
            System.out.println("Something goes wrong: " + e.getMessage());
            return;
        }
        System.out.println(result);
    }
}
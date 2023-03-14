package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.arrayChecker.CheckerOfArrayForNonSimpleNumbers;
import ru.nsu.fit.lylova.arrayChecker.ConsistentChecker;
import ru.nsu.fit.lylova.arrayChecker.ParallelCheckerWithParallelStream;
import ru.nsu.fit.lylova.arrayChecker.ParallelCheckerWithThreads;

import java.util.Scanner;

/**
 * Main class with method main.
 */
public class Main {
    /**
     * Method main, that reads from standard input count of numbers and array of numbers.
     * After that, the result of checking this array
     * for the content of a non-simple number is output using
     * {@code ConsistentChecker}, {@code ParallelCheckerWithParallelStream},
     * {@code ParallelCheckerWithThreads}.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; ++i) {
            numbers[i] = scanner.nextInt();
        }

        CheckerOfArrayForNonSimpleNumbers checker = new ConsistentChecker();
        System.out.println(checker.hasNonPrimeNumber(numbers));
        checker = new ParallelCheckerWithParallelStream();
        System.out.println(checker.hasNonPrimeNumber(numbers));
        checker = new ParallelCheckerWithThreads();
        System.out.println(checker.hasNonPrimeNumber(numbers));
    }
}
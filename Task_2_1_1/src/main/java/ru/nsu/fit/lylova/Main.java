package ru.nsu.fit.lylova;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; ++i) {
            numbers[i] = scanner.nextInt();
        }

        CheckerOfArrayForContentNonSimpleNumbers checker = new ConsistentChecker();
        System.out.println(checker.hasNonPrimeNumber(numbers));
        checker = new ParallelCheckerWithParallelStream();
        System.out.println(checker.hasNonPrimeNumber(numbers));
        checker = new ParallelCheckerWithThreads(10);
        System.out.println(checker.hasNonPrimeNumber(numbers));
    }
}
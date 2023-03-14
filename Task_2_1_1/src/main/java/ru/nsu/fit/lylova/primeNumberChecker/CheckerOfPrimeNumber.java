package ru.nsu.fit.lylova.primeNumberChecker;

/**
 * A class with a function for checking a number for simplicity.
 */
public class CheckerOfPrimeNumber {
    /**
     * Checks is number {@code number} is prime.
     *
     * @param number number
     * @return {@code true} if {@code number} is prime, otherwise {@code false}
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        boolean result = true;
        for (int i = 2; i * i <= number && result; ++i) {
            result = number % i != 0;
        }
        return result;
    }
}

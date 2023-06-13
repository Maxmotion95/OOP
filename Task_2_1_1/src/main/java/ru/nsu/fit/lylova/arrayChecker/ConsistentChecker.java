package ru.nsu.fit.lylova.arrayChecker;

import static ru.nsu.fit.lylova.primeNumberChecker.CheckerOfPrimeNumber.isPrime;

/**
 * Class that implements interface {@code CheckerOfArrayForNonSimpleNumbers}.
 * Sequentially checks each number for simplicity.
 */
public class ConsistentChecker implements CheckerOfArrayForNonSimpleNumbers {
    /**
     * Constructs checker.
     */
    public ConsistentChecker() {
    }

    /**
     * Check array for content of non-prime number.
     *
     * @param numbers array of numbers
     * @return {@code true} if array has non-prime number, otherwise {@code false}
     */
    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}

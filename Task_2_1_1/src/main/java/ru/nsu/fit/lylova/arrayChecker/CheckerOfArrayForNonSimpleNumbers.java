package ru.nsu.fit.lylova.arrayChecker;

/**
 * The interface of the array verifier for the content of a non-prime number.
 */
public interface CheckerOfArrayForNonSimpleNumbers {
    /**
     * Checks array for content of non-prime number.
     *
     * @param numbers array of numbers
     * @return {@code true} if array has non-prime number, otherwise {@code false}
     */
    boolean hasNonPrimeNumber(int[] numbers);
}

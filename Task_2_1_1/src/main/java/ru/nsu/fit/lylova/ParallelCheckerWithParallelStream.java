package ru.nsu.fit.lylova;

import java.util.Arrays;

/**
 * Class that implements interface {@code CheckerOfArrayForNonSimpleNumbers}.
 * ParallelStream is used to parallelize array validation.
 */
public class ParallelCheckerWithParallelStream implements CheckerOfArrayForNonSimpleNumbers {
    /**
     * Constructs checker.
     */
    public ParallelCheckerWithParallelStream() {
    }

    /**
     * Checks array for content of non-prime number.
     *
     * @param numbers array of numbers
     * @return {@code true} if array has non-prime number, otherwise {@code false}
     */
    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        return !Arrays.stream(numbers).parallel().unordered().allMatch(CheckerOfPrimeNumber::isPrime);
    }
}

package ru.nsu.fit.lylova;

import java.util.Arrays;

public class ParallelCheckerWithParallelStream implements CheckerOfArrayForNonSimpleNumbers {
    public ParallelCheckerWithParallelStream(){
    }
    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        return !Arrays.stream(numbers).parallel().unordered().allMatch(CheckerOfPrimeNumber::isPrime);
    }
}

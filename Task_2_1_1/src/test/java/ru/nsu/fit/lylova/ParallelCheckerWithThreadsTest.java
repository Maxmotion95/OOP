package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParallelCheckerWithThreadsTest {
    @Test
    void test() {
        CheckerOfArrayForContentNonSimpleNumbers checker = new ConsistentChecker();
        int[] numbers = new int[1000];
        Arrays.fill(numbers, 1000000007);
//        numbers[1] = 4;
        System.out.println(checker.hasNonPrimeNumber(numbers));
    }
}
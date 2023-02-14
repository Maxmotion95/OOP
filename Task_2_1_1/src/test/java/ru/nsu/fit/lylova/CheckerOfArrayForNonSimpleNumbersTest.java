package ru.nsu.fit.lylova;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckerOfArrayForNonSimpleNumbersTest {
    private static Stream<CheckerOfArrayForContentNonSimpleNumbers> provideCheckerForTest() {
        return Stream.of(
                new ParallelCheckerWithThreads(5),
                new ConsistentChecker(),
                new ParallelCheckerWithParallelStream()
        );
    }

    @ParameterizedTest
    @MethodSource("provideCheckerForTest")
    void test(CheckerOfArrayForContentNonSimpleNumbers checker) {
        int[] numbers = new int[1000];
        Arrays.fill(numbers, 1000000007);
        assertFalse(checker.hasNonPrimeNumber(numbers));

        numbers[324] = 4;
        numbers[22] = 738;
        assertTrue(checker.hasNonPrimeNumber(numbers));

        numbers = new int[]{3, 2, 3};
        assertFalse(checker.hasNonPrimeNumber(numbers));
        numbers = new int[]{123, 34, 29};
        assertTrue(checker.hasNonPrimeNumber(numbers));
        numbers = new int[]{3, 5, 4, 13, 17, 19, 9, 33};
        assertTrue(checker.hasNonPrimeNumber(numbers));
    }
}
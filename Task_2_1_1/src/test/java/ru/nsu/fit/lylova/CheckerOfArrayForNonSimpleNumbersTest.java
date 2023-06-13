package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.lylova.arrayChecker.CheckerOfArrayForNonSimpleNumbers;
import ru.nsu.fit.lylova.arrayChecker.ConsistentChecker;
import ru.nsu.fit.lylova.arrayChecker.ParallelCheckerWithParallelStream;
import ru.nsu.fit.lylova.arrayChecker.ParallelCheckerWithThreads;

class CheckerOfArrayForNonSimpleNumbersTest {
    private static Stream<CheckerOfArrayForNonSimpleNumbers> provideCheckerForTest() {
        return Stream.of(
                new ParallelCheckerWithThreads(5),
                new ConsistentChecker(),
                new ParallelCheckerWithParallelStream()
        );
    }

    @ParameterizedTest
    @MethodSource("provideCheckerForTest")
    void test(CheckerOfArrayForNonSimpleNumbers checker) {
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
package ru.nsu.fit.lylova;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {

    static Stream<int[]> intArrayProvider() {
        return Stream.of(
                new int[]{1, 2, 3, 4, 5},
                new int[]{8, 4, 2},
                new int[]{8, 8, 8, 8},
                new int[]{2, 3, 9, 0},
                new int[]{-10, 100, 25, 37},
                new int[]{637, 43627, 3547, 534, 33432, 143, 243, 25421});
    }

    @ParameterizedTest
    @MethodSource("intArrayProvider")
    void smallArraysTest(int[] array) {
        int[] heapSorted = array.clone();
        int[] sorted = array.clone();
        Heapsort.heapsort(heapSorted);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; ++i) {
            assertEquals(sorted[i], heapSorted[i],
                    "Initial array was: " + Arrays.toString(array));
        }
    }

    @RepeatedTest(10)
    void largeArraysTest() {
        Random random = new Random();
        int n = 1000 + random.nextInt(1000);
        int[] init_a = new int[n];
        for (int i = 0; i < n; ++i) {
            init_a[i] = random.nextInt();
        }
        int[] sorted = init_a.clone();
        int[] heapSorted = init_a.clone();
        Heapsort.heapsort(heapSorted);
        Arrays.sort(sorted);
        for (int i = 0; i < n; ++i) {
            assertEquals(sorted[i], heapSorted[i],
                    "Array: " + Arrays.toString(init_a) +
                            ", Expected answer: " + Arrays.toString(sorted) +
                            ", Heapsort result: " + Arrays.toString(heapSorted) + "\n");
        }
    }
}

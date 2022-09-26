package ru.nsu.fit.lylova;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {

    @ParameterizedTest
    @ValueSource(arrays = {{1, 2, 3, 4, 5},
            {8, 4, 2},
            {8, 8, 8, 8},
            {2, 3, 9, 0},
            {-10, 100, 25, 37},
            {637, 43627, 3547, 534, 33432, 143, 243, 25421}})
    void small_arrays_test(int[] array) {
        int[] heapsorted = array.clone();
        int[] sorted = array.clone();
        Heapsort.heapsort(heapsorted);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; ++i) {
            assertEquals(sorted[i], heapsorted[i],
                    "Initial array was: " + Arrays.toString(array));
        }
    }

    @RepeatedTest(10)
    void large_arrays_test() {
        Random random = new Random();
        int n = 1000 + random.nextInt(1000);
        int[] init_a = new int[n];
        for (int i = 0; i < n; ++i) {
            init_a[i] = random.nextInt();
        }
        int[] sorted = init_a.clone();
        int[] heapsorted = init_a.clone();
        Heapsort.heapsort(heapsorted);
        Arrays.sort(sorted);
        for (int i = 0; i < n; ++i) {
            assertEquals(sorted[i], heapsorted[i],
                    "Array: " + Arrays.toString(init_a) +
                            ", Expected answer: " + Arrays.toString(sorted) +
                            ", Heapsort result: " + Arrays.toString(heapsorted) + "\n");
        }
    }
}

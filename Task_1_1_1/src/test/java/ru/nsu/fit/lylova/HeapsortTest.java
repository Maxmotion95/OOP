package ru.nsu.fit.lylova;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {

    @Test
    void small_arrays_test() {
        int[][] init_arrays = {{1, 2, 3, 4, 5},
                          {8, 4, 2},
                          {8, 8, 8, 8},
                          {2, 3, 9, 0},
                          {-10, 100, 25, 37},
                          {637, 43627, 3547, 534, 33432, 143, 243, 25421}};

        int[][] heapsorted = init_arrays.clone();
        int[][] arrays = init_arrays.clone();
        for (int i = 0; i < arrays.length; ++i){
            Heapsort.heapsort(heapsorted[i]);
            Arrays.sort(arrays[i]);
        }
        for (int i = 0; i < arrays.length; ++i){
            for (int j = 0; j < arrays[i].length; ++j){
                assertEquals(arrays[i][j], heapsorted[i][j],
                        "Array: " + Arrays.toString(init_arrays[i]) +
                                ", Expected answer: " + Arrays.toString(arrays[i]) +
                                ", Heapsort result: " + Arrays.toString(heapsorted[i]) + "\n");
            }
        }
    }

    @RepeatedTest(10)
    void large_arrays_test() {
        Random random = new Random();
        int n = 1000 + random.nextInt(1000);
        int[] init_a = new int[n];
        for (int i = 0; i < n; ++i){
            init_a[i] = random.nextInt();
        }
        int[] sorted = init_a.clone();
        int[] heapsorted = init_a.clone();
        Heapsort.heapsort(heapsorted);
        Arrays.sort(sorted);
        for (int i = 0; i < n; ++i){
            assertEquals(sorted[i], heapsorted[i],
                    "Array: " + Arrays.toString(init_a) +
                            ", Expected answer: " + Arrays.toString(sorted) +
                            ", Heapsort result: " + Arrays.toString(heapsorted) + "\n");
        }
    }
}

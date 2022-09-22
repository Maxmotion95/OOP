package ru.nsu.fit.lylova;

/**
 * class with function heapsort
 */
public class Heapsort {
    /**
     * Returns an array of numbers sorted in ascending order.
     * Time complexity is O(n*log(n)) and memory complexity is O(n) where n - array length.
     *
     * @param arr an array of integers
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            int v = i + 1;
            while (v != 1) {
                if (arr[v / 2 - 1] < arr[v - 1]) {
                    int x = arr[v / 2 - 1];
                    arr[v / 2 - 1] = arr[v - 1];
                    arr[v - 1] = x;
                }
                v /= 2;
            }
        }
        for (int i = n - 1; i > 0; --i) {
            int x = arr[i];
            arr[i] = arr[0];
            arr[0] = x;
            int v = 1;
            while (v * 2 < i + 1) {
                int id_of_biggest = v * 2 + 1 < i + 1 && arr[v * 2 + 1 - 1] > arr[v * 2 - 1] ? v * 2 + 1 : v * 2;
                if (arr[v - 1] < arr[id_of_biggest - 1]) {
                    x = arr[v - 1];
                    arr[v - 1] = arr[id_of_biggest - 1];
                    arr[id_of_biggest - 1] = x;
                    v = id_of_biggest;
                } else {
                    break;
                }
            }
        }
    }
}

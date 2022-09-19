package ru.nsu.fit.lylova;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class with function main
 */
public class Main {
    /**
     * Asks you to enter an array of integers via the command line.
     * Then outputs this array in ascending order.
     * The heapsort algorithm is used to sort the array.
     *
     * @param args arguments of command line (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter array of integers: ");
        String s = sc.nextLine();
        String[] as = s.split(" ");
        int[] a = new int[as.length];
        for (int i = 0; i < as.length; ++i) {
            a[i] = Integer.parseInt(as[i]);
        }
        System.out.println("Sorted array: " + Arrays.toString(Heapsort.heapsort(a)));
    }
}

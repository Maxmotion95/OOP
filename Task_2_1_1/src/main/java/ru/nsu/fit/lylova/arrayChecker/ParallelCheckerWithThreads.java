package ru.nsu.fit.lylova.arrayChecker;

import static ru.nsu.fit.lylova.primeNumberChecker.CheckerOfPrimeNumber.isPrime;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that implements interface {@code CheckerOfArrayForNonSimpleNumbers}.
 * Threads are used to parallelize array validation.
 */
public class ParallelCheckerWithThreads implements CheckerOfArrayForNonSimpleNumbers {
    private final AtomicBoolean result = new AtomicBoolean(false);
    private int threadsCount = 8;

    /**
     * Constructs checker which will use 8 threads.
     */
    public ParallelCheckerWithThreads() {
    }

    /**
     * Constructs checker which will use {@code threadsCount} threads.
     *
     * @param threadsCount count of threads
     */
    public ParallelCheckerWithThreads(int threadsCount) {
        this.threadsCount = threadsCount > 0 ? threadsCount : 1;
    }

    /**
     * Checks array for content of non-prime number.
     *
     * @param numbers array of numbers
     * @return {@code true} if array has non-prime number, otherwise {@code false}
     */
    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        result.set(false);
        int count = numbers.length;

        Thread[] threads = new Thread[threadsCount];
        int used = 0;
        for (int i = 0; i < threadsCount; ++i) {
            int l = count / threadsCount + (i < count % threadsCount ? 1 : 0);
            threads[i] = new CheckerOfSubArray(numbers, used, used + l);
            used += l;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        while (true) {
            boolean isAnyThreadAlive = Arrays.stream(threads).allMatch(Thread::isAlive);
            if (!isAnyThreadAlive) {
                break;
            }
            if (result.get()) {
                for (var thread : threads) {
                    thread.interrupt();
                }
                break;
            }
        }
        return result.get();
    }

    private class CheckerOfSubArray extends Thread {
        final int[] numbers;
        final int left;
        final int right;

        CheckerOfSubArray(int[] numbers, int left, int right) {
            this.numbers = numbers;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            for (int i = left; i < right && !this.isInterrupted(); ++i) {
                if (!isPrime(numbers[i])) {
                    result.set(true);
                    break;
                }
            }
        }
    }
}

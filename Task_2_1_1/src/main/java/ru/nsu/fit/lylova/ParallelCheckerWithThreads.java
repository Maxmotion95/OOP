package ru.nsu.fit.lylova;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.nsu.fit.lylova.CheckerOfPrimeNumber.isPrime;

public class ParallelCheckerWithThreads implements CheckerOfArrayForNonSimpleNumbers {
    private final AtomicBoolean result = new AtomicBoolean(false);
    int threadsCount = 8;

    ParallelCheckerWithThreads() {
    }

    ParallelCheckerWithThreads(int threadsCount) {
        this.threadsCount = threadsCount > 0 ? threadsCount : 1;
    }

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
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

    class CheckerOfSubArray extends Thread {
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

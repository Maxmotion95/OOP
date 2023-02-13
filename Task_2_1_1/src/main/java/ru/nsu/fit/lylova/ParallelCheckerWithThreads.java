package ru.nsu.fit.lylova;

import static ru.nsu.fit.lylova.CheckerOfPrimeNumber.isPrime;

public class ParallelCheckerWithThreads implements CheckerOfArrayForContentNonSimpleNumbers{
    boolean result = false;
    int threadsCount;

    ParallelCheckerWithThreads(int threadsCount) {
        this.threadsCount = threadsCount > 0 ? threadsCount : 1;
    }

    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        result = false;
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
        return result;
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
            for (int i = left; i < right && !result; ++i) {
                if (!isPrime(numbers[i])) {
                    result = true;
                    break;
                }
            }
        }
    }
}

package ru.nsu.fit.lylova;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

public class BenchmarkThreadsCount {
    @State(Scope.Benchmark)
    public static class BenchmarkState {

        @Param({"1", "2", "4", "6", "8", "12", "16", "24", "32", "40", "50"})
        public int threadsCount;
        public int numberOfPrimeNumbers = 50000;
        public int[] bigPrimeNumbers;


        @Setup(Level.Trial)
        public void setUp() {
            bigPrimeNumbers = new int[numberOfPrimeNumbers];
            for (int i = 0; i < numberOfPrimeNumbers; i++) {
                if (i < numberOfPrimeNumbers / 2) {
                    bigPrimeNumbers[i] = 1000000007;
                } else {
                    bigPrimeNumbers[i] = 998244353;
                }
            }
        }
    }



    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public boolean ParallelThread2(BenchmarkThreadsCount.BenchmarkState state) {
        CheckerOfArrayForNonSimpleNumbers finder = new ParallelCheckerWithThreads(state.threadsCount);
        return finder.hasNonPrimeNumber(state.bigPrimeNumbers);
    }
}

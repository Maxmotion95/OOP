package ru.nsu.fit.lylova;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

public class BenchmarkDifferentTypes {
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public boolean ParallelThread(BenchmarkState state) {
        CheckerOfArrayForNonSimpleNumbers finder = new ParallelCheckerWithThreads(32);
        return finder.hasNonPrimeNumber(state.bigPrimeNumbers);
    }

    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public boolean ParallelStream(BenchmarkState state) {
        CheckerOfArrayForNonSimpleNumbers finder = new ParallelCheckerWithParallelStream();
        return finder.hasNonPrimeNumber(state.bigPrimeNumbers);
    }

    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @org.openjdk.jmh.annotations.Benchmark
    public boolean Linear(BenchmarkState state) {
        CheckerOfArrayForNonSimpleNumbers finder = new ConsistentChecker();
        return finder.hasNonPrimeNumber(state.bigPrimeNumbers);
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"10", "30", "50", "100", "500", "1000", "5000", "10000", "50000"})//
        public int numberOfPrimeNumbers;
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
}

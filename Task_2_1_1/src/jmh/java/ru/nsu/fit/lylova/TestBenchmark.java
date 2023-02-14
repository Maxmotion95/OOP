package ru.nsu.fit.lylova;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

public class TestBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"2", "10", "25", "50", "100", "250", "500", "1000", "5000", "10000", "50000"})
        public int numberOfPrimeNumbers;
        public int maxInt = 10000000;
//        public EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(maxInt);
//        public List<Integer> primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
        public int[] bigPrimeNumbers;

        @Setup(Level.Trial)
        public void setUp() {
            bigPrimeNumbers = new int[numberOfPrimeNumbers];
            for (int i = 0; i < numberOfPrimeNumbers; i++) {
                bigPrimeNumbers[i] = 1000000007;
            }
//            eratosthenesSieve.setSeed();
//            primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
        }
    }

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.Throughput)
    public static class DifferentTypesBigNumbers {
        /**
         * large numbers
         */
        @Benchmark
        public void ParallelThread(BenchmarkState state) {
            CheckerOfArrayForContentNonSimpleNumbers finder = new ParallelCheckerWithThreads(4);
            var res = finder.hasNonPrimeNumber(state.bigPrimeNumbers);
            assert (!res);
        }

//        @Benchmark
//
//        public void ParallelThreadWithAtomic(Blackhole blackhole, BenchmarkState state) {
//            NonPrimesFinder finder = new ParallelThreadWithAtomic();
//            var res = finder.hasNoPrime(state.bigPrimeNumbers);
//            assert (!res);
//        }
//
//        @Benchmark
//        public void Linear(Blackhole blackhole, BenchmarkState state) {
//            NonPrimesFinder finder = new LinearNonPrimeFinder();
//            var res = finder.hasNoPrime(state.bigPrimeNumbers);
//            assert (!res);
//        }
//
//        //
//        @Benchmark
//        public void ParallelStream(Blackhole blackhole, BenchmarkState state) {
//            NonPrimesFinder finder = new ParallelStreamNonPrimeNumberFinder();
//            var res = finder.hasNoPrime(state.bigPrimeNumbers);
//            assert (!res);
//        }
//
//        @Benchmark
//        public void ParallelHybrid(Blackhole blackhole, BenchmarkState state) {
//            NonPrimesFinder finder = new Hybrid();
//            var res = finder.hasNoPrime(state.bigPrimeNumbers);
//            assert (!res);
//        }
    }
//
//    @Benchmark
//    public void kek() {
//
//    }
//    public static void main(String[] args) throws Exception {
//        org.openjdk.jmh.Main.main(args);
//    }


//    @Benchmark
//    public void measureName
//    @State(Scope.Benchmark)
//    public static class BenchmarkState {
//        @Param({"2", "5", "10", "25", "50", "100", "250", "500", "1000", "2500", "5000"})
//        public int numberOfPrimeNumbers;
//        @Param({"1", "2", "4", "6", "8", "12"})
//        public int threadsAmount;
//        public int maxInt = 40000000;
//        public List<Integer> primeNumbers = new ArrayList<>(numberOfPrimeNumbers, 1000);
//    }
//
//    @State(Scope.Benchmark)
//    @BenchmarkMode(Mode.Throughput)
//    public static class DifferentThreads {
//        @Benchmark
//        public void TestParallelThreadNonPrime(Blackhole blackhole, BenchmarkState state) {
//            NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder(state.threadsAmount);
//            var res = finder.hasNoPrime(state.primeNumbers);
//            assert (!res);
//        }
//    }

}

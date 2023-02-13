package ru.nsu.fit.lylova;

public class CheckerOfPrimeNumber {
    static boolean isPrime(int a) {
        if (a <= 1) {
            return false;
        }
        boolean result = true;
        for (int i = 2; i * i <= a && result; ++i) {
            result = a % i != 0;
        }
        return result;
    }
}

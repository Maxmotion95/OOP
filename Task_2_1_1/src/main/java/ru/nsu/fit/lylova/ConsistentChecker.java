package ru.nsu.fit.lylova;

import static ru.nsu.fit.lylova.CheckerOfPrimeNumber.isPrime;

public class ConsistentChecker implements CheckerOfArrayForContentNonSimpleNumbers{
    public ConsistentChecker(){
    }
    @Override
    public boolean hasNonPrimeNumber(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}

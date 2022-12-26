package ru.nsu.fit.lylova;

import java.util.Stack;

public interface CalculatorFunction {
    void produce(Stack<Double> calculatorStack) throws Exception;

    boolean parse(String token);
}

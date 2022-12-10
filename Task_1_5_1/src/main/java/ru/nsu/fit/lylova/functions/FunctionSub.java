package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

public class FunctionSub extends CalculatorFunction {
    public FunctionSub() {
    }

    @Override
    public void produce(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2)
            throw new Exception("Not enough elements in calculatorStack");
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        calculatorStack.push(a - b);
    }
}

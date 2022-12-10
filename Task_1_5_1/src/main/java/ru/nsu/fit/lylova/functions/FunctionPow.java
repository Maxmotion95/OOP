package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

import static java.lang.Double.NaN;
import static java.lang.Math.pow;

public class FunctionPow extends CalculatorFunction {
    public FunctionPow() {
    }

    @Override
    public void produce(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        double result = pow(a, b);
        if (Double.isNaN(result)) {
            throw new Exception("Cannot pow");
        }
        calculatorStack.push(result);
    }
}

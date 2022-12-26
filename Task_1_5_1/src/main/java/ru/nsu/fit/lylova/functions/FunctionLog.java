package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

import static java.lang.Math.log;

public class FunctionLog implements CalculatorFunction {
    public FunctionLog() {
    }

    @Override
    public void produce(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 1) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double result = log(a);
        if (Double.isNaN(result)) {
            throw new Exception("Cannot calculate log");
        }
        calculatorStack.push(result);
    }

    @Override
    public boolean parse(String token) {
        return token.equals("log");
    }
}

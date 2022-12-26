package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

import static java.lang.Math.log;

/**
 * Function logarithm which implements interface {@code CalculatorFunction}.
 */
public class FunctionLog implements CalculatorFunction {
    /**
     * Constructs function logarithm.
     */
    public FunctionLog() {
    }

    /**
     * Uses logarithm on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if logarithm cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
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

    /**
     * Checks that specified token is token of logarithm function.
     * Token of logarithm function is {@code "log"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "log"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("log");
    }
}

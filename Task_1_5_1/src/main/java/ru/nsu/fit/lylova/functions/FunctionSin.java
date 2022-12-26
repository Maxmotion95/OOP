package ru.nsu.fit.lylova.functions;

import static java.lang.Math.sin;

import java.util.Stack;
import ru.nsu.fit.lylova.CalculatorFunction;

/**
 * Function sine which implements interface {@code CalculatorFunction}.
 */
public class FunctionSin implements CalculatorFunction {
    /**
     * Constructs function sine.
     */
    public FunctionSin() {
    }

    /**
     * Uses sine function on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if sine cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 1) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double result = sin(a);
        if (Double.isNaN(result)) {
            throw new Exception("Cannot calculate sin");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of sine function.
     * Token of sine function is {@code "sin"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "sin"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("sin");
    }
}

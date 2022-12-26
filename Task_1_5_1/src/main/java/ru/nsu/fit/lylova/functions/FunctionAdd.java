package ru.nsu.fit.lylova.functions;

import java.util.Stack;
import ru.nsu.fit.lylova.CalculatorFunction;

/**
 * Function addition which implements interface {@code CalculatorFunction}.
 */
public class FunctionAdd implements CalculatorFunction {
    /**
     * Constructs function addition.
     */
    public FunctionAdd() {
    }

    /**
     * Uses addition on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if addition cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        double result = a + b;
        if (Double.isNaN(result)) {
            throw new Exception("Cannot add");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of addition function.
     * Token of addition function is {@code "+"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "+"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("+");
    }
}

package ru.nsu.fit.lylova.functions;

import java.util.Stack;
import ru.nsu.fit.lylova.CalculatorFunction;

/**
 * Function multiplication which implements interface {@code CalculatorFunction}.
 */
public class FunctionMultiply implements CalculatorFunction {
    /**
     * Constructs function multiplication.
     */
    public FunctionMultiply() {
    }

    /**
     * Uses multiplication on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if multiplication cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        double result = a * b;
        if (Double.isNaN(result)) {
            throw new Exception("Cannot multiply");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of multiplication function.
     * Token of multiplication function is {@code "*"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "*"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("*");
    }
}

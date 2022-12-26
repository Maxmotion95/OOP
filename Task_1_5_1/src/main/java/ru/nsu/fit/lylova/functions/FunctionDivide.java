package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

/**
 * Function division which implements interface {@code CalculatorFunction}.
 */
public class FunctionDivide implements CalculatorFunction {
    /**
     * Constructs function division.
     */
    public FunctionDivide() {
    }

    /**
     * Uses division on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if division cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        double result = a / b;
        if (Double.isNaN(result)) {
            throw new Exception("Cannot divide");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of division function.
     * Token of division function is {@code "/"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "/"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("/");
    }
}

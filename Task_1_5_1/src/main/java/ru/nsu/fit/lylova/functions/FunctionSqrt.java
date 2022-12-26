package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

import static java.lang.Math.sqrt;

/**
 * Function square root which implements interface {@code CalculatorFunction}.
 */
public class FunctionSqrt implements CalculatorFunction {
    /**
     * Constructs function square root.
     */
    public FunctionSqrt() {
    }

    /**
     * Uses square root function on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if square root cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 1) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double result = sqrt(a);
        if (Double.isNaN(result)) {
            throw new Exception("Cannot calculate sqrt");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of square root function.
     * Token of square root function is {@code "sqrt"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "sqrt"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("sqrt");
    }
}

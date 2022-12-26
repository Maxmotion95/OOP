package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

import static java.lang.Math.cos;

/**
 * Function cosine which implements interface {@code CalculatorFunction}.
 */
public class FunctionCos implements CalculatorFunction {
    /**
     * Constructs function cosine.
     */
    public FunctionCos() {
    }

    /**
     * Uses cosine function on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if cosine cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 1) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double result = cos(a);
        if (Double.isNaN(result)) {
            throw new Exception("Cannot calculate cos");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of cosine function.
     * Token of cosine function is {@code "cos"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "cos"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("cos");
    }
}

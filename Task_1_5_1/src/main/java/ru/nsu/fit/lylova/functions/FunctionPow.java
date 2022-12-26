package ru.nsu.fit.lylova.functions;

import static java.lang.Math.pow;

import java.util.Stack;
import ru.nsu.fit.lylova.CalculatorFunction;

/**
 * Function pow which implements interface {@code CalculatorFunction}.
 */
public class FunctionPow implements CalculatorFunction {
    /**
     * Constructs function pow.
     */
    public FunctionPow() {
    }

    /**
     * Uses pow function on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if pow cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
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

    /**
     * Checks that specified token is token of pow function.
     * Token of pow function is {@code "pow"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "pow"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("pow");
    }
}

package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

/**
 * Function subtraction which implements interface {@code CalculatorFunction}.
 */
public class FunctionSub implements CalculatorFunction {
    /**
     * Constructs function subtraction.
     */
    public FunctionSub() {
    }

    /**
     * Uses subtraction on calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if subtraction cannot be used
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        if (calculatorStack.size() < 2) {
            throw new Exception("Not enough elements in calculatorStack");
        }
        double a = calculatorStack.pop();
        double b = calculatorStack.pop();
        double result = a - b;
        if (Double.isNaN(result)) {
            throw new Exception("Cannot subtract");
        }
        calculatorStack.push(result);
    }

    /**
     * Checks that specified token is token of subtraction function.
     * Token of subtraction function is {@code "-"}.
     *
     * @param token expression token
     * @return {@code true} if token is {@code "-"}.
     */
    @Override
    public boolean parse(String token) {
        return token.equals("-");
    }
}

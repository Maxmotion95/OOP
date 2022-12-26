package ru.nsu.fit.lylova;

import java.util.Stack;

/**
 * Class calculator function.
 */
public interface CalculatorFunction {
    /**
     * Uses this function to specified calculator stack.
     *
     * @param calculatorStack calculator stack
     * @throws Exception if function cannot be produced
     */
    void use(Stack<Double> calculatorStack) throws Exception;

    /**
     * Checks that specified token matches this function.
     *
     * @param token expression token
     * @return {@code true} if that token matches this function
     */
    boolean parse(String token);
}

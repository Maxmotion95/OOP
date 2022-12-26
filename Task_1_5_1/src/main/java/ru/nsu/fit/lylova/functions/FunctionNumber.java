package ru.nsu.fit.lylova.functions;

import java.util.Stack;
import ru.nsu.fit.lylova.CalculatorFunction;

/**
 * Function number which implements interface {@code CalculatorFunction}.
 */
public class FunctionNumber implements CalculatorFunction {
    private double number;

    /**
     * Constructs function with specified number.
     *
     * @param number number
     */
    public FunctionNumber(double number) {
        this.number = number;
    }

    /**
     * Adds number that was saved in this function.
     *
     * @param calculatorStack calculator stack
     * @throws Exception never
     */
    @Override
    public void use(Stack<Double> calculatorStack) throws Exception {
        calculatorStack.add(number);
    }

    /**
     * Parse token to double number and saves this number in this function.
     *
     * @param token expression token
     * @return {@code true} if token is double number
     */
    @Override
    public boolean parse(String token) {
        try {
            number = Double.parseDouble(token);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

package ru.nsu.fit.lylova.functions;

import ru.nsu.fit.lylova.CalculatorFunction;

import java.util.Stack;

public class FunctionNumber implements CalculatorFunction {
    double number;

    public FunctionNumber(double number) {
        this.number = number;
    }

    @Override
    public void produce(Stack<Double> calculatorStack) throws Exception {
        calculatorStack.add(number);
    }

    @Override
    public boolean parse(String token) {
        try
        {
            number = Double.parseDouble(token);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}

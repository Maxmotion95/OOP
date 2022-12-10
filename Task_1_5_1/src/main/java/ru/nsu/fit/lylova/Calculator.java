package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    static public double calculateExpression(String expression) throws Exception {
        Scanner scanner = new Scanner(expression);
        Stack<Double> numbersStack = new Stack<>();
        ArrayList<String> expressionTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            expressionTokens.add(scanner.next());
        }

        for (int i = expressionTokens.size() - 1; i >= 0; --i) {
            String token = expressionTokens.get(i);
            Scanner scToken = new Scanner(token);

            if (scToken.hasNextDouble()) {
                numbersStack.push(scToken.nextDouble());
                continue;
            }

            FunctionType operationType = null;
            switch (token) {
                case "log":
                    operationType = FunctionType.LOG;
                    break;
                case "pow":
                    operationType = FunctionType.POW;
                    break;
                case "sqrt":
                    operationType = FunctionType.SQRT;
                    break;
                case "sin":
                    operationType = FunctionType.SIN;
                    break;
                case "cos":
                    operationType = FunctionType.COS;
                    break;
                case "+":
                    operationType = FunctionType.ADD;
                    break;
                case "-":
                    operationType = FunctionType.SUB;
                    break;
                case "*":
                    operationType = FunctionType.MULTIPLY;
                    break;
                case "/":
                    operationType = FunctionType.DIVIDE;
                    break;
                default:
                    throw new IllegalArgumentException("bad expression :(");
            }

            CalculatorFunction function = CalculatorFunctionFactory.createFunction(operationType);
            function.produce(numbersStack);
        }
        if (numbersStack.size() != 1) {
            throw new IllegalArgumentException("bad expression :(");
        }
        return numbersStack.get(0);
    }
}

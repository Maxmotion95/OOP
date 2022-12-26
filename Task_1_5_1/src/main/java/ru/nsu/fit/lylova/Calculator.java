package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.functions.FunctionAdd;
import ru.nsu.fit.lylova.functions.FunctionCos;
import ru.nsu.fit.lylova.functions.FunctionDivide;
import ru.nsu.fit.lylova.functions.FunctionLog;
import ru.nsu.fit.lylova.functions.FunctionMultiply;
import ru.nsu.fit.lylova.functions.FunctionNumber;
import ru.nsu.fit.lylova.functions.FunctionPow;
import ru.nsu.fit.lylova.functions.FunctionSin;
import ru.nsu.fit.lylova.functions.FunctionSqrt;
import ru.nsu.fit.lylova.functions.FunctionSub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    private final List<CalculatorFunction> functions = new ArrayList<>();

    Calculator() {
        functions.add(new FunctionAdd());
        functions.add(new FunctionCos());
        functions.add(new FunctionDivide());
        functions.add(new FunctionLog());
        functions.add(new FunctionMultiply());
        functions.add(new FunctionNumber(0));
        functions.add(new FunctionPow());
        functions.add(new FunctionSin());
        functions.add(new FunctionSqrt());
        functions.add(new FunctionSub());
    }

    public double calculateExpression(String expression) throws Exception {
        Scanner scanner = new Scanner(expression);
        Stack<Double> numbersStack = new Stack<>();
        ArrayList<String> expressionTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            expressionTokens.add(scanner.next());
        }

        Collections.reverse(expressionTokens);

        for (String token : expressionTokens) {
            for (var function : functions) {
                if (function.parse(token)) {
                    function.produce(numbersStack);
                    break;
                }
            }
        }

        if (numbersStack.size() != 1) {
            throw new IllegalArgumentException("bad expression :(");
        }
        return numbersStack.peek();
    }

    public void addFunction(CalculatorFunction function) {
        functions.add(function);
    }

    public void addAllFunctions(Collection<CalculatorFunction> collectionFunctions) {
        functions.addAll(collectionFunctions);
    }
}

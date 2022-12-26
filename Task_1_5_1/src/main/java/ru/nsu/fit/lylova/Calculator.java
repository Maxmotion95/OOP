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

/**
 * Class calculator.
 */
public class Calculator {
    private final List<CalculatorFunction> functions = new ArrayList<>();

    /**
     * Constructs calculator with functions:
     * <ul>
     *     <li>addition</li>
     *     <li>cosine</li>
     *     <li>division</li>
     *     <li>logarithm</li>
     *     <li>multiplication</li>
     *     <li>pow</li>
     *     <li>sine</li>
     *     <li>square root</li>
     *     <li>subtraction</li>
     * </ul>
     */
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

    /**
     * Constructs calculator with specified functions.
     *
     * @param functions collection of functions
     */
    Calculator(Collection<CalculatorFunction> functions) {
        this.functions.addAll(functions);
    }

    /**
     * Calculates specified expression.
     *
     * @param expression expression to be calculated
     * @return result of calculation
     * @throws Exception if expression is incorrect
     */
    public double calculateExpression(String expression) throws Exception {
        Scanner scanner = new Scanner(expression);
        Stack<Double> numbersStack = new Stack<>();
        ArrayList<String> expressionTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            expressionTokens.add(scanner.next());
        }

        Collections.reverse(expressionTokens);

        for (String token : expressionTokens) {
            boolean tokenUsed = false;
            for (var function : functions) {
                if (function.parse(token)) {
                    function.use(numbersStack);
                    tokenUsed = true;
                    break;
                }
            }
            if (!tokenUsed) {
                throw new Exception("No such function :(");
            }
        }

        if (numbersStack.size() != 1) {
            throw new IllegalArgumentException("bad expression :(");
        }
        return numbersStack.peek();
    }

    /**
     * Adds specified function to this calculator.
     *
     * @param function function to be added
     */
    public void addFunction(CalculatorFunction function) {
        functions.add(function);
    }

    /**
     * Adds all functions from collection {@code collectionFunctions} to this calculator.
     *
     * @param collectionFunctions functions collection
     */
    public void addAllFunctions(Collection<CalculatorFunction> collectionFunctions) {
        functions.addAll(collectionFunctions);
    }
}

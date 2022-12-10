package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.functions.*;

public class CalculatorFunctionFactory {
    static public CalculatorFunction createFunction(FunctionType type) {
        CalculatorFunction function = null;

        switch (type) {
            case ADD:
                function = new FunctionAdd();
                break;
            case SUB:
                function = new FunctionSub();
                break;
            case MULTIPLY:
                function = new FunctionMultiply();
                break;
            case SIN:
                function = new FunctionSin();
                break;
            case COS:
                function = new FunctionCos();
                break;
            case DIVIDE:
                function = new FunctionDivide();
                break;
            case LOG:
                function = new FunctionLog();
                break;
            case SQRT:
                function = new FunctionSqrt();
                break;
            case POW:
                function = new FunctionPow();
                break;
            default:
                throw new IllegalArgumentException("Wrong function type:" + type);
        }
        return function;
    }
}

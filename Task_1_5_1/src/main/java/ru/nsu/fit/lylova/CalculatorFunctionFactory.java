package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.functions.FunctionAdd;
import ru.nsu.fit.lylova.functions.FunctionMultiply;
import ru.nsu.fit.lylova.functions.FunctionSub;

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
            default:
                throw new IllegalArgumentException("Wrong function type:" + type);
        }
        return function;
    }
}

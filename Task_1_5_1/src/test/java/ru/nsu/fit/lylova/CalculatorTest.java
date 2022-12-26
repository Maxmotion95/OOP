package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    @Test
    void test() throws Exception {
        Calculator calculator = new Calculator();

        assertEquals(123, calculator.calculateExpression("123"));
        assertEquals(-345, calculator.calculateExpression("-345"));
        assertEquals(120, calculator.calculateExpression("+ 111 9"));
        assertEquals(300, calculator.calculateExpression("- 300 0"));
        assertEquals(30, calculator.calculateExpression("/ 300 10"));
        assertEquals(0, calculator.calculateExpression("* 782368 0"));
        assertEquals(0, calculator.calculateExpression("sin 0"));
        assertEquals(1, calculator.calculateExpression("cos 0"));
        assertEquals(3, calculator.calculateExpression("sqrt 9"));
        assertEquals(0, calculator.calculateExpression("log 1"));
        assertEquals(1, calculator.calculateExpression("pow -1 2"));
    }

    @Test
    void testAddFunction() throws Exception {
        Calculator calculator = new Calculator();
        calculator.addFunction(new FunctionAddConstant(7));

        assertEquals(7, calculator.calculateExpression("add_magic_constant 0"));

        List<CalculatorFunction> functions = new ArrayList<>();
        functions.add(new FunctionAddConstant(1007));
        functions.add(new FunctionMultiplyByZero());
        functions.add(new FunctionConstant37());

        calculator = new Calculator(functions);

        String expr = "add_magic_constant add_magic_constant multiply_by_zero const37";
        assertEquals(2014, calculator.calculateExpression(expr));

        calculator = new Calculator(new ArrayList<>());
        calculator.addAllFunctions(functions);
        assertEquals(2014, calculator.calculateExpression(expr));
    }

    @Test
    void testExceptions() {
        Calculator calculator = new Calculator();
        Exception exception;

        String[][] expressionAndMessage = new String[][] {
                {"", "bad expression :("},
                {"1 2", "bad expression :("},
                {"kek 1", "No such function :("},
                {"+ 1", "Not enough elements in calculatorStack"},
                {"cos", "Not enough elements in calculatorStack"},
                {"/ 1", "Not enough elements in calculatorStack"},
                {"log", "Not enough elements in calculatorStack"},
                {"* 1", "Not enough elements in calculatorStack"},
                {"pow 1", "Not enough elements in calculatorStack"},
                {"sin", "Not enough elements in calculatorStack"},
                {"sqrt", "Not enough elements in calculatorStack"},
                {"- 1", "Not enough elements in calculatorStack"},
                {"/ 0 0", "Cannot divide"},
                {"sqrt -1", "Cannot calculate sqrt"},
                {"pow -1 0.5", "Cannot pow"},
                {"sin / 1 0", "Cannot calculate sin"},
                {"- / 1 0 / 1 0", "Cannot subtract"},
                {"+ / 1 0 / -1 0", "Cannot add"},
                {"cos / 1 0", "Cannot calculate cos"},
                {"log -1", "Cannot calculate log"},
                {"* / 1 0 0", "Cannot multiply"}
        };

        for (int i = 0; i < expressionAndMessage.length; ++i) {
            int finalI = i;
            exception = assertThrows(
                    Exception.class,
                    () -> calculator.calculateExpression(expressionAndMessage[finalI][0])
            );
            assertEquals(expressionAndMessage[finalI][1], exception.getMessage());
        }
    }

    private static class FunctionAddConstant implements CalculatorFunction {
        double number;

        FunctionAddConstant(double number) {
            this.number = number;
        }

        @Override
        public void use(Stack<Double> calculatorStack) throws Exception {
            if (calculatorStack.size() < 1) {
                throw new Exception("Not enough elements in calculatorStack");
            }
            double a = calculatorStack.pop();
            calculatorStack.push(a + number);
        }

        @Override
        public boolean parse(String token) {
            return token.equals("add_magic_constant");
        }
    }

    private static class FunctionMultiplyByZero implements CalculatorFunction {

        FunctionMultiplyByZero() {
        }

        @Override
        public void use(Stack<Double> calculatorStack) throws Exception {
            if (calculatorStack.size() < 1) {
                throw new Exception("Not enough elements in calculatorStack");
            }
            double a = calculatorStack.pop();
            calculatorStack.push(a * 0);
        }

        @Override
        public boolean parse(String token) {
            return token.equals("multiply_by_zero");
        }
    }

    private static class FunctionConstant37 implements CalculatorFunction {

        FunctionConstant37() {
        }

        @Override
        public void use(Stack<Double> calculatorStack) throws Exception {
            calculatorStack.push(37.0);
        }

        @Override
        public boolean parse(String token) {
            return token.equals("const37");
        }
    }
}
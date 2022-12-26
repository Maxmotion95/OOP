package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

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

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression(""));
        assertEquals("bad expression :(", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("1 2"));
        assertEquals("bad expression :(", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("kek 1"));
        assertEquals("No such function :(", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("+ 1"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("cos"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("/ 1"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("log"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("* 1"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("pow 1"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("sin"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("sqrt"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("- 1"));
        assertEquals("Not enough elements in calculatorStack", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("/ 0 0"));
        assertEquals("Cannot divide", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("sqrt -1"));
        assertEquals("Cannot calculate sqrt", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("pow -1 0.5"));
        assertEquals("Cannot pow", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("sin / 1 0"));
        assertEquals("Cannot calculate sin", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("- / 1 0 / 1 0"));
        assertEquals("Cannot subtract", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("+ / 1 0 / -1 0"));
        assertEquals("Cannot add", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("cos / 1 0"));
        assertEquals("Cannot calculate cos", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("log -1"));
        assertEquals("Cannot calculate log", exception.getMessage());

        exception = assertThrows(Exception.class, () -> calculator.calculateExpression("* / 1 0 0"));
        assertEquals("Cannot multiply", exception.getMessage());
    }

    private static class FunctionAddConstant implements CalculatorFunction {
        double number;

        FunctionAddConstant(double number) {
            this.number = number;
        }

        @Override
        public void produce(Stack<Double> calculatorStack) throws Exception {
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
        public void produce(Stack<Double> calculatorStack) throws Exception {
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
        public void produce(Stack<Double> calculatorStack) throws Exception {
            calculatorStack.push(37.0);
        }

        @Override
        public boolean parse(String token) {
            return token.equals("const37");
        }
    }
}
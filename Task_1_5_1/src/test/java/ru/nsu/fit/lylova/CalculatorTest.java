package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

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
}
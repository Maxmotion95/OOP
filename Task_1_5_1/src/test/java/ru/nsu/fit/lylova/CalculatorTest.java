package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void test() throws Exception {
        assertEquals(123, Calculator.calculateExpression("123"));
        assertEquals(-345, Calculator.calculateExpression("-345"));
        assertEquals(120, Calculator.calculateExpression("+ 111 9"));
        assertEquals(300, Calculator.calculateExpression("- 300 0"));
        assertEquals(30, Calculator.calculateExpression("/ 300 10"));
        assertEquals(0, Calculator.calculateExpression("* 782368 0"));
        assertEquals(0, Calculator.calculateExpression("sin 0"));
        assertEquals(1, Calculator.calculateExpression("cos 0"));
        assertEquals(3, Calculator.calculateExpression("sqrt 9"));
        assertEquals(0, Calculator.calculateExpression("log 1"));
        assertEquals(1, Calculator.calculateExpression("pow -1 2"));
    }
}
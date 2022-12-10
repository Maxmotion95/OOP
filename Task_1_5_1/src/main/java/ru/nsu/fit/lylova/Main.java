package ru.nsu.fit.lylova;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();
        System.out.println(Calculator.calculateExpression(expression));
    }
}
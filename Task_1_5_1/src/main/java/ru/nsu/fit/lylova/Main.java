package ru.nsu.fit.lylova;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();
        double result;
        try {
            result = Calculator.calculateExpression(expression);
        } catch (Exception e) {
            System.out.println("Something goes wrong: " + e.getMessage());
            return;
        }
        System.out.println(result);
    }
}
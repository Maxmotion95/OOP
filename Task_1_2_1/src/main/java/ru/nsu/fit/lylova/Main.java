package ru.nsu.fit.lylova;

public class Main {

    public static void main(String[] args) throws Exception {
        Stack<Integer> a = new Stack<>();
        a.push(2);
        a.push(7);
        Stack<Integer> x = new Stack<>();
        x.push(4);
        x.push(8);
        a.pushStack(x);
        System.out.println(a.pop());
        System.out.println(a.pop());
        Stack<Integer> y = a.popStack(2);
        System.out.println(y.toString());
        System.out.println(a.count());
    }
}
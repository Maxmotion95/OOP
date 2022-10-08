package ru.nsu.fit.lylova;

public class Main {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("HEHEH");
        Node<String> v = tree.addVertexByValue("kek");
        Node<String> u = tree.addVertexByValue("foo");
        tree.addVertexByParentAndValue(v, "1234");
        tree.addVertexByParentAndValue(u, "76325");
        System.out.println(v.value);
        for (String s: tree){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("Hello world!");
    }
}
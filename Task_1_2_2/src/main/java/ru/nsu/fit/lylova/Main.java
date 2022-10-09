package ru.nsu.fit.lylova;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>();
        Node<String> v = tree.addVertexByValue("kek");
        Node<String> u = tree.addVertexByValue("foo");
        tree.addVertexByParentAndValue(v, "1234");
        tree.addVertexByParentAndValue(u, "76325");
        System.out.println(v.value);
        for (String s : tree) {
            System.out.print(s + " ");
        }
        System.out.println();
        ArrayList<String> delete = new ArrayList<>();
        delete.add("kek");
        delete.add("76325");
        tree.removeAll(delete);
        System.out.println("After deleting:");
        for (var s : tree) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("Hello world!");
    }
}
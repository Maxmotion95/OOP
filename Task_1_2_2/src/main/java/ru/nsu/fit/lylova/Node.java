package ru.nsu.fit.lylova;

import java.util.ArrayList;

public class Node<T> {
    public final ArrayList<Node<T>> children = new ArrayList<>();
    public T value = null;
    public Node<T> parent = null;

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public Node(Node<T> parent) {
        this.parent = parent;
    }

    public Node(Node<T> parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    public void addChild(Node<T> child) {
        this.children.add(child);
    }

    public int cntChildren() {
        return this.children.size();
    }
}

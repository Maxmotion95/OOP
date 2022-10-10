package ru.nsu.fit.lylova;

import java.util.ArrayList;

/**
 * Class that implements the vertex of the tree.
 *
 * @param <T> type of value in {@code Node}
 */
public class Node<T> {
    public final ArrayList<Node<T>> children = new ArrayList<>();
    public T value = null;
    public Node<T> parent = null;

    /**
     * Creates empty vertex with {@code parent} = {@code null}, {@code value} = {@code null}.
     */
    public Node() {
    }

    /**
     * Creates vertex with {@code parent} = {@code null} and specified value.
     *
     * @param value value of vertex
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Creates vertex with specified parent and {@code value} = {@code null}.
     *
     * @param parent parent of vertex
     */
    public Node(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * Creates vertex with specified parent and value.
     *
     * @param parent parent of vertex
     * @param value  value of vertex
     */
    public Node(Node<T> parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    /**
     * Return value of vertex in string. Equals to {@code .value.toString()}.
     *
     * @return value of vertex in string
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Add vertex {@code child} to {@code children} array.
     *
     * @param child child vertex
     */
    public void addChild(Node<T> child) {
        this.children.add(child);
    }

    /**
     * Returns children count of vertex. Equals to {@code .children.size()}.
     *
     * @return children count of vertex
     */
    public int cntChildren() {
        return this.children.size();
    }
}

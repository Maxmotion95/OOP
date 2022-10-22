package ru.nsu.fit.lylova;

import java.util.ArrayList;

/**
 * Class that implements the vertex of the tree.
 *
 * @param <T> type of value in {@code Node}
 */
public class Node<T> {
    private final ArrayList<Node<T>> getChildren = new ArrayList<>();
    private T value = null;
    private Node<T> parent = null;

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
        child.parent = this;
        this.getChildren.add(child);
    }

    /**
     * Returns children count of vertex. Equals to {@code .children.size()}.
     *
     * @return children count of vertex
     */
    public int childrenCount() {
        return this.getChildren.size();
    }

    /**
     * Returns children array of vertex.
     *
     * @return children array of vertex
     */
    public ArrayList<Node<T>> getChildren() { return this.getChildren; }

    /**
     * Returns value of vertex.
     *
     * @return value of vertex
     */
    public T getValue() { return this.value; }

    /**
     * Returns parent of vertex.
     *
     * @return parent of vertex
     */
    public Node<T> getParent() { return this.parent; }

    /**
     * Set vertex parent to {@code newParent}.
     *
     * @param newParent vertex that is new parent
     */
    public void setParent(Node<T> newParent) { this.parent = newParent; }
}

package ru.nsu.fit.lylova;

import java.util.*;

public class Tree<T> implements Collection<T> {
    private final Node<T> root;
    private int cntNodes;

    Tree(T value) {
        root = new Node<>(value);
        cntNodes = 1;
    }

    Tree() {
        root = new Node<>();
        cntNodes = 0;
    }

    @Override
    public int size() {
        return cntNodes;
    }

    @Override
    public boolean isEmpty() {
        return cntNodes == 0;
    }

    @Override
    public boolean add(T t) {
        Node<T> newNode = new Node<>(root, t);
        root.addChild(newNode);
        ++cntNodes;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (T value : this) {
            if (value == o) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeDfsIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] res = new Object[cntNodes];
        int i = 0;
        for (T value : this) {
            res[i] = value;
            ++i;
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new NullPointerException();
        }
        if (a.length < cntNodes) {
            a = (T1[]) new Object[cntNodes];
        }
        int i = 0;
        for (T value : this) {
            a[i] = (T1) value;
            ++i;
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    Node<T> addVertexByValue(T value) {
        Node<T> newNode = new Node<>(root, value);
        root.addChild(newNode);
        ++cntNodes;
        return newNode;
    }

    Node<T> addVertexByParentAndValue(Node<T> parent, T value) {
        Node<T> newNode = new Node<>(parent, value);
        ++cntNodes;
        parent.addChild(newNode);
        return newNode;
    }

    public class TreeDfsIterator implements Iterator<T> {

        private final Map<Node<T>, Integer> pastChild;
        private Node<T> node;
        private boolean isStartedDfs = false;

        public TreeDfsIterator() {
            node = root;
            pastChild = new HashMap<>();
            pastChild.put(root, -1);
        }

        @Override
        public boolean hasNext() {
            Node<T> tmp = node;
            while (tmp != null && pastChild.get(tmp) + 1 == tmp.cntChildren()) {
                tmp = tmp.parent;
            }
            return tmp != null;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!isStartedDfs) {
                isStartedDfs = true;
                return node.value;
            }
            if (!this.hasNext()) {
                node = null;
                throw new NoSuchElementException();
            }
            while (pastChild.get(node) + 1 == node.cntChildren()) {
                node = node.parent;
            }
            int id = pastChild.get(node);
            ++id;
            pastChild.put(node, id);
            node = node.children.get(id);
            pastChild.put(node, -1);
            return node.value;
        }
    }
}

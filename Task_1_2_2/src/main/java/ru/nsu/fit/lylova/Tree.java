package ru.nsu.fit.lylova;

import java.util.*;

public class Tree<T> implements Collection<T> {
    private final Node<T> root;
    private int cntNodes;

    public Tree() {
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
        return new Iterator<>() {
            final Iterator<Node<T>> dfsIterator = new TreeDfsIterator();

            @Override
            public boolean hasNext() {
                return dfsIterator.hasNext();
            }

            @Override
            public T next() {
                return dfsIterator.next().value;
            }
        };
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
        Iterator<Node<T>> iterator = new TreeDfsIterator();
        while (iterator.hasNext()) {
            Node<T> vertex = iterator.next();
            if (o == vertex.value) {
                try {
                    removeVertexByNode(vertex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var o : c) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (var o : c) {
            this.add(o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (var o : c) {
            if (!this.remove(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator<Node<T>> iterator = new TreeDfsIterator();
        while (iterator.hasNext()) {
            Node<T> vertex = iterator.next();
            if (!c.contains(vertex.value)) {
                this.remove(vertex);
            }
        }
        return true;
    }

    @Override
    public void clear() {
        root.children.clear();
        cntNodes = 0;
    }

    public Node<T> addVertexByValue(T value) {
        Node<T> newNode = new Node<>(root, value);
        root.addChild(newNode);
        ++cntNodes;
        return newNode;
    }

    public Node<T> addVertexByParentAndValue(Node<T> parent, T value) {
        Node<T> newNode = new Node<>(parent, value);
        ++cntNodes;
        parent.addChild(newNode);
        return newNode;
    }

    public boolean removeVertexByNode(Node<T> vertex) throws Exception {
        if (vertex.parent == null)
            throw new Exception();
        Node<T> par = vertex.parent;
        for (Node<T> child : vertex.children) {
            child.parent = par;
        }
        par.children.remove(vertex);
        par.children.addAll(vertex.children);
        return true;
    }

    public class TreeDfsIterator implements Iterator<Node<T>> {
        private final Stack<Node<T>> stackNodes;
        private final Stack<Integer> pastId;
        int idNow;
        private Node<T> node;

        public TreeDfsIterator() {
            stackNodes = new Stack<>();
            pastId = new Stack<>();
            node = root;
            idNow = -1;
        }

        @Override
        public boolean hasNext() {
            if (idNow + 1 != node.cntChildren())
                return true;
            while (!stackNodes.empty() && stackNodes.lastElement().cntChildren() == pastId.lastElement() + 1) {
                node = stackNodes.pop();
                idNow = pastId.pop();
            }
            return !stackNodes.empty();
        }

        @Override
        public Node<T> next() throws NoSuchElementException {
            if (!this.hasNext()) {
                node = null;
                throw new NoSuchElementException();
            }
            while (idNow + 1 == node.cntChildren()) {
                node = stackNodes.pop();
                idNow = pastId.pop();
            }
            ++idNow;
            stackNodes.push(node);
            pastId.push(idNow);
            node = node.children.get(idNow);
            idNow = -1;
            return node;
        }
    }

    public class TreeBfsIterator implements Iterator<Node<T>> {
        private final Queue<Node<T>> queueNodes;
        private Node<T> nodeNow;
        private int idNow;

        public TreeBfsIterator() {
            queueNodes = new LinkedList<>();
            nodeNow = root;
            idNow = -1;
        }

        @Override
        public boolean hasNext() {
            if (idNow + 1 != nodeNow.cntChildren())
                return true;
            while (!queueNodes.isEmpty() && idNow + 1 == nodeNow.cntChildren()) {
                nodeNow = queueNodes.remove();
                idNow = -1;
            }
            return !queueNodes.isEmpty();
        }

        @Override
        public Node<T> next() throws NoSuchElementException {
            if (!this.hasNext())
                throw new NoSuchElementException();
            while (idNow + 1 == nodeNow.cntChildren()) {
                nodeNow = queueNodes.remove();
                idNow = -1;
            }
            ++idNow;
            queueNodes.add(nodeNow.children.get(idNow));
            return nodeNow.children.get(idNow);
        }
    }
}

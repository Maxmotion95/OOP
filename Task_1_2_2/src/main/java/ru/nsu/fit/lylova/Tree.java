package ru.nsu.fit.lylova;

import java.util.*;

/**
 * Class that implement collection tree. Tree is a connected, acyclic, oriented graph.
 *
 * @param <T> type of value in vertexes of tree
 */
public class Tree<T> implements Collection<T> {
    private final Node<T> root;
    private int cntNodes;

    /**
     * Creates empty tree.
     */
    public Tree() {
        root = new Node<>();
        cntNodes = 0;
    }

    /**
     * Returns number of vertices in tree
     *
     * @return number of vertices in the tree
     */
    @Override
    public int size() {
        return cntNodes;
    }

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return cntNodes == 0;
    }

    /**
     * Add vertex with specified value to root.
     *
     * @param t element whose presence in this collection is to be ensured
     * @return {@code true}
     */
    @Override
    public boolean add(T t) {
        Node<T> newNode = new Node<>(root, t);
        root.addChild(newNode);
        ++cntNodes;
        return true;
    }

    /**
     * Returns true if this collection contains the specified element.
     *
     * @param o element whose presence in this collection is to be tested
     * @return {@code true} if tree contain vertex with this object
     */
    @Override
    public boolean contains(Object o) {
        for (T value : this) {
            if (value == o) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this collection.
     * The order of the vertices corresponds to the DFS traversal.
     *
     * @return an {@code Iterator} over the elements in tree
     */
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

    /**
     * Returns an array containing all elements in tree.
     *
     * @return array containing all elements in tree
     */
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

    /**
     * Returns an array containing all elements in tree; the runtime type of the returned array
     * is that of the specified array. If the collection fits in the specified array,
     * it is returned therein. Otherwise, a new array is allocated with the runtime type
     * of the specified array and the size of tree.
     *
     * @param a    the array into which the elements of this collection are to be
     *             stored, if it is big enough; otherwise, a new array of the same
     *             runtime type is allocated for this purpose.
     * @param <T1> Type of elements in specified collection
     * @return array contains all elements of tree
     */
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

    /**
     * Removes a single instance of the specified element from tree if it is present.
     *
     * @param o element to be removed from this collection, if present
     * @return {@code true} if element deleted from tree
     */
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

    /**
     * Returns {@code true} if tree contains all elements
     * in the specified collection.
     *
     * @param c collection to be checked for containment in this collection
     * @return {@code true} if all elements contains in tree
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (var o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all elements in the specified collection to tree.
     *
     * @param c collection containing elements to be added to this collection
     * @return {@code true}
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (var o : c) {
            this.add(o);
        }
        return true;
    }

    /**
     * Removes all of this collection's elements that are also contained in the
     * tree.
     *
     * @param c collection containing elements to be removed from this collection
     * @return {@code true} if all elements removed
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        for (var o : c) {
            if (!this.remove(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retains only the elements in this collection that are contained in the tree.
     * In other words, removes from tree all of its elements that are
     * not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this collection
     * @return {@code true} if tree changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator<Node<T>> iterator = new TreeDfsIterator();
        boolean res = false;
        while (iterator.hasNext()) {
            Node<T> vertex = iterator.next();
            if (!c.contains(vertex.value)) {
                this.remove(vertex);
                res = true;
            }
        }
        return res;
    }

    /**
     * Clear tree.
     */
    @Override
    public void clear() {
        root.children.clear();
        cntNodes = 0;
    }

    /**
     * Add vertex to tree that contains {@code value} and has parent root of tree.
     *
     * @param value value of vertex
     * @return vertex that added
     */
    public Node<T> addVertexByValue(T value) {
        Node<T> newNode = new Node<>(root, value);
        root.addChild(newNode);
        ++cntNodes;
        return newNode;
    }

    /**
     * Add vertex to tree that contains {@code value} and has parent {@code parent}.
     *
     * @param parent vertex that is parent of new vertex
     * @param value  value of vertex
     * @return vertex that added
     */
    public Node<T> addVertexByParentAndValue(Node<T> parent, T value) {
        Node<T> newNode = new Node<>(parent, value);
        ++cntNodes;
        parent.addChild(newNode);
        return newNode;
    }

    /**
     * Delete vertex from tree.
     *
     * @param vertex the vertex to be deleted
     * @return {@code true} if vertex is deleted
     * @throws Exception if vertex parent is null
     */
    public boolean removeVertexByNode(Node<T> vertex) throws Exception {
        if (vertex.parent == null) {
            throw new Exception();
        }
        Node<T> par = vertex.parent;
        for (Node<T> child : vertex.children) {
            child.parent = par;
        }
        par.children.remove(vertex);
        par.children.addAll(vertex.children);
        return true;
    }

    /**
     * An iterator that returns the elements of the tree in the order of DFS traversal.
     */
    public class TreeDfsIterator implements Iterator<Node<T>> {
        private final Stack<Node<T>> stackNodes;
        private final Stack<Integer> pastId;
        private int idNow;
        private Node<T> node;

        /**
         * Creates a tree iterator using a DFS traversal.
         */
        public TreeDfsIterator() {
            stackNodes = new Stack<>();
            pastId = new Stack<>();
            node = root;
            idNow = -1;
        }

        /**
         * Returns true if the iteration has more elements. (In other words,
         * returns true if next would return an element rather than throwing an exception.).
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (idNow + 1 != node.cntChildren()) {
                return true;
            }
            while (!stackNodes.empty() &&
                    stackNodes.lastElement().cntChildren() == pastId.lastElement() + 1) {
                node = stackNodes.pop();
                idNow = pastId.pop();
            }
            return !stackNodes.empty();
        }

        /**
         * Returns true if the iteration has more elements. (In other words,
         * returns true if next would return an element rather than throwing an exception.).
         *
         * @return true if the iteration has more elements
         */
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

    /**
     * An iterator that returns the elements of the tree in the order of BFS traversal.
     */
    public class TreeBfsIterator implements Iterator<Node<T>> {
        private final Queue<Node<T>> queueNodes;
        private Node<T> nodeNow;
        private int idNow;

        /**
         * Creates a tree iterator using a BFS traversal.
         */
        public TreeBfsIterator() {
            queueNodes = new LinkedList<>();
            nodeNow = root;
            idNow = -1;
        }

        /**
         * Returns true if the iteration has more elements. (In other words,
         * returns true if next would return an element rather than throwing an exception.).
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (idNow + 1 != nodeNow.cntChildren()) {
                return true;
            }
            while (!queueNodes.isEmpty() && idNow + 1 == nodeNow.cntChildren()) {
                nodeNow = queueNodes.remove();
                idNow = -1;
            }
            return idNow + 1 != nodeNow.cntChildren();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException – if the iteration has no more elements
         */
        @Override
        public Node<T> next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
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

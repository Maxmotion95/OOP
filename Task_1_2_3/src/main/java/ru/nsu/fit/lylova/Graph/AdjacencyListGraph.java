package ru.nsu.fit.lylova.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@code Graph} interface, that stores edges using adjacency list.
 *
 * @param <V> class of vertexes
 * @param <E> class of edge value
 */
public class AdjacencyListGraph<V, E> implements Graph<V, E> {
    private final List<AdjacencyListEdge> listEdges;
    private final Set<V> vertexes;

    /**
     * Constructs an empty graph.
     */
    public AdjacencyListGraph() {
        listEdges = new ArrayList<>();
        vertexes = new HashSet<>();
    }

    /**
     * Add vertex with value {@code v} to graph.
     *
     * @param v value of new vertex
     * @return {@code true} if vertex was added
     */
    @Override
    public boolean addVertex(V v) {
        if (vertexes.contains(v)) {
            return false;
        }
        vertexes.add(v);
        return true;
    }

    /**
     * Remove vertex with value {@code v} from graph.
     *
     * @param v value of vertex to remove
     * @return {@code true} if graph was changed
     */
    @Override
    public boolean removeVertex(V v) {
        if (!vertexes.contains(v)) {
            return false;
        }
        List<AdjacencyListEdge> forDelete = new ArrayList<>();
        for (var e : listEdges) {
            if (e.start.equals(v) || e.end.equals(v)) {
                forDelete.add(e);
            }
        }
        listEdges.removeAll(forDelete);
        vertexes.remove(v);
        return true;
    }

    /**
     * Add edge from vertex {@code a} to vertex {@code b}.
     * Return {@code true} if edge was added.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @param e value of edge
     * @return {@code true} if edge was added
     */
    @Override
    public boolean addEdge(V a, V b, E e) {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            return false;
        }
        AdjacencyListEdge x = new AdjacencyListEdge(a, b, e);
        listEdges.add(x);
        return true;
    }

    /**
     * Remove edge from vertex {@code a} to vertex {@code b}.
     * Return {@code true} if edge was removed.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return {@code true} if edge was removed
     */
    @Override
    public boolean removeEdge(V a, V b) {
        for (var e : listEdges) {
            if (e.start.equals(a) && e.end.equals(b)) {
                listEdges.remove(e);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns ArrayList that contains all vertexes.
     *
     * @return ArrayList that contains all vertexes
     */
    @Override
    public ArrayList<V> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

    /**
     * Return value of edge that starts from vertex with value {@code a} and
     * ends in vertex with value {@code b}.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return value of edge that starts from vertex {@code a} and ends in vertex {@code b}
     * @throws java.lang.Exception such edge doesn't exist
     */
    @Override
    public E getEdge(V a, V b) throws Exception {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            throw new Exception();
        }
        for (var e : listEdges) {
            if (e.start.equals(a) && e.end.equals(b)) {
                return e.value;
            }
        }
        throw new Exception();
    }

    private class AdjacencyListEdge {
        private final V start;
        private final V end;
        private final E value;

        private AdjacencyListEdge(V a, V b, E e) {
            start = a;
            end = b;
            value = e;
        }
    }
}

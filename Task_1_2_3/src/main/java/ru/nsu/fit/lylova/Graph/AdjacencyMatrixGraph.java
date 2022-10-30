package ru.nsu.fit.lylova.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@code Graph} interface, that stores edges using adjacency matrix.
 *
 * @param <V> class of vertexes
 * @param <E> class of edge value
 */
public class AdjacencyMatrixGraph<V, E> implements Graph<V, E> {
    private final Set<V> vertexes;
    private final Map<V, Map<V, E>> matrix;

    /**
     * Constructs an empty graph.
     */
    public AdjacencyMatrixGraph() {
        vertexes = new HashSet<>();
        matrix = new HashMap<>();
    }

    /**
     * Add vertex with value {@code v} to graph.
     *
     * @param v value of new vertex
     * @return {@code true} if vertex was added
     */
    @Override
    public boolean addVertex(V v) {
        if (vertexes.contains(v))
            return false;
        Map<V, E> row_v = new HashMap<>();
        row_v.put(v, null);
        for (var u : vertexes) {
            matrix.get(u).put(v, null);
            row_v.put(u, null);
        }
        matrix.put(v, row_v);
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
        vertexes.remove(v);
        matrix.remove(v);
        for (var u : vertexes) {
            matrix.get(u).remove(v);
        }
        return true;
    }

    /**
     * Add edge from vertex {@code a} to vertex {@code b}.
     * If value of edge {@code e} equals {@code null} then edge will be removed.
     * Return {@code true} if edge was added.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @param e value of edge
     * @return {@code true} if edge was added
     */
    @Override
    public boolean addEdge(V a, V b, E e) {
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) != null) {
            return false;
        }
        matrix.get(a).put(b, e);
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
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) == null) {
            return false;
        }
        matrix.get(a).put(b, null);
        return true;
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
    public E getEdge(V a, V b) throws java.lang.Exception {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            throw new java.lang.Exception();
        }
        if (matrix.get(a).get(b) == null) {
            throw new Exception();
        }
        return matrix.get(a).get(b);
    }
}

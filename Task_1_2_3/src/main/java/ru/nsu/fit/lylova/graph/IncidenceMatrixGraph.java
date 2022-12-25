package ru.nsu.fit.lylova.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@code Graph} interface, that stores edges using incidence matrix.
 *
 * @param <V> class of vertexes
 * @param <E> class of edge value
 */
public class IncidenceMatrixGraph<V, E> extends Graph<V, E> {
    private final Map<V, Map<E, V>> matrix;
    private final Set<V> vertexes;

    /**
     * Constructs an empty graph.
     */
    public IncidenceMatrixGraph() {
        matrix = new HashMap<>();
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
        matrix.put(v, new HashMap<>());
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
            var row = matrix.get(u);
            List<E> forDelete = new ArrayList<>();
            for (var i : row.keySet()) {
                if (row.get(i).equals(v)) {
                    forDelete.add(i);
                }
            }
            for (var i : forDelete) {
                row.remove(i);
            }
        }
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
        matrix.get(a).put(e, b);
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
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            return false;
        }
        var row = matrix.get(a);
        for (var e : row.keySet()) {
            if (row.get(e).equals(b)) {
                row.remove(e);
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
        var row = matrix.get(a);
        for (var e : row.keySet()) {
            if (row.get(e).equals(b)) {
                return e;
            }
        }
        throw new Exception();
    }
}

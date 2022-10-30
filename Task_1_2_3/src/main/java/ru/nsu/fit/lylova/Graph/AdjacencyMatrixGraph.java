package ru.nsu.fit.lylova.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrixGraph<V, E> implements Graph<V, E> {
    private final Set<V> vertexes;
    private final Map<V, Map<V, E>> matrix;

    public AdjacencyMatrixGraph() {
        vertexes = new HashSet<>();
        matrix = new HashMap<>();
    }

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

    @Override
    public boolean addEdge(V a, V b, E e) {
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) != null) {
            return false;
        }
        matrix.get(a).put(b, e);
        return true;
    }

    @Override
    public boolean removeEdge(V a, V b) {
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) == null) {
            return false;
        }
        matrix.get(a).put(b, null);
        return true;
    }

    @Override
    public ArrayList<V> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

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

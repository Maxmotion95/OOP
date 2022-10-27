package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncidenceMatrixGraph<V, E> implements Graph<V, E> {
    private final Map<V, Map<E, V>> matrix;
    private final Set<V> vertexes;

    IncidenceMatrixGraph() {
        matrix = new HashMap<>();
        vertexes = new HashSet<>();
    }

    @Override
    public boolean addVertex(V v) {
        if (vertexes.contains(v)) {
            return false;
        }
        vertexes.add(v);
        matrix.put(v, new HashMap<>());
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
            var row = matrix.get(u);
            for (var i : row.keySet()) {
                if (row.get(i).equals(v)) {
                    row.remove(i);
                }
            }
        }
        return true;
    }

    @Override
    public boolean addEdge(V a, V b, E e) {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            return false;
        }
        matrix.get(a).put(e, b);
        return true;
    }

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

    @Override
    public ArrayList<V> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

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

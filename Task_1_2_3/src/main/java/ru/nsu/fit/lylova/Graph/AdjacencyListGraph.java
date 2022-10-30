package ru.nsu.fit.lylova.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacencyListGraph<V, E> implements Graph<V, E> {
    private final List<AdjacencyListEdge> listEdges;
    private final Set<V> vertexes;

    public AdjacencyListGraph() {
        listEdges = new ArrayList<>();
        vertexes = new HashSet<>();
    }

    @Override
    public boolean addVertex(V v) {
        if (vertexes.contains(v)) {
            return false;
        }
        vertexes.add(v);
        return true;
    }

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

    @Override
    public boolean addEdge(V a, V b, E e) {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            return false;
        }
        AdjacencyListEdge x = new AdjacencyListEdge(a, b, e);
        listEdges.add(x);
        return true;
    }

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

    @Override
    public ArrayList<V> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

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

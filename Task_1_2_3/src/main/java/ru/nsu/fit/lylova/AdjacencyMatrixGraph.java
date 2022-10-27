package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrixGraph<Vertex, Edge> implements Graph<Vertex, Edge> {
    private final Set<Vertex> vertexes;
    private final Map<Vertex, Map<Vertex, Edge>> matrix;

    AdjacencyMatrixGraph() {
        vertexes = new HashSet<>();
        matrix = new HashMap<>();
    }

    @Override
    public boolean addVertex(Vertex v) {
        if (vertexes.contains(v))
            return false;
        Map<Vertex, Edge> row_v = new HashMap<>();
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
    public boolean removeVertex(Vertex v) {
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
    public boolean addEdge(Vertex a, Vertex b, Edge e) {
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) != null) {
            return false;
        }
        matrix.get(a).put(b, e);
        return true;
    }

    @Override
    public boolean removeEdge(Vertex a, Vertex b) {
        if (!vertexes.contains(a) || !vertexes.contains(b) || matrix.get(a).get(b) == null) {
            return false;
        }
        matrix.get(a).put(b, null);
        return true;
    }

    @Override
    public ArrayList<Vertex> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

    @Override
    public Edge getEdge(Vertex a, Vertex b) throws java.lang.Exception {
        if (!vertexes.contains(a) || !vertexes.contains(b))
            throw new java.lang.Exception();
        return matrix.get(a).get(b);
    }
}

package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacencyListGraph<Vertex, Edge> implements Graph<Vertex, Edge> {
    private final List<AdjacencyListEdge> listEdges;
    private final Set<Vertex> vertexes;

    public AdjacencyListGraph() {
        listEdges = new ArrayList<>();
        vertexes = new HashSet<>();
    }

    @Override
    public boolean addVertex(Vertex v) {
        if (vertexes.contains(v)) {
            return false;
        }
        vertexes.add(v);
        return true;
    }

    @Override
    public boolean removeVertex(Vertex v) {
        if (!vertexes.contains(v)) {
            return false;
        }
        List<AdjacencyListEdge> toDel = new ArrayList<>();
        for (var e : listEdges) {
            if (e.start.equals(v) || e.end.equals(v)) {
                toDel.add(e);
            }
        }
        listEdges.removeAll(toDel);
        vertexes.remove(v);
        return true;
    }

    @Override
    public boolean addEdge(Vertex a, Vertex b, Edge e) {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            return false;
        }
        AdjacencyListEdge x = new AdjacencyListEdge(a, b, e);
        listEdges.add(x);
        return true;
    }

    @Override
    public boolean removeEdge(Vertex a, Vertex b) {
        for (var e : listEdges) {
            if (e.start.equals(a) && e.end.equals(b)) {
                listEdges.remove(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Vertex> arrayVertexes() {
        return new ArrayList<>(vertexes);
    }

    @Override
    public Edge getEdge(Vertex a, Vertex b) throws Exception {
        if (!vertexes.contains(a) || !vertexes.contains(b)) {
            throw new Exception();
        }
        for (var e : listEdges) {
            if (e.start.equals(a) && e.end.equals(b)) {
                return e.edge;
            }
        }
        throw new Exception();
    }

    private class AdjacencyListEdge {
        private final Vertex start;
        private final Vertex end;
        private final Edge edge;

        private AdjacencyListEdge(Vertex a, Vertex b, Edge e) {
            start = a;
            end = b;
            edge = e;
        }
    }
}

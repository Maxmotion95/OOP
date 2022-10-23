package ru.nsu.fit.lylova;

import java.util.ArrayList;

public interface Graph<Vertex, Edge>{
    boolean addVertex(Vertex v);

    boolean removeVertex(Vertex v);

    boolean addEdge(Vertex a, Vertex b, Edge e);

    boolean removeEdge(Vertex a, Vertex b);

    ArrayList<Vertex> arrayVertexes();

    Edge getEdge(Vertex a, Vertex b) throws java.lang.Exception;
}

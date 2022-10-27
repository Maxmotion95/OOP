package ru.nsu.fit.lylova;

import java.util.ArrayList;

/**
 * Interface of graph with vertexes of the {@code Vertex} class and edges of the {@code Edge} class.
 *
 * @param <Vertex> class of vertexes
 * @param <Edge>   class of edges
 */
public interface Graph<Vertex, Edge> {
    /**
     * Add vertex with value {@code v}.
     * Returns {@code true} if vertex was added. Otherwise, return {@code false}.
     *
     * @param v value of new vertex
     * @return {@code true} if vertex was added
     */
    boolean addVertex(Vertex v);

    /**
     * Remove vertex with value {@code v}.
     * Returns {@code true} if vertex was removed. Otherwise, return {@code false}.
     *
     * @param v value of vertex to remove
     * @return {@code true} if vertex was deleted
     */
    boolean removeVertex(Vertex v);

    /**
     * Add edge from vertex {@code a} to vertex {@code b} with value {@code e}.
     * Return {@code true} if edge was added.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @param e value of edge
     * @return {@code true} if edge was added
     */
    boolean addEdge(Vertex a, Vertex b, Edge e);

    /**
     * Remove edge from vertex {@code a} to vertex {@code b}.
     * Return {@code true} if edge was removed.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return {@code true} if edge was removed
     */
    boolean removeEdge(Vertex a, Vertex b);

    /**
     * Returns ArrayList that contains all vertexes.
     *
     * @return ArrayList that contains all vertexes
     */
    ArrayList<Vertex> arrayVertexes();

    /**
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return value of edge that starts from vertex {@code a} and ends in vertex {@code b}
     * @throws java.lang.Exception such edge doesn't exist
     */
    Edge getEdge(Vertex a, Vertex b) throws java.lang.Exception;
}

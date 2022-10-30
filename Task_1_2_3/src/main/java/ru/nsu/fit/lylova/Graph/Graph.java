package ru.nsu.fit.lylova.Graph;

import java.util.ArrayList;

/**
 * Interface of graph with vertexes of the {@code V} class and edges of the {@code E} class.
 *
 * @param <V> class of vertexes
 * @param <E> class of edges
 */
public interface Graph<V, E> {
    /**
     * Add vertex with value {@code v}.
     * Returns {@code true} if vertex was added. Otherwise, return {@code false}.
     *
     * @param v value of new vertex
     * @return {@code true} if vertex was added
     */
    boolean addVertex(V v);

    /**
     * Remove vertex with value {@code v}.
     * Returns {@code true} if vertex was removed. Otherwise, return {@code false}.
     *
     * @param v value of vertex to remove
     * @return {@code true} if vertex was deleted
     */
    boolean removeVertex(V v);

    /**
     * Add edge from vertex {@code a} to vertex {@code b} with value {@code e}.
     * Return {@code true} if edge was added.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @param e value of edge
     * @return {@code true} if edge was added
     */
    boolean addEdge(V a, V b, E e);

    /**
     * Remove edge from vertex {@code a} to vertex {@code b}.
     * Return {@code true} if edge was removed.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return {@code true} if edge was removed
     */
    boolean removeEdge(V a, V b);

    /**
     * Returns ArrayList that contains all vertexes.
     *
     * @return ArrayList that contains all vertexes
     */
    ArrayList<V> arrayVertexes();

    /**
     * Return value of edge that starts from vertex with value {@code a} and
     * ends in vertex with value {@code b}.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return value of edge that starts from vertex {@code a} and ends in vertex {@code b}
     * @throws java.lang.Exception such edge doesn't exist
     */
    E getEdge(V a, V b) throws java.lang.Exception;
}

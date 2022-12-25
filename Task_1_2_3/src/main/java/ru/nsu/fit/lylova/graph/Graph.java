package ru.nsu.fit.lylova.graph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Abstract class of graph with vertexes of the {@code V} class and edges of the {@code E} class.
 *
 * @param <V> class of vertexes
 * @param <E> class of edges
 */
public abstract class Graph<V, E> {
    /**
     * Add vertex with value {@code v}.
     * Returns {@code true} if vertex was added. Otherwise, return {@code false}.
     *
     * @param v value of new vertex
     * @return {@code true} if vertex was added
     */
    abstract public boolean addVertex(V v);

    /**
     * Remove vertex with value {@code v}.
     * Returns {@code true} if vertex was removed. Otherwise, return {@code false}.
     *
     * @param v value of vertex to remove
     * @return {@code true} if vertex was deleted
     */
    abstract public boolean removeVertex(V v);

    /**
     * Add edge from vertex {@code a} to vertex {@code b} with value {@code e}.
     * Return {@code true} if edge was added.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @param e value of edge
     * @return {@code true} if edge was added
     */
    abstract public boolean addEdge(V a, V b, E e);

    /**
     * Remove edge from vertex {@code a} to vertex {@code b}.
     * Return {@code true} if edge was removed.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return {@code true} if edge was removed
     */
    abstract public boolean removeEdge(V a, V b);

    /**
     * Returns ArrayList that contains all vertexes.
     *
     * @return ArrayList that contains all vertexes
     */
    abstract public ArrayList<V> arrayVertexes();

    /**
     * Return value of edge that starts from vertex with value {@code a} and
     * ends in vertex with value {@code b}.
     *
     * @param a start vertex of edge
     * @param b end vertex of edge
     * @return value of edge that starts from vertex {@code a} and ends in vertex {@code b}
     * @throws java.lang.Exception such edge doesn't exist
     */
    abstract public E getEdge(V a, V b) throws java.lang.Exception;

    /**
     * Initializes graph from scanner data.
     * Does not clean the graph before initialization,
     * but only adds the graph described in the scanner.
     * To parse the scanner token into a vertex, the {@code vertexParser} function is used.
     * To parse the scanner token into an edge, the {@code edgeParser} function is used.
     * <br>Format of graph data:
     * <ul>
     *     <li>First token of scanner is integer number, which is equal to the number of vertices.</li>
     *     <li>The following n tokens contain vertices.</li>
     *     <li>Next token is integer number, which is equal to the number of edges.</li>
     *     <li>The next n triples of tokens describe the edges.
     *     Each edge is described by two vertices and an edge.
     *     The first two tokens in the triple are the starting and ending vertices of the edge.
     *     The third token is the content of the edge.</li>
     * </ul>
     *
     * @param vertexParser function that parses vertex
     * @param edgeParser function that parses edge
     * @param scanner scanner with graph data
     */
    public void initializationFromScanner(Function<String, V> vertexParser, Function<String, E> edgeParser, Scanner scanner) {
        int n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            this.addVertex(vertexParser.apply(scanner.next()));
        }

        int m = scanner.nextInt();
        for (int i = 0; i < m; ++i) {
            V vertex1 = vertexParser.apply(scanner.next());
            V vertex2 = vertexParser.apply(scanner.next());
            E edge = edgeParser.apply(scanner.next());
            this.addEdge(vertex1, vertex2, edge);
        }
    }
}

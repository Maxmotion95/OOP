package ru.nsu.fit.lylova.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ru.nsu.fit.lylova.graph.Graph;

/**
 * Class with function calculate for calculating distance of the shortest paths from some vertex.
 *
 * @param <G> Graph class
 * @param <V> Vertex class
 * @param <E> Edge class
 * @param <W> Weight class
 */
public class ShortestPathInGraph<G extends Graph<V, E>, V,
        E extends GraphEdge<W>, W extends GraphEdgeWeight<W> & Comparable<W>> {
    /**
     * Constructor of class.
     */
    public ShortestPathInGraph() {
    }

    /**
     * Function that calculate the shortest distance from vertex {@code start}
     * to every vertex from graph. If the vertex is unreachable from
     * the starting point, then distance is {@code null}.
     *
     * @param graph     graph
     * @param start     start vertex
     * @param startDist distance start vertex
     * @return Map from vertex to the shortest distance
     */
    public Map<V, W> calculate(G graph, V start, W startDist) {
        if (!graph.arrayVertexes().contains(start)) {
            return null;
        }
        Map<V, W> result = new HashMap<>();
        ArrayList<V> vertexes = graph.arrayVertexes();
        for (var v : vertexes) {
            result.put(v, null);
        }
        result.put(start, startDist);
        for (int i = 0; i < vertexes.size() - 1; ++i) {
            for (var u : vertexes) {
                if (result.get(u) == null) {
                    continue;
                }
                for (var v : vertexes) {
                    try {
                        E e = graph.getEdge(u, v);
                        if (e == null) {
                            continue;
                        }
                        if (result.get(v) == null
                                || result.get(u).sum(e.getWeight()).compareTo(result.get(v)) < 0) {
                            W newW = result.get(u).sum(e.getWeight());
                            result.put(v, newW);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return result;
    }
}

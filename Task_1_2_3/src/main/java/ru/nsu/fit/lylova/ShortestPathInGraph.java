package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortestPathInGraph<G extends Graph<V, E>, V, E extends GraphEdge<W>, W extends GraphEdgeWeight<W> & Comparable<W>> {
    ShortestPathInGraph() {
    }

    public Map<V, W> calculate(G graph, V start, W startDist) {
        if (!graph.arrayVertexes().contains(start))
            return null;
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
                        if (result.get(v) == null || result.get(u).sum(e.getWeight()).compareTo(result.get(v)) < 0) {
                            W newW = result.get(u).sum(e.getWeight());
                            result.put(v, newW);
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return result;
    }
}

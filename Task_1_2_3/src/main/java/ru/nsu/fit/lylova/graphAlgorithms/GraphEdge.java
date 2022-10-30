package ru.nsu.fit.lylova.graphAlgorithms;

/**
 * The interface of the edge of the graph from which you can find out its weight.
 *
 * @param <W> class of edge weight
 */
public interface GraphEdge<W extends GraphEdgeWeight<W> & Comparable<W>> {
    /**
     * Returns weight of the edge.
     *
     * @return weight of the edge
     */
    W getWeight();
}

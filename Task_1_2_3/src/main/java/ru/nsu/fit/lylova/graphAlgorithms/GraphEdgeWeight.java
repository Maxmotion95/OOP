package ru.nsu.fit.lylova.graphAlgorithms;

/**
 * Interface of edge weight.
 *
 * @param <W> weight of edge
 */
public interface GraphEdgeWeight<W extends Comparable<W>> {
    /**
     * Returns sum of two edge weights.
     *
     * @param w weight of another edge
     * @return sum of weights
     */
    W sum(W w);
}

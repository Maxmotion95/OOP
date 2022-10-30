package ru.nsu.fit.lylova.GraphAlgorithms;

public interface GraphEdge<Weight extends GraphEdgeWeight<Weight> & Comparable<Weight>> {
    Weight getWeight();
}

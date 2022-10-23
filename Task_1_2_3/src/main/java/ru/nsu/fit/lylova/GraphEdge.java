package ru.nsu.fit.lylova;

public interface GraphEdge<Weight extends GraphEdgeWeight<Weight> & Comparable<Weight>> {
    Weight getWeight();
}

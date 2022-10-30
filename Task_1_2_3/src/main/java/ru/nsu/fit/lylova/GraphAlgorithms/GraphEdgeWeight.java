package ru.nsu.fit.lylova.GraphAlgorithms;

public interface GraphEdgeWeight<Weight extends Comparable<Weight>> {
    Weight sum(Weight w);
}

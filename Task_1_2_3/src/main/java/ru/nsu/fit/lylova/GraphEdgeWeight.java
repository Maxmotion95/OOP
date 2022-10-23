package ru.nsu.fit.lylova;

public interface GraphEdgeWeight<Weight extends Comparable<Weight>> {
    Weight sum(Weight w);
}

package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.graph.AdjacencyMatrixGraph;
import ru.nsu.fit.lylova.graph.Graph;
import ru.nsu.fit.lylova.graphAlgorithms.GraphEdge;
import ru.nsu.fit.lylova.graphAlgorithms.GraphEdgeWeight;
import ru.nsu.fit.lylova.graphAlgorithms.ShortestPathInGraph;

/**
 * Class Main with function main.
 */
public class Main {

    /**
     * Implementation of example from task description.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Graph<String, Edge> g = new AdjacencyMatrixGraph<>();
        String[] v = {"A", "B", "C", "D", "E", "F", "G"};
        for (var i : v) {
            g.addVertex(i);
        }
        g.addEdge("A", "B", new Edge(5));
        g.addEdge("B", "A", new Edge(5));
        g.addEdge("A", "D", new Edge(12));
        g.addEdge("D", "A", new Edge(12));
        g.addEdge("A", "G", new Edge(25));
        g.addEdge("G", "A", new Edge(25));
        g.addEdge("D", "B", new Edge(8));
        g.addEdge("B", "D", new Edge(8));
        g.addEdge("C", "D", new Edge(2));
        g.addEdge("D", "C", new Edge(2));
        g.addEdge("C", "E", new Edge(4));
        g.addEdge("E", "C", new Edge(4));
        g.addEdge("C", "F", new Edge(5));
        g.addEdge("F", "C", new Edge(5));
        g.addEdge("C", "G", new Edge(10));
        g.addEdge("G", "C", new Edge(10));
        g.addEdge("E", "G", new Edge(5));
        g.addEdge("G", "E", new Edge(5));
        g.addEdge("F", "G", new Edge(5));
        g.addEdge("G", "F", new Edge(5));
        ShortestPathInGraph<Graph<String, Edge>, String, Edge, IntWeight> a =
                new ShortestPathInGraph<>();
        var ans = a.calculate(g, "C", new IntWeight(0));
        for (var i : v) {
            System.out.println(i + ": " + ans.get(i).weight);
        }
    }

    static class IntWeight implements GraphEdgeWeight<IntWeight>, Comparable<IntWeight> {
        Integer weight;

        IntWeight() {
            weight = 0;
        }

        IntWeight(Integer w) {
            weight = w;
        }

        @Override
        public IntWeight sum(IntWeight w) {
            IntWeight res = new IntWeight(this.weight);
            res.weight += w.weight;
            return res;
        }

        @Override
        public int compareTo(IntWeight o) {
            if (this.weight < o.weight) {
                return -1;
            }
            if (this.weight.equals(o.weight)) {
                return 0;
            }
            return 1;
        }
    }

    static class Edge implements GraphEdge<IntWeight> {
        Integer weight;

        Edge() {
            weight = 0;
        }

        Edge(Integer a) {
            weight = a;
        }

        @Override
        public IntWeight getWeight() {
            return new IntWeight(weight);
        }
    }
}
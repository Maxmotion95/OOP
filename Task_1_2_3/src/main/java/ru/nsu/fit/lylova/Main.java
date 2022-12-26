package ru.nsu.fit.lylova;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.function.Function;

import ru.nsu.fit.lylova.algorithms.GraphEdge;
import ru.nsu.fit.lylova.algorithms.GraphEdgeWeight;
import ru.nsu.fit.lylova.algorithms.ShortestPathInGraph;
import ru.nsu.fit.lylova.graph.AdjacencyMatrixGraph;
import ru.nsu.fit.lylova.graph.Graph;


/**
 * Class Main with function main.
 */
public class Main {

    /**
     * Implementation of example from task description.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream("Task_1_2_3/src/main/input.txt"));

        Graph<String, Edge> g = new AdjacencyMatrixGraph<>();

        Function<String, String> vertexParser = str -> str;
        Function<String, Edge> edgeParser = str -> new Edge(Integer.parseInt(str));

        g.initializationFromScanner(vertexParser, edgeParser, sc);

        ShortestPathInGraph<Graph<String, Edge>, String, Edge, IntWeight> a =
                new ShortestPathInGraph<>();
        var ans = a.calculate(g, "C", new IntWeight(0));
        for (var i : g.arrayVertexes()) {
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
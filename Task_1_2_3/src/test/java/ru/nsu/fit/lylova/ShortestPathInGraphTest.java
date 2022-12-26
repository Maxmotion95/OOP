package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lylova.algorithms.GraphEdge;
import ru.nsu.fit.lylova.algorithms.GraphEdgeWeight;
import ru.nsu.fit.lylova.algorithms.ShortestPathInGraph;
import ru.nsu.fit.lylova.graph.AdjacencyListGraph;
import ru.nsu.fit.lylova.graph.AdjacencyMatrixGraph;
import ru.nsu.fit.lylova.graph.Graph;
import ru.nsu.fit.lylova.graph.IncidenceMatrixGraph;

class ShortestPathInGraphTest {

    void init(Graph<Integer, Edge> g, String filepath) throws FileNotFoundException {
        Function<String, Integer> vertexParser = str -> {
            Scanner sc = new Scanner(str);
            return sc.nextInt();
        };

        Function<String, Edge> edgeParser = str -> new Edge(Integer.parseInt(str));

        g.initializeFromScanner(vertexParser, edgeParser, new FileInputStream(filepath));
    }

    @Test
    void testWithAdjacencyList() throws FileNotFoundException {
        Graph<Integer, Edge> g = new AdjacencyListGraph<>();
        init(g, "src/test/ShortestPathInGraphTestSource/graph1.txt");
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a
                = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void testWithAdjacencyMatrix() throws FileNotFoundException {
        Graph<Integer, Edge> g = new AdjacencyMatrixGraph<>();
        init(g, "src/test/ShortestPathInGraphTestSource/graph1.txt");
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a
                = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void testWithIncidenceMatrix() throws FileNotFoundException {
        Graph<Integer, Edge> g = new IncidenceMatrixGraph<>();
        init(g, "src/test/ShortestPathInGraphTestSource/graph1.txt");
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a
                = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
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
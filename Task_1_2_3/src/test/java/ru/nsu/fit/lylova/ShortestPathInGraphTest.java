package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lylova.algorithms.GraphEdge;
import ru.nsu.fit.lylova.algorithms.GraphEdgeWeight;
import ru.nsu.fit.lylova.algorithms.ShortestPathInGraph;
import ru.nsu.fit.lylova.graph.AdjacencyListGraph;
import ru.nsu.fit.lylova.graph.AdjacencyMatrixGraph;
import ru.nsu.fit.lylova.graph.Graph;
import ru.nsu.fit.lylova.graph.IncidenceMatrixGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ShortestPathInGraphTest {

    void init(Graph<Integer, Edge> g, String filepath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filepath));
        int n = sc.nextInt();
        for (int i = 0; i < n; ++i) {
            int v = sc.nextInt();
            g.addVertex(v);
        }

        int m = sc.nextInt();
        for (int i = 0; i < m; ++i) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(u, v, new Edge(w));
        }
    }

    @Test
    void test1() throws FileNotFoundException {
        Graph<Integer, Edge> g = new AdjacencyListGraph<>();
        init(g, "src\\test\\graph1.txt");
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a
                = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void test2() throws FileNotFoundException {
        Graph<Integer, Edge> g = new AdjacencyMatrixGraph<>();
        init(g, "src\\test\\graph1.txt");
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a
                = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void test3() throws FileNotFoundException {
        Graph<Integer, Edge> g = new IncidenceMatrixGraph<>();
        init(g, "src\\test\\graph1.txt");
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
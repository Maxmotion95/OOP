package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestPathInGraphTest {

    void init(Graph<Integer, Edge> g) {
        for (int i = 1; i <= 5; ++i) {
            g.addVertex(i);
        }
        g.addEdge(3, 4, new Edge(-1));

        g.addEdge(3, 2, new Edge(1));
        g.addEdge(2, 3, new Edge(1));

        g.addEdge(2, 4, new Edge(1));
        g.addEdge(4, 2, new Edge(1));

        g.addEdge(2, 1, new Edge(-2));

        g.addEdge(4, 1, new Edge(1));

        g.addEdge(4, 5, new Edge(2));
    }

    @Test
    void test1() {
        Graph<Integer, Edge> g = new AdjacencyListGraph<>();
        init(g);
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void test2() {
        Graph<Integer, Edge> g = new AdjacencyMatrixGraph<>();
        init(g);
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a = new ShortestPathInGraph<>();
        var res = a.calculate(g, 3, new IntWeight(0));
        int[] ans = {0, -2, 0, 0, -1, 1};
        for (int i = 1; i <= 5; ++i) {
            assertEquals(ans[i], res.get(i).weight);
        }
    }

    @Test
    void test3() {
        Graph<Integer, Edge> g = new IncidenceMatrixGraph<>();
        init(g);
        ShortestPathInGraph<Graph<Integer, Edge>, Integer, Edge, IntWeight> a = new ShortestPathInGraph<>();
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
            if (this.weight < o.weight)
                return -1;
            if (this.weight.equals(o.weight))
                return 0;
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
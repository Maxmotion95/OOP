package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.lylova.graph.AdjacencyListGraph;
import ru.nsu.fit.lylova.graph.AdjacencyMatrixGraph;
import ru.nsu.fit.lylova.graph.Graph;
import ru.nsu.fit.lylova.graph.IncidenceMatrixGraph;


class GraphTest {

    void test(Graph<Integer, String> g) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/test/GraphTestSource/graph1.txt"));

        while (sc.hasNext()) {
            String type = sc.next();
            if (type.equals("removeVertex")) {
                int v = sc.nextInt();
                boolean res = sc.nextBoolean();
                assertEquals(res, g.removeVertex(v));
            }
            if (type.equals("addVertex")) {
                int v = sc.nextInt();
                boolean res = sc.nextBoolean();
                assertEquals(res, g.addVertex(v));
            }
            if (type.equals("addEdge")) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                String val = sc.next();
                boolean res = sc.nextBoolean();
                assertEquals(res, g.addEdge(u, v, val));
            }
            if (type.equals("removeEdge")) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                boolean res = sc.nextBoolean();
                assertEquals(res, g.removeEdge(u, v));
            }
            if (type.equals("arrayVertexes")) {
                ArrayList<Integer> vertexes = new ArrayList<>();
                int n = sc.nextInt();
                for (int i = 0; i < n; ++i) {
                    vertexes.add(sc.nextInt());
                }
                assertEquals(vertexes, g.arrayVertexes());
            }
        }

        assertThrows(Exception.class, () -> {
            g.getEdge(0, 6);
        });
        assertThrows(Exception.class, () -> {
            g.getEdge(0, 1);
        });
        String x;
        try {
            x = g.getEdge(0, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("0->0", x);

        for (int i = 0; i < 5; ++i) {
            assertTrue(g.removeVertex(i));
        }
    }

    @Test
    void incidenceMatrixTest() throws FileNotFoundException {
        Graph<Integer, String> g = new IncidenceMatrixGraph<>();
        test(g);
    }

    @Test
    void adjacencyMatrixTest() throws FileNotFoundException {
        Graph<Integer, String> g = new AdjacencyMatrixGraph<>();
        test(g);
    }

    @Test
    void adjacencyListTest() throws FileNotFoundException {
        Graph<Integer, String> g = new AdjacencyListGraph<>();
        test(g);
    }
}
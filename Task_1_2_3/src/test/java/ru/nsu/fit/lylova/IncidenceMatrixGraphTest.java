package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncidenceMatrixGraphTest {

    @Test
    void test() {
        Graph<Integer, String> g = new IncidenceMatrixGraph<>();
        ArrayList<Integer> vertexes = new ArrayList<>();

        assertFalse(g.removeVertex(0));
        assertFalse(g.removeVertex(10));
        for (int i = 0; i < 5; ++i) {
            assertTrue(g.addVertex(i));
            vertexes.add(i);
        }
        assertFalse(g.addVertex(1));

        assertTrue(g.addEdge(1, 2, "1->2"));
        assertTrue(g.addEdge(0, 4, "0->4"));
        assertFalse(g.addEdge(0, 5, "gg"));
        assertFalse(g.addEdge(6, 2, "gg"));
        assertFalse(g.addEdge(6, 6, "gg"));
        assertTrue(g.addEdge(0, 0, "0->0"));

        assertFalse(g.removeEdge(6, 6));
        assertFalse(g.removeEdge(6, 2));
        assertFalse(g.removeEdge(2, 1));
        assertTrue(g.removeEdge(1, 2));

        assertTrue(g.removeVertex(4));
        assertFalse(g.removeEdge(0, 4));
        assertTrue(g.addVertex(4));
        assertFalse(g.removeEdge(0, 4));

        assertEquals(vertexes, g.arrayVertexes());

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
}
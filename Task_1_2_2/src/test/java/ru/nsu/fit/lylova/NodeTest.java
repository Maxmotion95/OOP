package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


class NodeTest {

    @Test
    void testToString() {
        Node<String> node = new Node<>("hdghjgasdiwe");
        assertEquals("hdghjgasdiwe", node.toString());
        node = new Node<>("1234567890");
        assertEquals("1234567890", node.toString());
        Node<Integer> node1 = new Node<>(63527);
        assertEquals("63527", node1.toString());
    }

    @Test
    void testConstructors() {
        Node<Integer> node = new Node<>();
        assertNull(node.getParent());
        assertNull(node.getValue());
        assertNotEquals(null, node.getChildren());
        assertEquals(0, node.getChildren().size());

        node = new Node<>(1123);
        assertNull(node.getParent());
        assertEquals(1123, node.getValue());
        assertNotEquals(null, node.getChildren());
        assertEquals(0, node.childrenCount());

        Node<Integer> node1 = new Node<>(node, 1342);
        assertEquals(node, node1.getParent());
        assertEquals(1342, node1.getValue());
        assertNotEquals(null, node1.getChildren());
        assertEquals(0, node1.childrenCount());

        node1 = new Node<>(node);
        assertEquals(node, node1.getParent());
        assertNull(node1.getValue());
        assertNotEquals(null, node1.getChildren());
        assertEquals(0, node1.childrenCount());
    }

    @Test
    void testAddChild() {
        Random random = new Random();
        Node<Integer> node = new Node<>(random.nextInt());
        int[] arr = new int[100];
        for (int i = 0; i < 100; ++i) {
            arr[i] = random.nextInt();
            Node<Integer> newNode = new Node<>(arr[i]);
            node.addChild(newNode);
            assertEquals(newNode, node.getChildren().get(i));
        }
        assertEquals(100, node.childrenCount());
        for (int i = 0; i < 100; ++i) {
            assertEquals(node, node.getChildren().get(i).getParent());
        }
    }

    @RepeatedTest(3)
    void testCntChild() {
        Random random = new Random();
        Node<Integer> node = new Node<>(random.nextInt());
        int[] arr = new int[100];
        for (int i = 0; i < 100; ++i) {
            arr[i] = random.nextInt();
            Node<Integer> newNode = new Node<>(arr[i]);
            node.addChild(newNode);
            assertEquals(i + 1, node.childrenCount());
        }
    }
}
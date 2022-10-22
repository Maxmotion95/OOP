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
        assertNull(node.parent);
        assertNull(node.value);
        assertNotEquals(null, node.children);
        assertEquals(0, node.children.size());

        node = new Node<>(1123);
        assertNull(node.parent);
        assertEquals(1123, node.value);
        assertNotEquals(null, node.children);
        assertEquals(0, node.children.size());

        Node<Integer> node1 = new Node<>(node, 1342);
        assertEquals(node, node1.parent);
        assertEquals(1342, node1.value);
        assertNotEquals(null, node1.children);
        assertEquals(0, node1.children.size());

        node1 = new Node<>(node);
        assertEquals(node, node1.parent);
        assertNull(node1.value);
        assertNotEquals(null, node1.children);
        assertEquals(0, node1.children.size());
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
            assertEquals(newNode, node.children.get(i));
        }
        assertEquals(100, node.children.size());
        for (int i = 0; i < 100; ++i) {
            assertEquals(node, node.children.get(i).parent);
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
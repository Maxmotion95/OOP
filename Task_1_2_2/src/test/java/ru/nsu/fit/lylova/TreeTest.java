package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void test1() {
        Tree<Integer> tree = new Tree<>();
        assertEquals(0, tree.size());
        int[] arr = new int[10];
        Random random = new Random();
        assertTrue(tree.isEmpty());
        for (int i = 0; i < 10; ++i) {
            arr[i] = random.nextInt();
            tree.add(arr[i]);
            assertEquals(i + 1, tree.size());
            assertFalse(tree.isEmpty());
        }
        int i = 0;
        for (var val : tree) {
            assertEquals(arr[i], val);
            ++i;
        }
        for (i = 0; i < 10; ++i) {
            assertFalse(tree.isEmpty());
            tree.remove(arr[i]);
            assertEquals(9 - i, tree.size());
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    void test2() {
        Tree<Integer> tree = new Tree<>();
        tree.add(123);
        tree.add(321);
        Node<Integer> v1 = tree.addVertexByValue(1), v2 = tree.addVertexByValue(2);
        tree.addVertexByParentAndValue(v1, 0);
        tree.addVertexByParentAndValue(v2, 723);
        tree.addVertexByParentAndValue(v1, 68326);
//                    Tree image
//                 _______root_______
//                 /    \    \      \
//               123   321  1(v1)  2(v2)
//                          /  \      \
//                         0  68326   723
        int[] arrBFS = new int[]{123, 321, 1, 2, 0, 68326, 723};
        int[] arrDFS = new int[]{123, 321, 1, 0, 68326, 2, 723};
        Iterator<Node<Integer>> bfs = tree.new TreeBfsIterator();
        int i = 0;
        while (bfs.hasNext()) {
            Node<Integer> node = bfs.next();
            assertEquals(arrBFS[i], node.value);
            ++i;
        }
        Iterator<Node<Integer>> dfs = tree.new TreeDfsIterator();
        i = 0;
        while (dfs.hasNext()) {
            Node<Integer> node = dfs.next();
            assertEquals(arrDFS[i], node.value);
            ++i;
        }
        for (i = 0; i < arrBFS.length; ++i) {
            assertTrue(tree.contains(arrBFS[i]));
            assertTrue(tree.contains(arrBFS[i]));
        }
        assertFalse(tree.contains(342134));
        assertFalse(tree.contains(3));
        assertFalse(tree.contains(320));
        var arr = tree.toArray();
        assertEquals(arrDFS.length, arr.length);
        for (i = 0; i < arrDFS.length; ++i) {
            assertEquals(arrDFS[i], arr[i]);
        }
        ArrayList<Integer> collection = new ArrayList<>();
        for (i = 0; i < arrDFS.length; ++i){
            collection.add(arrDFS[i]);
        }
        assertTrue(tree.containsAll(collection));
        collection.remove(1);
        assertTrue(tree.containsAll(collection));
        collection.add(21334);
        assertFalse(tree.containsAll(collection));
    }

    @Test
    void test3() {
        Tree<Integer> tree = new Tree<>();
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(34);
        arr.add(244);
        Node<Integer> v = tree.addVertexByValue(324);
        tree.addVertexByParentAndValue(v, 5);
        tree.addAll(arr);
        assertEquals("[324, 5, 1, 34, 244]", Arrays.toString(tree.toArray()));
    }

}
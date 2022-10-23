package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;


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
        Node<Integer> v1 = tree.addVertexByValue(1);
        Node<Integer> v2 = tree.addVertexByValue(2);
        tree.addVertexByParentAndValue(v1, 0);
        tree.addVertexByParentAndValue(v2, 723);
        tree.addVertexByParentAndValue(v1, 68326);
        //                    Tree image
        //                 _______root_______
        //                 /    \    \      \
        //               123   321  1(v1)  2(v2)
        //                          /  \      \
        //                         0  68326   723
        int[] arrBfs = new int[]{123, 321, 1, 2, 0, 68326, 723};
        int[] arrDfs = new int[]{123, 321, 1, 0, 68326, 2, 723};
        Iterator<Node<Integer>> bfs = tree.new TreeBfsIterator();
        int i = 0;
        while (bfs.hasNext()) {
            Node<Integer> node = bfs.next();
            assertEquals(arrBfs[i], node.getValue());
            ++i;
        }
        assertThrows(NoSuchElementException.class, bfs::next);
        Iterator<Node<Integer>> dfs = tree.new TreeDfsIterator();
        i = 0;
        while (dfs.hasNext()) {
            Node<Integer> node = dfs.next();
            assertEquals(arrDfs[i], node.getValue());
            ++i;
        }
        assertThrows(NoSuchElementException.class, dfs::next);
        for (i = 0; i < arrBfs.length; ++i) {
            assertTrue(tree.contains(arrBfs[i]));
            assertTrue(tree.contains(arrBfs[i]));
        }
        assertFalse(tree.contains(342134));
        assertFalse(tree.contains(3));
        assertFalse(tree.contains(320));
        var arr = tree.toArray();
        assertEquals(arrDfs.length, arr.length);
        for (i = 0; i < arrDfs.length; ++i) {
            assertEquals(arrDfs[i], arr[i]);
        }
        ArrayList<Integer> collection = new ArrayList<>();
        for (i = 0; i < arrDfs.length; ++i) {
            collection.add(arrDfs[i]);
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
        assertEquals(5, tree.size());
        assertTrue(tree.removeAll(arr));
        assertEquals(2, tree.size());
        assertEquals("[324, 5]", Arrays.toString(tree.toArray()));
        assertFalse(tree.removeAll(arr));
        tree.addAll(arr);
        arr.remove(2);
        tree.retainAll(arr);
        assertEquals(2, tree.size());
        assertEquals("[1, 34]", Arrays.toString(tree.toArray()));
        tree.clear();
        assertEquals(0, tree.size());
        assertEquals("[]", Arrays.toString(tree.toArray()));
    }

    @Test
    void testConcurrentModificationException() {
        Tree<Integer> tree = new Tree<>();
        tree.add(123);
        tree.add(321);
        Node<Integer> v1 = tree.addVertexByValue(1);
        Node<Integer> v2 = tree.addVertexByValue(2);
        tree.addVertexByParentAndValue(v1, 0);
        tree.addVertexByParentAndValue(v2, 723);
        tree.addVertexByParentAndValue(v1, 68326);
        Iterator<Node<Integer>> iterator = tree.new TreeBfsIterator();
        iterator.next();
        assertFalse(tree.remove(324));
        assertTrue(tree.remove(68326));
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testRemoveAll(){
        Tree<Integer> tree = new Tree<>();
        tree.add(123);
        tree.add(321);
        Node<Integer> v1 = tree.addVertexByValue(1);
        Node<Integer> v2 = tree.addVertexByValue(2);
        tree.addVertexByParentAndValue(v1, 0);
        tree.addVertexByParentAndValue(v2, 723);
        tree.addVertexByParentAndValue(v1, 68326);
        //                    Tree image
        //                 _______root_______
        //                 /    \    \      \
        //               123   321  1(v1)  2(v2)
        //                          /  \      \
        //                         0  68326   723
        ArrayList<Integer> toDel = new ArrayList<>();
        toDel.add(1);
        toDel.add(3);
        toDel.add(123);
        assertFalse(tree.removeAll(toDel));
        toDel.remove(1);
        assertTrue(tree.removeAll(toDel));
        assertFalse(tree.removeAll(toDel));
        toDel = new ArrayList<>();
        toDel.add(0);
        toDel.add(0);
        toDel.add(321);
        assertFalse(tree.removeAll(toDel));
        tree.add(0);
        assertTrue(tree.removeAll(toDel));
        assertFalse(tree.removeAll(toDel));
    }
}
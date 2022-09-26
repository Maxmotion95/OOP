package ru.nsu.fit.lylova;

import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StackTest {

    @RepeatedTest(10)
    void testPushPopStack() throws Exception {
        Random random = new Random();
        int n = 100 + random.nextInt(100);
        int[] all = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; ++i) {
            all[i] = random.nextInt();
            st.push(all[i]);
        }
        for (int i = 0; i < n; ++i) {
            assertEquals(st.pop(), all[n - i - 1]);
        }
    }

    @Test
    void testStack() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(1);
        assertEquals(st.pop(), 1);
        st.push(2);
        assertEquals(st.pop(), 2);

        st.push(123);
        st.push(8347);
        st.push(7266);
        Stack<Integer> x = st.popStack(2);
        assertEquals(x.pop(), 7266);
        assertEquals(x.pop(), 8347);
        assertEquals(st.count(), 1);
        assertEquals(st.pop(), 123);

        st.push(67823);
        st.push(89534);
        x = new Stack<>();
        x.push(793587);
        x.push(8346);
        x.push(89723);
        st.pushStack(x);
        int[] all = {67823, 89534, 793587, 8346, 89723};
        for (int i = all.length - 1; i >= 0; --i) {
            assertEquals(all[i], st.pop());
        }
    }

    @Test
    void testToString() {
        Stack<String> st = new Stack<>();
        String[] text = {"Kolobok", "hanged", "himself.", "Buratino", "drowned."};
        for (String s : text) {
            st.push(s);
        }
        assertEquals(st.toString(), "{Kolobok, hanged, himself., Buratino, drowned.}");
    }
}
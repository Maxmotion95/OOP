package ru.nsu.fit.lylova;

/**
 * Data structure stack with implemented functions push, push Stack, clap, clap stack, count.
 *
 * @param <T> type of stack elements
 */
public class Stack<T> {
    private int capacity;
    private int top = -1;
    private T[] array;

    /**
     * Creates an empty stack.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        this.capacity = 1;
        this.array = (T[]) new Object[this.capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] tmp = (T[]) new Object[this.capacity * 2];
        System.arraycopy(this.array, 0, tmp, 0, this.capacity);
        this.capacity *= 2;
        this.array = tmp;
    }

    /**
     * Adds this element to the top of the stack.
     *
     * @param element The element to add to the stack
     */
    public void push(T element) {
        if (this.top + 1 == this.capacity) {
            this.resize();
        }
        this.top++;
        this.array[this.top] = element;
    }


    /**
     * Adds a stack to the stack at the same time.
     * The order of elements in the added stack does not change.
     *
     * @param stack The stack being added to this stack
     */
    public void pushStack(Stack<T> stack) {
        for (int i = 0; i < stack.count(); ++i) {
            this.push(stack.get(i));
        }
    }

    /**
     * Throws the top element out of the stack, while returning its value.
     *
     * @return The previous top element of the stack
     * @throws Exception if stack is empty
     */
    public T pop() throws Exception {
        if (this.top == -1) {
            // TO DO
            // throw exception
            // catch error ????
            throw new Exception("stack is empty, cannot do pop");
        }
        return this.array[this.top--];
    }

    /**
     * Throws the required number of elements from the stack
     * Return a stack that contain these elements.
     *
     * @param count Number of top stack elements to be thrown out of the stack
     * @return A stack consisting of count of the top elements of the stack.
     *         The order of the elements in the stack is the same as in the original
     * @throws Exception if count is negative or there are not enough elements in the stack
     */
    public Stack<T> popStack(int count) throws Exception {
        if (count < 0) {
            // TO DO
            // throw exception
            // catch error ????
            throw new Exception("invalid count of elements: must be non-negative");
        }
        if (this.count() < count) {
            // TO DO
            // throw exception
            // catch error ????
            throw new Exception("the number of stack elements is less than count");
        }
        Stack<T> res = new Stack<>();
        for (int i = this.top - count + 1; i <= this.top; ++i) {
            res.push(this.get(i));
        }
        this.top -= count;
        return res;
    }


    /**
     * Returns Number of elements in the stack.
     *
     * @return Number of elements in the stack
     */
    public int count() {
        return this.top + 1;
    }

    private T get(int id) {
        return this.array[id];
    }

    /**
     * Returns a string representation of the stack.
     *
     * @return A string of the form {element1, element2, ..., elementN},
     *         where elementN is the top element of the stack,
     *         and element1 is the lowest element of the stack
     * To get a string representation of an element, the toString() function is used
     */
    @Override
    public String toString() {
        StringBuilder resBuilder = new StringBuilder("{");
        for (int i = 0; i < this.count(); ++i) {
            resBuilder.append(this.get(i).toString());
            if (i + 1 != this.count()) {
                resBuilder.append(", ");
            }
        }
        String res = resBuilder.toString();
        res += "}";
        return res;
    }
}

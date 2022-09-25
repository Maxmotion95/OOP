package ru.nsu.fit.lylova;

public class Stack<T> {
    private int capacity;
    private int top = -1;
    private T[] array;

    @SuppressWarnings("unchecked")
    public Stack() {
        this.capacity = 1;
        this.array = (T[]) new Object[this.capacity];
    }

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        this.capacity = capacity;
        this.array = (T[]) new Object[this.capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] tmp = (T[]) new Object[this.capacity * 2];
        if (this.capacity >= 0)
            System.arraycopy(this.array, 0, tmp, 0, this.capacity);
        this.capacity *= 2;
        this.array = tmp;
    }

    public void push(T element) {
        if (this.top + 1 == this.capacity) {
            this.resize();
        }
        this.top++;
        this.array[this.top] = element;
    }


    public void pushStack(Stack<T> stack) {
        for (int i = 0; i < stack.count(); ++i) {
            this.push(stack.get(i));
        }
    }

    public T pop() throws Exception {
        if (this.top == -1) {
            // TO DO
            // throw exception
            // catch error ????
            throw new Exception();
        }
        return this.array[this.top--];
    }

    public Stack<T> popStack(int count) throws Exception {
        if (this.count() < count) {
            // TO DO
            // throw exception
            // catch error ????
            throw new Exception();
        }
        Stack<T> res = new Stack<>(count);
        for (int i = this.top - count + 1; i <= this.top; ++i) {
            res.push(this.get(i));
        }
        this.top -= count;
        return res;
    }

    public int count() {
        return this.top + 1;
    }

    private T get(int id) {
        return this.array[id];
    }

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

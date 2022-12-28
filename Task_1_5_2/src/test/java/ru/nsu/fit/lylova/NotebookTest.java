package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class NotebookTest {
    @Test
    void test() {
        Notebook notebook = new Notebook();
        assertEquals("", notebook.showAll());
        assertEquals("[]", notebook.getDataInJson());
        notebook.addRecord(new NotebookRecord("123", "Super content"));
        assertNotEquals("", notebook.showAll());
        assertNotEquals("[]", notebook.getDataInJson());
    }
}
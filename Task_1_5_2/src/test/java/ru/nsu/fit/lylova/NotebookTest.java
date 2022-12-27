package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
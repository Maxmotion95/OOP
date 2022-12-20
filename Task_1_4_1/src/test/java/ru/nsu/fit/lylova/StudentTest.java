package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {
    @Test
    void test() {
        Student student = new Student();
        student.setName("name");
        student.setGroup(19215);
        student.setSurname("surname");
        student.setPatronymic("patronymic");

        assertEquals(19215, student.getGroup());
        assertEquals("surname n.p.", student.toString());
    }
}
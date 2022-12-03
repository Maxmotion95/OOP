package ru.nsu.fit.lylova;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class GradeBookTest {

    @Test
    void test() throws IOException {
        File initialFile = new File("src/test/resources/myGrades.json").getAbsoluteFile();
        JSONTokener tokener = new JSONTokener(new FileReader(initialFile, StandardCharsets.UTF_8));
        JSONObject root = new JSONObject(tokener);

        GradeBook gradebook = new GradeBook(root);
        assertEquals("4,8", String.format("%.1f", gradebook.getAverageGrade()));
        assertTrue(gradebook.diplomaWithHonours());
//        assertTrue(false);

//        System.out.println(gradebook.getAverageGrade());
    }

}
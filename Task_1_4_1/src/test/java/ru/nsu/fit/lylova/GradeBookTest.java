package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.nsu.fit.lylova.GradeBook.loadGradeBookFromJson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

class GradeBookTest {

    @Test
    void testWithMyGradeBookData() throws IOException {
        File initialFile = new File("src/test/resources/myGrades.json").getAbsoluteFile();
        JSONTokener tokener = new JSONTokener(new FileReader(initialFile, StandardCharsets.UTF_8));
        JSONObject root = new JSONObject(tokener);

        GradeBook gradebook = loadGradeBookFromJson(root);
        assertEquals("4.8", String.format(Locale.US, "%.1f", gradebook.getAverageGrade()));
        assertFalse(gradebook.diplomaWithHonours());

        System.out.println(gradebook);

        assertEquals(
                2,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        0,
                        Subject.ExamType.EXAM
                )
        );
        assertEquals(
                4,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        0,
                        Subject.ExamType.CREDIT
                )
        );
        assertEquals(
                0,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        0,
                        Subject.ExamType.DEFENSE_FQW
                )
        );
        assertEquals(
                4,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        0,
                        Subject.ExamType.DIF_CREDIT
                )
        );
        assertEquals(
                0,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        0,
                        Subject.ExamType.PROTECTION_OF_THE_PRACTICE_REPORT
                )
        );

        assertEquals(
                3,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        1,
                        Subject.ExamType.EXAM
                )
        );
        assertEquals(
                3,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        1,
                        Subject.ExamType.CREDIT
                )
        );
        assertEquals(
                0,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        1,
                        Subject.ExamType.DEFENSE_FQW
                )
        );
        assertEquals(
                3,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        1,
                        Subject.ExamType.DIF_CREDIT
                )
        );
        assertEquals(
                0,
                gradebook.getCountOfSubjectWithExamTypeInSemester(
                        1,
                        Subject.ExamType.PROTECTION_OF_THE_PRACTICE_REPORT
                )
        );

        for (int i = 0; i < gradebook.getSemesters().size(); ++i) {
            assertFalse(gradebook.isIncreasedScholarshipInSemester(i));
        }
    }

    @Test
    void testGetAverageGrade() {
        Student owner = new Student("Ivan", "Ivanov", "Ivanovich", 23456);
        GradeBook gradeBook = new GradeBook(owner, 753232);

        assertEquals(23456, gradeBook.getStudent().getGroup());
        assertEquals(753232, gradeBook.getGradeBookNumber());
        assertEquals("Ivan", gradeBook.getStudent().getName());
        assertEquals("Ivanov", gradeBook.getStudent().getSurname());
        assertEquals("Ivanovich", gradeBook.getStudent().getPatronymic());

        gradeBook.addSemester();
        Subject s = new Subject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        s.setExamDate(LocalDate.parse("06.12.2022", formatter));
        s.setExamType(Subject.ExamType.EXAM);
        s.setExamGrade(2);
        s.setSubjectName("Subject 1");
        gradeBook.addSubjectToSemester(s, 0);
        assertEquals(2, gradeBook.getAverageGrade());
        assertFalse(gradeBook.isIncreasedScholarshipInSemester(0));
        assertFalse(gradeBook.diplomaWithHonours());
    }
}
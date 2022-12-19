package ru.nsu.fit.lylova;

import java.time.LocalDate;
import java.util.List;

public class Subject {
    private String subjectName;
    private LocalDate examDate;
    private List<Teacher> teachers;
    private ExamType examType;

    ;
    private int examGrade;

    Subject() {
    }

    Subject(String subjectName,
            LocalDate examDate,
            List<Teacher> teachers,
            ExamType examType,
            int examGrade) {
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.teachers = teachers;
        this.examType = examType;
        this.examGrade = examGrade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
        this.examGrade = 0;
    }

    public int getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(int examGrade) {
        this.examGrade = examGrade;
    }

    public enum ExamType {
        EXAM,
        DIF_CREDIT,
        CREDIT,
        DEFENSE_FQW,
        PROTECTION_OF_THE_PRACTICE_REPORT
    }
}

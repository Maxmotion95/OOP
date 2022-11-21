package ru.nsu.fit.lylova;

import java.util.Date;
import java.util.List;

public class Subject {
    private String subjectName;
    private Date examDate;
    private List<Teacher> teachers;
    public enum ExamType {Exam, DifCredit, Credit};
    private ExamType examType;
    private int examGrade;

    Subject() {
    }

    Subject(String subjectName,
            Date examDate,
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

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
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
    }

    public int getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(int examGrade) {
        this.examGrade = examGrade;
    }
}

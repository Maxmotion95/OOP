package ru.nsu.fit.lylova;

public class Gradebook {
    private Student student;
    private int gradebookNumber;
    private final Semester[] semesters = new Semester[8];

    Gradebook(){
    }

    Gradebook(Student student, int gradebookNumber) {
        this.student = student;
        this.gradebookNumber = gradebookNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getGradebookNumber() {
        return gradebookNumber;
    }

    public void setGradebookNumber(int gradebookNumber) {
        this.gradebookNumber = gradebookNumber;
    }

    public Semester[] getSemesters() {
        return semesters;
    }
}

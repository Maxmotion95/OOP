package ru.nsu.fit.lylova;

import java.util.ArrayList;
import java.util.List;

public class Semester {
    private List<Subject> subjects;

    Semester(){
        subjects = new ArrayList<>();
    }

    Semester(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public int getCountOfSubjects(){
        return subjects.size();
    }

    public int getSumOfGrades(){
        return subjects.stream().mapToInt(Subject::getExamGrade).sum();
    }
}

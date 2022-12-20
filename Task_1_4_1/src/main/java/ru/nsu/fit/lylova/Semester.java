package ru.nsu.fit.lylova;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Semester {
    private List<Subject> subjects;

    Semester() {
        subjects = new ArrayList<>();
    }

    Semester(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int[] widthOfTableColumn = new int[]{25, 11, 1, 10, 30};
        int widthOfTable = Arrays.stream(widthOfTableColumn).sum() + (widthOfTableColumn.length) * 3 + 1;
        String delimiterLine = "-".repeat(widthOfTable) + '\n';
        if (subjects.isEmpty()) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (Subject subject : subjects) {
            result.append(delimiterLine);

            String[] valuesOfTableRow = new String[]{
                    subject.getSubjectName(),
                    subject.getExamType().toString(),
                    Integer.toString(subject.getExamGrade()),
                    subject.getExamDate().format(formatter),
                    subject.getTeachers().stream().map(Person::toString).collect(Collectors.joining(", "))
            };

            for (int i = 0; i < widthOfTableColumn.length; ++i) {
                result.append("| ");
                String shortValueOfRow = valuesOfTableRow[i].substring(0, Math.min(valuesOfTableRow[i].length(), widthOfTableColumn[i]));
                shortValueOfRow += " ".repeat(widthOfTableColumn[i] - shortValueOfRow.length());
                result.append(shortValueOfRow);
                result.append(" ");
            }
            result.append("|\n");
        }
        result.append(delimiterLine);
        return result.toString();
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

    public int getCountOfSubjects() {
        return subjects.size();
    }

    public int getSumOfGrades() {
        return subjects.stream().mapToInt(Subject::getExamGrade).sum();
    }

    public int getCountOfSubjectWithExamType(Subject.ExamType examType) {
        return (int) subjects.stream().filter(subject -> subject.getExamType() == examType).count();
    }
}

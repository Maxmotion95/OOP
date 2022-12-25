package ru.nsu.fit.lylova;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Semester to collect subjects of one semester.
 */
public class Semester {
    private List<Subject> subjects;

    /**
     * Constructs semester that does not contain subjects.
     */
    Semester() {
        subjects = new ArrayList<>();
    }

    /**
     * Returns string that contains table view of all subject in this semester.
     *
     * @return string that contains table view of all subject in this semester
     */
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

    /**
     * Returns list of all subjects in this semester.
     *
     * @return list of all subjects
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Changes list of subjects to specified one.
     *
     * @param subjects new list of subjects
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * Adds specified subject to this semester.
     *
     * @param subject subject to be added in this semester
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * Returns count of subjects in this semester.
     *
     * @return count of subjects
     */
    public int getCountOfSubjects() {
        return subjects.size();
    }

    /**
     * Returns sum of grades in this semester.
     *
     * @return sum of grades
     */
    public int getSumOfGrades() {
        return subjects.stream().mapToInt(Subject::getExamGrade).sum();
    }

    /**
     * Returns count of subjects with specified exam type in this semester.
     *
     * @param examType exam type
     * @return count of subjects with specified exam type
     */
    public int getCountOfSubjectWithExamType(Subject.ExamType examType) {
        return (int) subjects.stream().filter(subject -> subject.getExamType() == examType).count();
    }
}

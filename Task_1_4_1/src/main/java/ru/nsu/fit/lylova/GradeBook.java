package ru.nsu.fit.lylova;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    private final List<Semester> semesters = new ArrayList<>();
    private Student student;
    private int gradeBookNumber;

    public GradeBook() {
    }

    public GradeBook(Student student, int gradebookNumber) {
        this.student = student;
        this.gradeBookNumber = gradebookNumber;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("GradeBook #");
        result.append(gradeBookNumber);
        result.append("\nStudent: ");
        result.append(student.getSurname())
                .append(" ")
                .append(student.getName())
                .append(" ")
                .append(student.getPatronymic());
        result.append("\n");
        for (int i = 0; i < semesters.size(); ++i) {
            result.append("Semester ");
            result.append(i + 1);
            result.append("\n").append(semesters.get(i));
            result.append("\n");
        }
        return result.toString();
    }

    public void loadGradeBookFromJson(JSONObject gradeBook) {
        JSONObject jsonStudent = gradeBook.getJSONObject("student");
        student = new Student(
                jsonStudent.getString("name"),
                jsonStudent.getString("surname"),
                jsonStudent.getString("patronymic"),
                jsonStudent.getInt("group")
        );
        gradeBookNumber = gradeBook.getInt("grade_book_id");
        var semesters = gradeBook.getJSONArray("semesters");
        int semester_id = 0;
        for (var semester : semesters) {
            this.addSemester();
            for (var jsonSubject : (JSONArray) semester) {
                JSONObject subject = (JSONObject) jsonSubject;
                String type = subject.getString("form");
                Subject.ExamType type1 = Subject.ExamType.valueOf(type);

                ArrayList<Teacher> teachers = new ArrayList<>();
                for (var teacher : subject.getJSONArray("teachers")) {
                    JSONObject teacherJSONObject = (JSONObject) teacher;
                    teachers.add(new Teacher(
                            teacherJSONObject.getString("name"),
                            teacherJSONObject.getString("surname"),
                            teacherJSONObject.getString("patronymic")
                    ));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                this.addSubjectToSemester(new Subject(
                        subject.getString("subject"),
                        LocalDate.parse(subject.getString("date"), formatter),
                        teachers,
                        type1,
                        subject.getInt("grade")
                ), semester_id);
            }
            ++semester_id;
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getGradeBookNumber() {
        return gradeBookNumber;
    }

    public void setGradeBookNumber(int gradeBookNumber) {
        this.gradeBookNumber = gradeBookNumber;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void addSemester() {
        semesters.add(new Semester());
    }

    public void addSubjectToSemester(Subject subject, int semesterId) {
        semesters.get(semesterId).addSubject(subject);
    }

    public double getAverageGrade() {
        int subjectsCount = semesters.stream().mapToInt(Semester::getCountOfSubjects).sum();
        int gradesSum = semesters.stream().mapToInt(Semester::getSumOfGrades).sum();
        return (double) gradesSum / subjectsCount;
    }

    public boolean diplomaWithHonours() {
        int subjectsCount = semesters.stream().mapToInt(Semester::getCountOfSubjects).sum();
        int excellentGradesCount = semesters
                .stream()
                .mapToInt(s -> s.getSubjects()
                        .stream()
                        .mapToInt(subj -> subj
                                .getExamGrade() == 5 ? 1 : 0
                        ).sum()
                ).sum();

        // check that 75% grades is excellent
        // 3/4 <= excellentGradesCount / subjectsCount
        // <=> 3 * subjectsCount <= 4 * excellentGradesCount
        if (3 * subjectsCount > 4 * excellentGradesCount) {
            return false;
        }

        boolean noSatisfactoryGrade = semesters
                .stream()
                .allMatch(semester -> semester
                        .getSubjects()
                        .stream()
                        .allMatch(subject -> subject
                                .getExamGrade() > 3));

        if (!noSatisfactoryGrade) {
            return false;
        }

        return semesters
                .stream()
                .anyMatch(semester -> semester
                        .getSubjects()
                        .stream()
                        .anyMatch(subject -> subject.getExamType() == Subject.ExamType.DEFENSE_FQW
                                && subject.getExamGrade() == 5
                        ));
    }

    public boolean isIncreasedScholarshipInSemester(int semesterID) {
        if (semesterID <= 0 || semesterID - 1 >= semesters.size()) {
            return false;
        }
        return semesters
                .get(semesterID - 1)
                .getSubjects()
                .stream()
                .allMatch(subject -> subject
                        .getExamGrade() == 5);
    }

    public int getCountOfSubjectWithExamTypeInSemester(int semesterID, Subject.ExamType examType) {
        if (semesterID < 0 || semesterID >= semesters.size()) {
            return 0;
        }
        return semesters.get(semesterID).getCountOfSubjectWithExamType(examType);
    }
}

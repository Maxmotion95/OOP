package ru.nsu.fit.lylova;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class GradeBook {
    private final ArrayList<Semester> semesters = new ArrayList<>();
    private Student student;
    private int gradeBookNumber;

    GradeBook(JSONObject gradeBook) {
        JSONObject jsonStudent = (JSONObject) gradeBook.getJSONObject("student");
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
                var kek = (JSONObject) jsonSubject;
                var type = kek.getString("form");
                Subject.ExamType type1;
                switch (type) {
                    case "exam" :
                        type1 = Subject.ExamType.Exam;
                        break;
                    case "credit":
                        type1 = Subject.ExamType.Credit;
                        break;
                    case "dif credit" :
                        type1 = Subject.ExamType.DifCredit;
                        break;
                    case "defense FQW" :
                        type1 = Subject.ExamType.defenseFQW;
                        break;
                    default:
                        type1 = Subject.ExamType.ProtectionOfThePracticeReport;
                };
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (var teacher : kek.getJSONArray("teachers")) {
                    var kek2 = (JSONObject) teacher;
                    teachers.add(new Teacher(
                            kek2.getString("name"),
                            kek2.getString("surname"),
                            kek2.getString("patronymic")
                    ));
                }
                this.addSubjectToSemester(new Subject(
                        kek.getString("subject"),
                        kek.getString("date"),
                        teachers,
                        type1,
                        kek.getInt("grade")
                ), semester_id);
            }
            ++semester_id;
        }
    }

    GradeBook(Student student, int gradebookNumber) {
        this.student = student;
        this.gradeBookNumber = gradebookNumber;
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

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

    public void addSemester() {
        semesters.add(new Semester());
    }

    public void addSubjectToSemester(Subject subject, int semester_id) {
        semesters.get(semester_id).addSubject(subject);
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

        boolean noSat = semesters
                .stream()
                .allMatch(semester -> semester
                        .getSubjects()
                        .stream()
                        .allMatch(subject -> subject
                                .getExamGrade() > 3));

        if (!noSat) {
            return false;
        }

        return semesters
                .stream()
                .anyMatch(semester -> semester
                        .getSubjects()
                        .stream()
                        .anyMatch(subject -> subject.getExamType() == Subject.ExamType.defenseFQW && subject.getExamGrade() == 5));
    }

    boolean isIncreasedScholarshipInSemester(int semesterID) {
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
}

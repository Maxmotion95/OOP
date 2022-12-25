package ru.nsu.fit.lylova;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class grade book.
 */
public class GradeBook {
    private final List<Semester> semesters = new ArrayList<>();
    private Student student;
    private int gradeBookNumber;

    /**
     * Constructs an empty grade book.
     */
    public GradeBook() {
    }

    /**
     * Constructs grade book with specified owner and number of grade book.
     *
     * @param student         owner of this grade book
     * @param gradebookNumber grade book number
     */
    public GradeBook(Student student, int gradebookNumber) {
        this.student = student;
        this.gradeBookNumber = gradebookNumber;
    }

    /**
     * Returns string that contains table view of grade book data.
     *
     * @return string that contains table view of grade book data
     */
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

    /**
     * Initializes grade book with data from {@code gradeBook}.
     *
     * @param gradeBook json object that contain all data of grade book.
     */
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

    /**
     * Returns owner of this grade book.
     *
     * @return owner of this grade book
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Changes owner of this grade book to specified student.
     *
     * @param student new owner of this grade book.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Returns number of this grade book.
     *
     * @return number of this grade book.
     */
    public int getGradeBookNumber() {
        return gradeBookNumber;
    }

    /**
     * Changes number of this grade book to specified number.
     *
     * @param gradeBookNumber new number of this grade book
     */
    public void setGradeBookNumber(int gradeBookNumber) {
        this.gradeBookNumber = gradeBookNumber;
    }

    /**
     * Returns list of Semesters.
     *
     * @return list of Semesters
     */
    public List<Semester> getSemesters() {
        return semesters;
    }

    /**
     * Adds one semester to grade book.
     */
    public void addSemester() {
        semesters.add(new Semester());
    }

    /**
     * Adds specified subject to semester with number {@code semesterId}.
     * Semesters are numbered from 0.
     *
     * @param subject    subject to add
     * @param semesterId number of semester
     */
    public void addSubjectToSemester(Subject subject, int semesterId) {
        semesters.get(semesterId).addSubject(subject);
    }

    /**
     * Returns average grade of all subjects in grade book.
     *
     * @return average grade of all subjects in grade book
     */
    public double getAverageGrade() {
        int subjectsCount = semesters.stream().mapToInt(Semester::getCountOfSubjects).sum();
        int gradesSum = semesters.stream().mapToInt(Semester::getSumOfGrades).sum();
        return (double) gradesSum / subjectsCount;
    }

    /**
     * Checks whether a student can get a diploma with honors based on the grades received.
     * Requirements for a diploma with honors:
     * <ul>
     *     <li>75% of the grades in the diploma supplement (the last grade) – "excellent"</li>
     *     <li>There are no final grades "satisfactory" in the record book</li>
     *     <li>The qualification work is protected with "excellent"</li>
     * </ul>
     *
     * @return {@code true} if student will have diploma with honours
     */
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

    /**
     * Checks whether a student can have an increased scholarship
     * in semester with number {@code semesterID}.
     * Requirements for an increased scholarship:
     * <ul>
     *     <li>All grades in previous semester are excellent (5)</li>
     * </ul>
     * Semesters are numbered from 0.
     *
     * @param semesterID number of semester
     * @return {@code true} if student will have increased scholarship
     * in semester with number {@code semesterID}
     */
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

    /**
     * Returns count of subjects with exam type - {@code examType}
     * in semester with number {@code semesterID}.
     * Semesters are numbered from 0.
     *
     * @param semesterID number of semester
     * @param examType exam type
     * @return count of subjects with specified exam type in specified semester
     */
    public int getCountOfSubjectWithExamTypeInSemester(int semesterID, Subject.ExamType examType) {
        if (semesterID < 0 || semesterID >= semesters.size()) {
            return 0;
        }
        return semesters.get(semesterID).getCountOfSubjectWithExamType(examType);
    }
}

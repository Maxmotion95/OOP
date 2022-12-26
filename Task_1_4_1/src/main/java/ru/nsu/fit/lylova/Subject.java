package ru.nsu.fit.lylova;

import java.time.LocalDate;
import java.util.List;

/**
 * Class subject.
 */
public class Subject {
    private String subjectName;
    private LocalDate examDate;
    private List<Teacher> teachers;
    private ExamType examType;
    private int examGrade = 0;

    /**
     * Construct empty subject.
     */
    Subject() {
    }

    /**
     * Constructs subject with specified data.
     *
     * @param subjectName subject name
     * @param examDate    subject exam date
     * @param teachers    teachers of this subject
     * @param examType    subject exam type
     * @param examGrade   subject exam grade
     */
    Subject(String subjectName,
            LocalDate examDate,
            List<Teacher> teachers,
            ExamType examType,
            int examGrade) {
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.teachers = teachers;
        this.examType = examType;
        this.setExamGrade(examGrade);
    }

    /**
     * Returns name of this subject.
     *
     * @return subject's name
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Changes subject's name to the specified one.
     *
     * @param subjectName new subject's name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Returns exam date of this subject.
     *
     * @return subject's exam date
     */
    public LocalDate getExamDate() {
        return examDate;
    }

    /**
     * Changes subject's exam date to the specified one.
     *
     * @param examDate new subject's exam date
     */
    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    /**
     * Returns list of teachers of this subject.
     *
     * @return list of teachers
     */
    public List<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * Changes list of teachers to the specified one.
     *
     * @param teachers new list of teachers.
     */
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    /**
     * Returns exam type of this subject.
     *
     * @return subject's exam type
     */
    public ExamType getExamType() {
        return examType;
    }

    /**
     * Changes subject's exam type to the specified one.
     * Subject's exam grade changes to {@code 2}.
     *
     * @param examType new subject's exam type
     */
    public void setExamType(ExamType examType) {
        this.examType = examType;
        this.examGrade = 2;
    }

    /**
     * Returns exam grade of this subject.
     *
     * @return subject's exam grade
     */
    public int getExamGrade() {
        return examGrade;
    }

    /**
     * Changes exam grade of this subject to the specified one.
     * The new grade is validated according to the type of exam.
     * If the exam type is {@code CREDIT}, then the grade should be 2 or 5.
     * If the exam type is different from {@code CREDIT},
     * then the grade should be between 2 and 5.
     *
     * @param examGrade new subject's exam grade
     * @return {@code true} if subject's grade changed
     */
    public boolean setExamGrade(int examGrade) {
        if (examGrade > 5 || examGrade < 2) {
            return false;
        }
        if (examType == ExamType.CREDIT) {
            if (examGrade == 5 || examGrade == 2) {
                this.examGrade = examGrade;
                return true;
            }
            return false;
        }
        this.examGrade = examGrade;
        return true;
    }

    /**
     * Enum class with all exam types.
     */
    public enum ExamType {
        EXAM,
        DIF_CREDIT,
        CREDIT,
        DEFENSE_FQW,
        PROTECTION_OF_THE_PRACTICE_REPORT
    }
}

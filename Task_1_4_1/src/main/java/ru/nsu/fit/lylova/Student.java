package ru.nsu.fit.lylova;

/**
 * Class student.
 */
public class Student extends Person {
    private int group = 0;

    /**
     * Constructs empty student.
     * Student's name, surname, patronymic are set to {@code ""}.
     * Student's number of group is set to {@code 0}.
     */
    Student(){
        super();
    }

    /**
     * Constructs student with specified name, surname, patronymic and number of group.
     *
     * @param name student's name
     * @param surname student's surname
     * @param patronymic student's patronymic
     * @param group student's number of group
     */
    Student(String name, String surname, String patronymic, int group) {
        super(name, surname, patronymic);
        this.group = group;
    }

    /**
     * Returns number of group of this student.
     *
     * @return number of group
     */
    public int getGroup() {
        return group;
    }

    /**
     * Changes number of group to the specified one.
     *
     * @param group new number of group
     */
    public void setGroup(int group) {
        this.group = group;
    }
}

package ru.nsu.fit.lylova;

public class Student extends Person {
    private int group = 0;

    Student(){
    }

    Student(String name, String surname, String patronymic, int group) {
        super(name, surname, patronymic);
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}

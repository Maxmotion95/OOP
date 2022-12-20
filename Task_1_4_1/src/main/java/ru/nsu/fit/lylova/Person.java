package ru.nsu.fit.lylova;

public class Person {
    private String name = "";
    private String surname = "";
    private String patronymic = "";

    Person() {
    }

    Person(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        String shortName = new String();
        if (name.length() > 0) {
            shortName += name.charAt(0);
            shortName += '.';
        }
        String shortPatronymic = new String();
        if (patronymic.length() > 0) {
            shortPatronymic += patronymic.charAt(0);
            shortPatronymic += '.';
        }
        return surname + ' ' + shortName + shortPatronymic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

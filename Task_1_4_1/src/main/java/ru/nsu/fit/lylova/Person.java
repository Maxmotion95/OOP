package ru.nsu.fit.lylova;

public class Person {
    private String name = "";
    private String surname = "";
    private String patronymic = "";

    /**
     * Construct empty person.
     * Person's name, surname and patronymic are set to {@code ""}.
     */
    Person() {
    }

    /**
     * Constructs person with specified name, surname and patronymic.
     *
     * @param name       person's name
     * @param surname    person's surname
     * @param patronymic person's patronymic
     */
    Person(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    /**
     * Returns string representation of this person.
     *
     * @return string representation of this person
     */
    @Override
    public String toString() {
        String shortName = "";
        if (name.length() > 0) {
            shortName += name.charAt(0);
            shortName += '.';
        }
        String shortPatronymic = "";
        if (patronymic.length() > 0) {
            shortPatronymic += patronymic.charAt(0);
            shortPatronymic += '.';
        }
        return surname + ' ' + shortName + shortPatronymic;
    }

    /**
     * Returns name of this person.
     *
     * @return name of this person
     */
    public String getName() {
        return name;
    }

    /**
     * Changes name of this person to specified one.
     *
     * @param name new person's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns surname of this person.
     *
     * @return surname of this person
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Changes surname of this person to specified one.
     *
     * @param surname new person's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns patronymic of this person.
     *
     * @return patronymic of this person
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Changes patronymic of this person to specified one.
     *
     * @param patronymic new person's patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

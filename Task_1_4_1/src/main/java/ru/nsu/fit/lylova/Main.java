package ru.nsu.fit.lylova;

public class Main {
    public static void main(String[] args) {
        Student kek = new Student("aba", "kek", "patr", 1224);
        kek = new Student();
        System.out.println(kek.getName());
        System.out.println(kek.getSurname());
        System.out.println(kek.getPatronymic());
        System.out.println(kek.getGroup());
    }
}
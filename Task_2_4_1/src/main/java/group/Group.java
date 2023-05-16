package group;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

import java.util.ArrayList;
import java.util.List;


public class Group extends GroovyObjectSupport {
    private String groupNumber = "unspecified";
    private StudentList students = new StudentList();

    public String getGroupNumber() {
        return groupNumber;
    }

    public StudentList getStudents() {
        return students;
    }

    public void students(Closure<?> closure) {
        closure.setDelegate(students);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public static class StudentList {
        private final List<Student> list = new ArrayList<>();

        public void student(Closure<?> closure) {
            Student student = new Student();

            closure.setDelegate(student);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();

            list.add(student);
        }

        public List<Student> getList() {
            return list;
        }
    }
}

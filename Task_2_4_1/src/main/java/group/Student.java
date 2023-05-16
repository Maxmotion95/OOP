package group;

import groovy.lang.GroovyObjectSupport;

public class Student extends GroovyObjectSupport {
    private String name;
    private String repo;

    public String getName() {
        return name;
    }

    public  String getRepo() {
        return repo;
    }
}

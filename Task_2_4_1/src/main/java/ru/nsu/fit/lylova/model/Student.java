package ru.nsu.fit.lylova.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends GroovyConfigurable {
    private String id;
    private String fullName;
    private String repository;
}
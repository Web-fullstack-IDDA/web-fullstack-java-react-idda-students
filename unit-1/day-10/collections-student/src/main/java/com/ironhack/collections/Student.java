package com.ironhack.collections;

// Student extends Person, adding a grade field.
public class Student extends Person {

    private int grade;

    public Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    @Override
    public String toString() {
        return getName() + " (grade: " + grade + ")";
    }
}

package org.example.lab5.exception;

public class NullStudentException extends Exception {
    public NullStudentException() {
        super("No such student exists.");
    }
}
package org.example.lab5.exception;

public class NullCourseException extends Exception {
    public NullCourseException() {
        super("No such course exists");
    }
}
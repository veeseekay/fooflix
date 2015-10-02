package com.bar.fooflix.exceptions;


public class FooflixException extends RuntimeException {

    private ExceptionType type;

    public ExceptionType getType() {
        return type;
    }

    // method chaining
    public FooflixException setType(ExceptionType type) {
        this.type = type;
        return this;
    }

    public FooflixException(String message) {
        super(message);
    }

    public FooflixException(Throwable cause) {
        super(cause);
    }

    public FooflixException(String message, Throwable cause) {
        super(message, cause);
    }
}
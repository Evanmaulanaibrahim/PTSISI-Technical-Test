package com.evanmaulanaibrahim.backenddev.exception.classes;

public class RateLimitExceededException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -8800565442287420414L;

    public RateLimitExceededException(String message) {
        super(message);
    }
}
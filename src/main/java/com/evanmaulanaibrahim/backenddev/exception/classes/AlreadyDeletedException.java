package com.evanmaulanaibrahim.backenddev.exception.classes;

public class AlreadyDeletedException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -165091889894568911L;

    public AlreadyDeletedException(String message) {
        super(message);
    }
}


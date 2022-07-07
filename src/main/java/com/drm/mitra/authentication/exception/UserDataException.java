package com.drm.mitra.authentication.exception;

public class UserDataException extends Exception {

    private static final long serialVersionUID = -470180507998010368L;

    public UserDataException() {
        super();
    }

    public UserDataException(String message) {
        super(message);
    }

    public UserDataException(String s, UserDataException e) {
    }
}

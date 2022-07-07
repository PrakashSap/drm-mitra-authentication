package com.drm.mitra.authentication.exception;

public class UserNameNotEmptyException extends Exception{

    private static final long serialVersionUID = -9079454849611061074L;

    public UserNameNotEmptyException() {
        super();
    }

    public UserNameNotEmptyException(String message) {
        super(message);
    }

}

package com.drm.mitra.authentication.exception;

public class RoleNameNotEmptyException extends Exception{

    private static final long serialVersionUID = -9079454849611061074L;

    public RoleNameNotEmptyException() {
        super();
    }

    public RoleNameNotEmptyException(String message) {
        super(message);
    }

}

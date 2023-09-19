package org.Exceptions;

public class InvalidPasswordException extends UserRestException {
    public InvalidPasswordException(String s) {
        super(s);
    }
}

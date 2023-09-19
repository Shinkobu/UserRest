package org.Exceptions;

public class InvalidEmailException extends UserRestException {
    public InvalidEmailException(String s) {
        super(s);
    }
}

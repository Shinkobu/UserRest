package org.Exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String emailAddress) {
        super(emailAddress);
    }
}

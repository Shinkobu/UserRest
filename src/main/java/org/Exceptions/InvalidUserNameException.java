package org.Exceptions;

import org.User;

public class InvalidUserNameException extends UserRestException {
    public InvalidUserNameException(String s) {
        super(s);
    }
}

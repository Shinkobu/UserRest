package org.Exceptions;

import org.User;

public class EmailExistsException extends UserRestException {
    public EmailExistsException(String s) {
        super(s);
    }
}

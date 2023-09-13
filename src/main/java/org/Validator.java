package org;

import org.Exceptions.InvalidEmailException;
import org.Exceptions.InvalidUserNameException;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isNameValid(String name) throws InvalidUserNameException {

        if (regexChecker("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", name)) {
            return true;
        }
        throw new InvalidUserNameException("Mistake in user name: " + name);
    }

    public static boolean isEmailValid(String emailAddress) throws InvalidEmailException {

        if (regexChecker("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", emailAddress)) {
            return true;
        }
        throw new InvalidEmailException("Mistake in email address: " + emailAddress);
    }

    public static boolean isPasswordValid(String name) {
        return true;
    }

    private static boolean regexChecker(String regexPattern, String stringToCheck) {
        return (Pattern.compile(regexPattern)
                .matcher(stringToCheck)
                .matches());
    }
}

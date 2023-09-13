package org;

import org.Exceptions.InvalidEmailException;
import org.Exceptions.InvalidPasswordException;
import org.Exceptions.InvalidUserNameException;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isNameValid(String name) throws InvalidUserNameException {

        if (regexChecker("^[a-zA-Zа-яА-Я]+$", name)) {
            return true;
        }
        throw new InvalidUserNameException("Invalid user name: " + name);
    }

    public static boolean isEmailValid(String emailAddress) throws InvalidEmailException {

        if (regexChecker("^[a-zA-Z0-9_!#$%&*+/=?{|}.-]+@[a-zA-Z0-9.-]+$", emailAddress)) {
            return true;
        }
        throw new InvalidEmailException("Invalid email address: " + emailAddress);
    }

    public static boolean isPasswordValid(String password) throws InvalidPasswordException {

        if (regexChecker("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", password)) {
            return true;
        }
        throw new InvalidPasswordException ("Invalid password: " + password);
    }


    private static boolean regexChecker(String regexPattern, String stringToCheck) {
        return (Pattern.compile(regexPattern)
                .matcher(stringToCheck)
                .matches());
    }
}

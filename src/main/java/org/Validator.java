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
        throw new InvalidUserNameException("InvalidUserNameException: Invalid user name: " + name);
    }

    public static boolean isEmailValid(String emailAddress) throws InvalidEmailException {
        // должно содержать только буквы и быть не пустым.
        if (regexChecker("^[a-zA-Z0-9_!#$%&*+/=?{|}.-]+@[a-zA-Z0-9.-]+$", emailAddress)) {
            return true;
        }
        throw new InvalidEmailException("InvalidEmailException: Invalid email address: " + emailAddress);
    }

    public static boolean isPasswordValid(String password) throws InvalidPasswordException {
// должно быть не меньше 8 символов и содержать хотя бы одну букву верхнего регистра, одну букву нижнего регистра и одну цифру.
        if (regexChecker("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", password)) {
            return true;
        }
        throw new InvalidPasswordException ("InvalidPasswordException: Invalid password: " + password);
    }


    private static boolean regexChecker(String regexPattern, String stringToCheck) {
        return (Pattern.compile(regexPattern)
                .matcher(stringToCheck)
                .matches());
    }
}

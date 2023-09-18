package org;

import org.Exceptions.InvalidEmailException;
import org.Exceptions.InvalidPasswordException;
import org.Exceptions.InvalidUserNameException;

import java.util.regex.Pattern;

/**
 * Класс методов валидации
 */
public class Validator {

    /**
     * Сводный метод для валидации полей пользователя
     *
     * @param user - пользователь для проверки
     * @return - true, если валидно
     * @throws InvalidUserNameException - Недопустимое имя
     * @throws InvalidEmailException    - Некорректный email
     * @throws InvalidPasswordException - Недопустимый пароль
     */
    public static boolean validator(User user) throws
            InvalidUserNameException, InvalidEmailException, InvalidPasswordException {

        isNameValid(user.getName());
        isEmailValid(user.getEmail());
        isPasswordValid(user.getPassword());
        return true;
    }

    /**
     * Валидация имени пользователя
     * @param name - имя пользователя
     * @return - true, если валидно
     * @throws InvalidUserNameException - исключение, если не валидно
     */
    public static boolean isNameValid(String name) throws InvalidUserNameException {

        if (regexChecker("^[a-zA-Zа-яА-Я]+$", name)) {
            return true;
        }
        throw new InvalidUserNameException("InvalidUserNameException: Недопустимое имя пользователя: " + name);
    }

    /**
     * Валидация email
     * @param emailAddress - email для проверки
     * @return  - true, если валидно
     * @throws InvalidEmailException - исключение, если не валидно
     */
    public static boolean isEmailValid(String emailAddress) throws InvalidEmailException {
        if (regexChecker("^[a-zA-Z0-9_!#$%&*+/=?{|}.-]+@[a-zA-Z0-9.-]+$", emailAddress)) {
            return true;
        }
        throw new InvalidEmailException("InvalidEmailException: Неверный адрес email: " + emailAddress);
    }

    /**
     * Валидация пароля пользователя
     *
     * @param password - пароль для проверки
     * @return  - true, если валидно
     * @throws InvalidPasswordException - исключение, если не валидно
     */
    public static boolean isPasswordValid(String password) throws InvalidPasswordException {
/* должно быть не меньше 8 символов и содержать хотя бы одну букву верхнего регистра,
одну букву нижнего регистра и одну цифру. */
        if (regexChecker("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", password)) {
            return true;
        }
        throw new InvalidPasswordException("InvalidPasswordException: Недопустимый пароль: " + password);
    }

    /**
     * служебный метод для проверки соответствия строки регулярному выражению
     * @param regexPattern - комбинация регулярных выражений
     * @param stringToCheck - строка для проверки соответствия
     * @return  - true, если валидно
     */
    private static boolean regexChecker(String regexPattern, String stringToCheck) {
        return (Pattern.compile(regexPattern)
                .matcher(stringToCheck)
                .matches());
    }
}

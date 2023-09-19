package org.Exceptions;

/**
 * Класс - родитель всех специальных исключений пакета org.Exceptions
 */

public class UserRestException extends Exception{
    public UserRestException(String message) {
        super(message);
    }
}

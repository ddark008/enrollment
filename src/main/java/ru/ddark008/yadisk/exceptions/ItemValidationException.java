package ru.ddark008.yadisk.exceptions;

/**
 * Невалидная схема документа или входные данные не верны.
 */
public class ItemValidationException extends RuntimeException {
    public ItemValidationException(String id, String message) {
        super("Item " + id + " not valid: " + message);
    }
}

package ru.ddark008.yadisk.exceptions;

/**
 * Элемент не найден.
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String id) {
        super("Item not found: " + id);
    }

    public ItemNotFoundException(String id, String message) {
        super("Item " + id + " not found: " + message);
    }
}

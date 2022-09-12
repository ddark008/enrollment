package ru.ddark008.yadisk.api.impl;

public class ErrorValues {
    public static final String VALIDATION_FAILED = """
            {
              "code": 400,
              "message": "Validation Failed"
            }
            """;

    public static final String NOT_FOUND = """
            {
              "code": 404,
              "message": "Item not found"
            }
            """;
}

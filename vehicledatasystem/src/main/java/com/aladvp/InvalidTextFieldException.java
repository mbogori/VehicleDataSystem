package com.aladvp;


/**
 * Question 6: Represents an invalid text field entered
 * while creating a new record.
 *
 * The exception is thrown when the selected field is empty
 * or contains digits only.
 */
public class InvalidTextFieldException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception containing a clear correction message.
     *
     * @param fieldName the name of the invalid text field
     */
    public InvalidTextFieldException(String fieldName) {

        super("The " + fieldName  + " field cannot be empty. It cannot contain only digits! Please correct this!");
    }
}


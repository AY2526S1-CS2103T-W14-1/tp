package seedu.edubook.model.person.exceptions;

/**
 * Represents an error which occurred when handling Assignment objects.
 */
public class AssignmentException extends RuntimeException {
    public AssignmentException(String message) {
        super(message);
    }
}

package seedu.edubook.logic.parser.exceptions;

/**
 * Signals that given data does not meet length restrictions.
 */
public class ExceedLengthException extends ParseException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public ExceedLengthException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public ExceedLengthException(String message, Throwable cause) {
        super(message, cause);
    }
}

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

}

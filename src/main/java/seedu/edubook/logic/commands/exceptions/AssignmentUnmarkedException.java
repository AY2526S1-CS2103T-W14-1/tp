package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the assignment the user intends to unmark has already been unmarked.
 *
 * <p>This exception extends {@link CommandException} and is thrown when an
 * assignment is already unmarked for the specified student.</p>
 */
public class AssignmentUnmarkedException extends CommandException {

    /** Message indicating the assignment is already unmarked for the student */
    public static final String MESSAGE_ASSIGNMENT_ALREADY_UNMARKED = "This assignment "
            + "is already unmarked for this student. ";

    /**
     * Constructs a {@code AssignmentUnmarkedException} with a default message
     * indicating the assignment has already been unmarked.
     */
    public AssignmentUnmarkedException() {
        super(MESSAGE_ASSIGNMENT_ALREADY_UNMARKED);
    }
}

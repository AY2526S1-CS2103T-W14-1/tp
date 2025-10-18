package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the assignment the user intends to mark has already been marked
 *
 * <p>This exception extends {@link CommandException} and is thrown when an
 * assignment is already marked for the specified student.</p>
 */
public class AssignmentMarkedException extends CommandException {

    /** Message indicating the assignment is already marked for the student */
    public static final String MESSAGE_ASSIGNMENT_ALREADY_MARKED = "This assignment "
            + "is already marked for this student. ";

    /**
     * Constructs a {@code AssignmentMarkedException} with a default message
     * indicating the assignment has already been marked.
     */
    public AssignmentMarkedException() {
        super(MESSAGE_ASSIGNMENT_ALREADY_MARKED);
    }
}

package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find the target assignment
 * for a student.
 *
 * <p>This exception extends {@link CommandException} and is thrown
 * when an assignment is not currently assigned to the specified student.</p>
 */
public class AssignmentNotFoundException extends CommandException {

    /** Message indicating the assignment is not found for the student */
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "This student does not have "
            + "this assignment currently. ";

    /**
     * Constructs an {@code AssignmentNotFoundException} with a default message
     * indicating the assignment is not found.
     */
    public AssignmentNotFoundException() {
        super(MESSAGE_ASSIGNMENT_NOT_FOUND);
    }
}

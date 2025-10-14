package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the operation will lead to duplicate assignments being assigned
 * to the same student.
 *
 * <p>This exception extends {@link CommandException} and is thrown when an
 * assignment is already assigned to the specified student.</p>
 */
public class DuplicateAssignmentException extends CommandException {

    /** Message indicating the assignment is already assigned to the student */
    public static final String MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED = "This assignment "
            + "is already assigned to this student. ";

    /**
     * Constructs a {@code DuplicateAssignmentException} with a default message
     * indicating the assignment duplication.
     */
    public DuplicateAssignmentException() {
        super(MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED);
    }
}

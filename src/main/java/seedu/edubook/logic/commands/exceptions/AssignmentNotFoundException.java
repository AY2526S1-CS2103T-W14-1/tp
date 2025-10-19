package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find the target assignment
 * for a student.
 *
 * <p>This exception extends {@link CommandException} and is thrown
 * when an assignment is not currently assigned to the specified student.</p>
 */
public class AssignmentNotFoundException extends CommandException {

    /** Message indicating the assignment is not found for the student. */
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "This student does not have "
            + "this assignment currently. ";

    /** Message indicating the assignment is not found for the whole class. */
    public static final String MESSAGE_CLASS_ALREADY_UNASSIGNED =
            "All students in %s do not have assignment %s.";

    /**
     * Constructs an {@code AssignmentNotFoundException} with a default message
     * indicating the assignment is not found.
     */
    public AssignmentNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an {@code AssignmentNotFoundException} instance for the case where
     * the target is a single student who already has the specified assignment.
     *
     * @return An {@code AssignmentAlreadyExistsException} with a predefined message.
     */
    public static AssignmentNotFoundException forStudent() {
        return new AssignmentNotFoundException(MESSAGE_ASSIGNMENT_NOT_FOUND);
    }

    /**
     * Creates an {@code AssignmentAlreadyExistsException} instance for the case where
     * all students in a specified class already have the assignment.
     *
     * @param className The name of the class where all students have the assignment.
     * @param assignmentName The name of the assignment that is already assigned.
     * @return An {@code AssignmentAlreadyExistsException} with a formatted message.
     */
    public static AssignmentNotFoundException forClass(String className, String assignmentName) {
        return new AssignmentNotFoundException(
                String.format(MESSAGE_CLASS_ALREADY_UNASSIGNED, className, assignmentName)
        );
    }
}

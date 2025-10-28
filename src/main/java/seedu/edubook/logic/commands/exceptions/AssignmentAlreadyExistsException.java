package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the assignment is already assigned to the specified target
 * (student or entire class).
 */
public class AssignmentAlreadyExistsException extends CommandException {

    /** Message for a student who already has the assignment */
    public static final String MESSAGE_STUDENT_ALREADY_ASSIGNED = "This student already has this assignment.";

    /** Format string for a class where all students already have the assignment */
    public static final String MESSAGE_CLASS_ALREADY_ASSIGNED =
            "All students in '%s' already have the assignment '%s'.";

    /**
     * Constructs an {@code AssignmentAlreadyExistsException} with the specified detail message.
     *
     * @param message A message describing the duplication error.
     */
    public AssignmentAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Creates an {@code AssignmentAlreadyExistsException} instance for the case where
     * the target is a single student who already has the specified assignment.
     *
     * @return An {@code AssignmentAlreadyExistsException} with a predefined message.
     */
    public static AssignmentAlreadyExistsException forStudent() {
        return new AssignmentAlreadyExistsException(MESSAGE_STUDENT_ALREADY_ASSIGNED);
    }

    /**
     * Creates an {@code AssignmentAlreadyExistsException} instance for the case where
     * all students in a specified class already have the assignment.
     *
     * @param className The name of the class where all students have the assignment.
     * @param assignmentName The name of the assignment that is already assigned.
     * @return An {@code AssignmentAlreadyExistsException} with a formatted message.
     */
    public static AssignmentAlreadyExistsException forClass(String className, String assignmentName) {
        return new AssignmentAlreadyExistsException(
                String.format(MESSAGE_CLASS_ALREADY_ASSIGNED, className, assignmentName)
        );
    }
}

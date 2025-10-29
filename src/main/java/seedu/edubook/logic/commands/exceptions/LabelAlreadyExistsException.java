package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the label is already assigned to the specified target
 * (student or entire class).
 */
public class LabelAlreadyExistsException extends CommandException {

    /** Message for a student who already has a label */
    public static final String MESSAGE_STUDENT_ALREADY_ASSIGNED = "This student already has a label";

    /** Format string for a class where all students already have a label */
    public static final String MESSAGE_CLASS_ALREADY_ASSIGNED =
            "All students in '%s' already have a label";

    /**
     * Constructs an {@code LabelAlreadyExistsException} with the specified detail message.
     *
     * @param message A message describing the duplication error.
     */
    public LabelAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Creates an {@code LabelAlreadyExistsException} instance for the case where
     * the target is a single student who already has the specified assignment.
     *
     * @return An {@code AssignmentAlreadyExistsException} with a predefined message.
     */
    public static LabelAlreadyExistsException forStudent() {
        return new LabelAlreadyExistsException(MESSAGE_STUDENT_ALREADY_ASSIGNED);
    }

    /**
     * Creates an {@code LabelAlreadyExistsException} instance for the case where
     * all students in a specified class already have a label.
     *
     * @param className The name of the class where all students have a label.
     * @return An {@code AssignmentAlreadyExistsException} with a formatted message.
     */
    public static LabelAlreadyExistsException forClass(String className) {
        return new LabelAlreadyExistsException(
                String.format(MESSAGE_CLASS_ALREADY_ASSIGNED, className)
        );
    }
}

package seedu.edubook.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find the target label
 * for a student.
 *
 * <p>This exception extends {@link CommandException} and is thrown
 * when a label is not currently assigned to the specified student.</p>
 */
public class LabelNotFoundException extends CommandException {

    /** Message indicating a label is not found for the student. */
    public static final String MESSAGE_STUDENT_ALREADY_UNASSIGNED = "This student does not have "
            + "a label currently";

    /** Message indicating a label is not found for the whole class. */
    public static final String MESSAGE_CLASS_ALREADY_UNASSIGNED =
            "All students in '%s' do not have a label";

    public LabelNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an {@code LabelNotFoundException} instance for the case where
     * the target is a single student who currently does not have a label.
     *
     * @return An {@code LabelNotFoundException} with a predefined message.
     */
    public static LabelNotFoundException forStudent() {
        return new LabelNotFoundException(MESSAGE_STUDENT_ALREADY_UNASSIGNED);
    }

    /**
     * Creates a {@code LabelNotFoundException} instance for the case where
     * all students in a specified class does not have a label.
     *
     * @param className The name of the class where all students do not have a label.
     * @return An {@code LabelNotFoundException} with a formatted message.
     */
    public static LabelNotFoundException forClass(String className) {
        return new LabelNotFoundException(
                String.format(MESSAGE_CLASS_ALREADY_UNASSIGNED, className)
        );
    }
}

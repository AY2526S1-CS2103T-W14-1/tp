package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;
import static seedu.edubook.model.person.Name.MAX_NAME_LENGTH;

import seedu.edubook.model.person.Name;

/**
 * Represents an assignment in EduBook
 */
public class Assignment {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should only contain alphanumeric characters "
            + "and spaces, and it should not be blank";

    public static final int MAX_ASSIGNMENT_LENGTH = MAX_NAME_LENGTH;

    public static final String MESSAGE_LENGTH_CONSTRAINTS = "Assignment names should only contain a maximum of "
            + MAX_ASSIGNMENT_LENGTH + " characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final Name assignmentName;

    /**
     * Constructs an {@code Assignment}.
     *
     * @param assignmentName Name of assignment being assigned.
     */
    public Assignment(Name assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignment(assignmentName.fullName), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignmentName;
    }

    /**
     * Returns true if a given string is a valid assignment name.
     */
    public static boolean isValidAssignment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherTag = (Assignment) other;
        return assignmentName.equals(otherTag.assignmentName);
    }

    @Override
    public int hashCode() {
        return assignmentName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + assignmentName.fullName + ']';
    }

}

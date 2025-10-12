package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;

/**
 * Represents an assignment in EduBook
 */
public class Assignment {

    // Identity fields.
    public final AssignmentName assignmentName;

    /**
     * Constructs an {@code Assignment}.
     *
     * @param assignmentName Name of assignment being assigned.
     */
    public Assignment(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        this.assignmentName = assignmentName;
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

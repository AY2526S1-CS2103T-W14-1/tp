package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;
import static seedu.edubook.model.commons.Name.MAX_NAME_LENGTH;

import seedu.edubook.logic.commands.exceptions.AssignmentMarkedException;
import seedu.edubook.logic.commands.exceptions.AssignmentUnmarkedException;

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

    public final AssignmentName assignmentName;

    private boolean isDone;

    /**
     * Constructs an {@code Assignment}.
     *
     * @param assignmentName Name of assignment being assigned.
     */
    public Assignment(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignment(assignmentName.fullName), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignmentName;
        this.isDone = false;
    }

    /**
     * Constructs an {@code Assignment}.
     *
     * @param assignmentName Name of assignment being assigned.
     * @param isDone Current status of completion.
     */
    public Assignment(AssignmentName assignmentName, boolean isDone) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignment(assignmentName.fullName), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignmentName;
        this.isDone = isDone;
    }

    /**
     * Returns true if a given string is a valid assignment name.
     */
    public static boolean isValidAssignment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Marks the assignment as done.
     *
     * @throws AssignmentMarkedException if assignment is already marked.
     */
    public void mark() throws AssignmentMarkedException {
        if (this.isDone) {
            throw new AssignmentMarkedException();
        }
        this.isDone = true;
    }

    /**
     * Unmarks the assignment as not done.
     *
     * @throws AssignmentUnmarkedException if assignment is already unmarked.
     */
    public void unmark() throws AssignmentUnmarkedException {
        if (!this.isDone) {
            throw new AssignmentUnmarkedException();
        }
        this.isDone = false;
    }

    /**
     * Checks if input name matches assignment name of this instance.
     *
     * @param assignmentName input name to be checked.
     * @return boolean if name matches.
     */
    public boolean hasName(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        return this.assignmentName.equals(assignmentName);
    }

    /**
     * Returns the boolean denoted by isDone.
     */
    public boolean isDone() {
        return isDone;
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

        Assignment otherAssignment = (Assignment) other;
        return assignmentName.equals(otherAssignment.assignmentName);
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

package seedu.edubook.model.target;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;

/**
 * Represents a target that represents all students with the specified assignment name.
 */
public class AssignmentTarget implements Target {

    /** Error message when the assignment cannot be found in the model. */
    public static final String MESSAGE_NO_ASSIGNMENT_FOUND = "Assignment \"%1$s\" not found";

    /** Template for message when viewing assignment is successful. */
    private static final String MESSAGE_VIEW_SUCCESS =
            "Here are the details of all students with assignment \"%1$s\": ";

    /** Template for unexpected method call on AssignmentTarget. */
    private static final String MESSAGE_UNEXPECTED_CALL =
            "Error: getSuccessMessage should not be called on AssignmentTarget";

    private final AssignmentName assignmentName;

    /**
     * Constructs an {@code AssignmentTarget} for the given person assignment.
     *
     * @param assignmentName The assignment name of the students.
     */
    public AssignmentTarget(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        this.assignmentName = assignmentName;
    }

    @Override
    public List<Person> getPersons(Model model) throws CommandException {
        assert model != null : "Model cannot be null.";

        List<Person> persons = model.findPersonsByAssignmentName(assignmentName);
        assert persons != null : "Returned list of persons should not be null.";
        if (persons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_ASSIGNMENT_FOUND, assignmentName));
        }
        return persons;
    }

    @Override
    public String getDisplayName() {
        return assignmentName.toString();
    }

    @Override
    public boolean isSinglePersonTarget() {
        return false;
    }

    @Override
    public String getAssignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getUnassignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getMarkSuccessMessage(String assignmentName, int markedCount,
                                        int alreadyMarkedCount, int notExistCount) {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getUnmarkSuccessMessage(String assignmentName, int markedCount,
                                        int alreadyMarkedCount, int notExistCount) {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getLabelSuccessMessage(String labelName) {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getUnlabelSuccessMessage() {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getUnlabelFailureMessage() {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getDeleteSuccessMessage() {
        return MESSAGE_UNEXPECTED_CALL;
    }

    @Override
    public String getViewSuccessMessage() {
        return String.format(MESSAGE_VIEW_SUCCESS, assignmentName);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssignmentTarget)) {
            return false;
        }
        AssignmentTarget otherTarget = (AssignmentTarget) other;
        return assignmentName.equals(otherTarget.assignmentName);
    }

    @Override
    public int hashCode() {
        return assignmentName.hashCode();
    }

    @Override
    public String toString() {
        return "AssignmentTarget{Assignment=" + assignmentName + "}";
    }
}

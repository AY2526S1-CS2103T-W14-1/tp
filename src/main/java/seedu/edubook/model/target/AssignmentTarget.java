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
    public static final String MESSAGE_NO_ASSIGNMENT_FOUND = "Assignment '%s' not found.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_ASSIGN_SUCCESS = "New assignment '%s' assigned to student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_UNASSIGN_SUCCESS = "New assignment '%s' unassigned from student: '%s'.";

    /** Template for message when view class is successful. */
    private static final String MESSAGE_VIEW_SUCCESS =
            "Here are the details of all the students with assignment '%1$s'.";

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
        return String.format(MESSAGE_ASSIGN_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getUnassignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format(MESSAGE_UNASSIGN_SUCCESS, assignmentName, getDisplayName());
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

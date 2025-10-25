package seedu.edubook.model.target;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.TuitionClass;

/**
 * Represents a target that represents all students in a specified class.
 */
public class ClassTarget implements Target {

    /** Error message when no students are found in the class. */
    public static final String MESSAGE_NO_STUDENTS_FOUND = "No students found in class: '%s'.";

    /** Template for success message when assignment is assigned to a class. */
    public static final String MESSAGE_ASSIGN_SUCCESS =
            "New assignment '%s' assigned to class: '%s' (%d assigned, %d skipped).";

    /** Template for success message when assignment is unassigned from a class. */
    public static final String MESSAGE_UNASSIGN_SUCCESS =
            "Assignment '%s' unassigned from class: '%s' (%d unassigned, %d skipped).";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_LABEL_SUCCESS = "New label '%s' created for student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_UNLABEL_SUCCESS = "Existing label '%s' removed from student: '%s'.";

    /** Template for message when view class is successful. */
    private static final String MESSAGE_VIEW_SUCCESS =
            "Here are the details of all the students in '%1$s'.";

    private final TuitionClass tuitionClass;

    /**
     * Constructs a {@code ClassTarget} for the given class.
     *
     * @param tuitionClass The class whose students will be assigned.
     */
    public ClassTarget(TuitionClass tuitionClass) {
        requireNonNull(tuitionClass);
        this.tuitionClass = tuitionClass;
    }

    @Override
    public List<Person> getPersons(Model model) throws CommandException {
        assert model != null : "Model cannot be null.";

        List<Person> persons = model.findPersonsByClass(tuitionClass);
        assert persons != null : "Returned list of persons should not be null.";
        if (persons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_STUDENTS_FOUND, tuitionClass));
        }
        return persons;
    }

    @Override
    public String getDisplayName() {
        return tuitionClass.toString();
    }

    @Override
    public boolean isSinglePersonTarget() {
        return false;
    }

    @Override
    public String getAssignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format(MESSAGE_ASSIGN_SUCCESS, assignmentName, getDisplayName(), successCount, skippedCount);
    }

    @Override
    public String getUnassignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format(MESSAGE_UNASSIGN_SUCCESS, assignmentName, getDisplayName(), successCount, skippedCount);
    }

    @Override
    public String getLabelSuccessMessage(String assignmentName) {
        return String.format(MESSAGE_LABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getUnlabelSuccessMessage(String assignmentName) {
        return String.format(MESSAGE_UNLABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getViewSuccessMessage() {
        return String.format(MESSAGE_VIEW_SUCCESS, tuitionClass);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassTarget)) {
            return false;
        }
        ClassTarget otherTarget = (ClassTarget) other;
        return tuitionClass.equals(otherTarget.tuitionClass);
    }

    @Override
    public int hashCode() {
        return tuitionClass.hashCode();
    }

    @Override
    public String toString() {
        return "ClassTarget{tuitionClass=" + tuitionClass + "}";
    }
}

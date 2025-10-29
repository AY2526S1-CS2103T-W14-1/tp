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
    public static final String MESSAGE_NO_STUDENTS_FOUND =
            "No students found in class \"%1$s\"";

    /** Template for success message when assignment is assigned to a class. */
    public static final String MESSAGE_ASSIGN_SUCCESS =
            "Assignment \"%1$s\" assigned to class \"%2$s\" (%3$d assigned, %4$d skipped)";

    /** Template for success message when assignment is unassigned from a class. */
    public static final String MESSAGE_UNASSIGN_SUCCESS =
            "Assignment \"%1$s\" unassigned from class \"%2$s\" (%3$d unassigned, %4$d skipped)";

    /** Template for success message when label is assigned to a class. */
    public static final String MESSAGE_LABEL_SUCCESS =
            "Label \"%1$s\" assigned to class \"%2$s\"";

    /** Template for success message when label is unassigned from a class. */
    public static final String MESSAGE_UNLABEL_SUCCESS =
            "Label removed from class \"%1$s\"";

    /** Template for failure message when label is unassigned from a class. */
    public static final String MESSAGE_UNLABEL_FAILURE =
            "Class \"%1$s\" does not have any labels to remove";

    /** Template for success message when assignment is marked for a class. */
    public static final String MESSAGE_MARK_SUCCESS =
            "Assignment \"%1$s\" marked for class \"%2$s\" (%3$d marked, %4$d already marked, %5$d not exist)";

    /** Template for success message when assignment is unmarked for a class. */
    public static final String MESSAGE_UNMARK_SUCCESS =
            "Assignment \"%1$s\" unmarked for class \"%2$s\" (%3$d unmarked, %4$d already unmarked, %5$d not exist)";

    /** Template for message when delete class is successful. */
    public static final String MESSAGE_DELETE_SUCCESS =
            "All students in class \"%1$s\" have been deleted";

    /** Template for message when viewing a class is successful. */
    private static final String MESSAGE_VIEW_SUCCESS =
            "Here are the details of all students in class \"%1$s\": ";

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
    public String getMarkSuccessMessage(String assignmentName, int markedCount,
                                        int alreadyMarkedCount, int notExistCount) {
        return String.format(MESSAGE_MARK_SUCCESS, assignmentName, getDisplayName(),
                markedCount, alreadyMarkedCount, notExistCount);
    }

    @Override
    public String getUnmarkSuccessMessage(String assignmentName, int unmarkedCount,
                                        int alreadyUnmarkedCount, int notExistCount) {
        return String.format(MESSAGE_UNMARK_SUCCESS, assignmentName, getDisplayName(),
                unmarkedCount, alreadyUnmarkedCount, notExistCount);
    }

    @Override
    public String getLabelSuccessMessage(String labelName) {
        return String.format(MESSAGE_LABEL_SUCCESS, labelName, getDisplayName());
    }

    @Override
    public String getUnlabelSuccessMessage() {
        return String.format(MESSAGE_UNLABEL_SUCCESS, getDisplayName());
    }

    @Override
    public String getUnlabelFailureMessage() {
        return String.format(MESSAGE_UNLABEL_FAILURE, getDisplayName());
    }

    @Override
    public String getViewSuccessMessage() {
        return String.format(MESSAGE_VIEW_SUCCESS, tuitionClass);
    }

    @Override
    public String getDeleteSuccessMessage() {
        return String.format(MESSAGE_DELETE_SUCCESS, getDisplayName());
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

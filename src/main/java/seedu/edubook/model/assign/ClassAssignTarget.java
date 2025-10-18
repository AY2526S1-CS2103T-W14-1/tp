package seedu.edubook.model.assign;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.TuitionClass;

/**
 * Represents a target that assigns an assignment to all students in a class.
 */
public class ClassAssignTarget implements AssignTarget {

    /** Error message when no students are found in the class. */
    public static final String MESSAGE_NO_STUDENTS_FOUND = "No students found in class: %s.";

    /** Template for success message when assignment is assigned to a class. */
    public static final String MESSAGE_ASSIGNMENT_SUCCESS =
            "New assignment %s assigned to class: %s (%d assigned, %d skipped)";

    private final TuitionClass tuitionClass;

    /**
     * Constructs a {@code ClassAssignTarget} for the given class.
     *
     * @param tuitionClass The class whose students will be assigned.
     */
    public ClassAssignTarget(TuitionClass tuitionClass) {
        this.tuitionClass = tuitionClass;
    }

    @Override
    public List<Person> getPersons(Model model) throws CommandException {
        List<Person> persons = model.findPersonsByClass(tuitionClass);
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
    public String getAssignmentSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format(MESSAGE_ASSIGNMENT_SUCCESS, assignmentName, getDisplayName(), successCount, skippedCount);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassAssignTarget)) {
            return false;
        }
        ClassAssignTarget otherTarget = (ClassAssignTarget) other;
        return tuitionClass.equals(otherTarget.tuitionClass);
    }

    @Override
    public int hashCode() {
        return tuitionClass.hashCode();
    }

    @Override
    public String toString() {
        return "ClassAssignTarget{tuitionClass=" + tuitionClass + "}";
    }
}


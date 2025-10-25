package seedu.edubook.model.target;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a target that represents a student with the specified name.
 */
public class NameTarget implements Target {

    /** Error message when the student cannot be found in the model. */
    public static final String MESSAGE_PERSON_NOT_FOUND = "Student '%s' not found.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_ASSIGN_SUCCESS = "New assignment '%s' assigned to student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_UNASSIGN_SUCCESS = "New assignment '%s' unassigned from student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_LABEL_SUCCESS = "New label '%s' created for student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_UNLABEL_SUCCESS = "Existing label '%s' removed from student: '%s'.";

    /** Template for message when view class is successful. */
    private static final String MESSAGE_VIEW_SUCCESS = "Here are the details of %1$s.";

    private final PersonName name;

    /**
     * Constructs a {@code NameTarget} for the given person name.
     *
     * @param name The name of the student to assign to.
     */
    public NameTarget(PersonName name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public List<Person> getPersons(Model model) throws CommandException {
        try {
            Person person = model.findPersonByName(name);
            return List.of(person);
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, name));
        }
    }

    @Override
    public String getDisplayName() {
        return name.fullName;
    }

    @Override
    public boolean isSinglePersonTarget() {
        return true;
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
    public String getLabelSuccessMessage(String assignmentName) {
        return String.format(MESSAGE_LABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getUnlabelSuccessMessage(String assignmentName) {
        return String.format(MESSAGE_UNLABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getViewSuccessMessage() {
        return String.format(MESSAGE_VIEW_SUCCESS, name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NameTarget)) {
            return false;
        }
        NameTarget otherTarget = (NameTarget) other;
        return name.equals(otherTarget.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "NameTarget{name=" + name.fullName + "}";
    }
}

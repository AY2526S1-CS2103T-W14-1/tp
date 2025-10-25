package seedu.edubook.model.target;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.model.target.NameTarget.MESSAGE_MARK_SUCCESS;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a target that creates a label to a single student by name.
 */
public class NameTargetForLabel implements Target {

    /** Error message when the student cannot be found in the model. */
    public static final String MESSAGE_PERSON_NOT_FOUND = "Student '%s' not found.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_LABEL_SUCCESS = "New label '%s' created for student: '%s'.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_UNLABEL_SUCCESS = "Existing label '%s' removed from student: '%s'.";

    private final PersonName name;

    /**
     * Constructs a {@code NameAssignTarget} for the given person name.
     *
     * @param name The name of the student to assign to.
     */
    public NameTargetForLabel(PersonName name) {
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
        return String.format(MESSAGE_LABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getUnassignSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format(MESSAGE_UNLABEL_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public String getMarkSuccessMessage(String assignmentName, int markedCount,
                                        int alreadyMarkedCount, int notExistCount) {
        return String.format(MESSAGE_MARK_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NameTargetForLabel)) {
            return false;
        }
        NameTargetForLabel otherTarget = (NameTargetForLabel) other;
        return name.equals(otherTarget.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "NameTargetForLabel{name=" + name.fullName + "}";
    }
}

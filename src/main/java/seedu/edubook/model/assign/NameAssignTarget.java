package seedu.edubook.model.assign;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a target that assigns an assignment to a single student by name.
 */
public class NameAssignTarget implements AssignTarget {

    /** Error message when the student cannot be found in the model. */
    public static final String MESSAGE_PERSON_NOT_FOUND = "Student not found.";

    /** Template for success message when assignment is assigned to a student. */
    public static final String MESSAGE_ASSIGNMENT_SUCCESS = "New assignment %s assigned to student: %s";

    private final PersonName name;

    /**
     * Constructs a {@code NameAssignTarget} for the given person name.
     *
     * @param name The name of the student to assign to.
     */
    public NameAssignTarget(PersonName name) {
        this.name = name;
    }

    @Override
    public List<Person> getPersons(Model model) throws CommandException {
        try {
            Person person = model.findPersonByName(name);
            return List.of(person);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
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
        return String.format(MESSAGE_ASSIGNMENT_SUCCESS, assignmentName, getDisplayName());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NameAssignTarget)) {
            return false;
        }
        NameAssignTarget otherTarget = (NameAssignTarget) other;
        return name.equals(otherTarget.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "NameAssignTarget{name=" + name.fullName + "}";
    }
}

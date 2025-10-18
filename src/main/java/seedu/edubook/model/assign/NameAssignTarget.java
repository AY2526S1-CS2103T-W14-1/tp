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
            throw new CommandException("Student not found.");
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
    public String getAssignmentSuccessMessage(String assignmentName, int successCount, int skippedCount) {
        return String.format("New assignment %s assigned to student: %s", assignmentName, getDisplayName());
    }
}


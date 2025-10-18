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
            throw new CommandException(String.format("No students found in class %s.", tuitionClass));
        }
        return persons;
    }

    @Override
    public String getDisplayName() {
        return tuitionClass.toString();
    }
}


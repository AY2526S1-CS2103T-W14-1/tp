package seedu.edubook.model.assign;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;

/**
 * Represents a target that can provide one or more assignees for an assignment.
 * <p>
 * Implementations define how the target is resolved (e.g., by name, by class, by tag).
 */
public interface AssignTarget {

    /**
     * Returns a list of persons that this target represents.
     *
     * @param model The model to query for persons.
     * @return List of persons to assign the assignment to.
     * @throws CommandException if no valid persons are found or some error occurs.
     */
    List<Person> getPersons(Model model) throws CommandException;

    /**
     * Returns a displayable name for the target, used in messages/logging.
     *
     * @return The target’s display name.
     */
    String getDisplayName();
}


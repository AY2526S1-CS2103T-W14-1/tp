package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a student identified by their name or the index number used in the "
            + "displayed student list or all students in a class.\n"
            + "Parameters: [INDEX (must be a positive integer) | "
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS]\n"
            + "Example: " + COMMAND_WORD + " 1"
            + " OR "
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + "Example: " + COMMAND_WORD + " " + PREFIX_CLASS + "Class 1-B";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Student %1$s has been deleted";

    private final Target target;
    private final Index targetIndex;

    /**
     * Creates a {@code DeleteCommand} to delete student at specified {@code targetIndex}
     *
     * @param targetIndex The index of the student to delete.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.target = null;
    }

    /**
     * Creates a {@code DeleteCommand} to delete all students in specified {@code target}
     *
     * @param target Target student or class to delete.
     */
    public DeleteCommand(Target target) {
        requireNonNull(target);
        this.target = target;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.target == null) {
            return deleteByIndex(model);
        } else {
            return deleteByTarget(model);
        }
    }

    /**
     * Removes a person identified by their index in the displayed person list.
     *
     * @param model The model to remove.
     *
     * @throws CommandException If {@code targetIndex} is bigger than the number of students.
     */
    public CommandResult deleteByIndex(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(targetIndex);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

    /**
     * Removes a person identified by their name in the displayed person list.
     *
     * @param model The model to remove.
     */
    public CommandResult deleteByTarget(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(target);

        List<Person> studentsToDelete = target.getPersons(model);

        for (Person p : studentsToDelete) {
            model.deletePerson(p);
        }
        return new CommandResult(String.format(target.getDeleteSuccessMessage()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        // Handle case where both use index
        if (targetIndex != null && otherDeleteCommand.targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }

        // Handle case where both use name
        if (target != null && otherDeleteCommand.target != null) {
            return target.equals(otherDeleteCommand.target);
        }

        // If one uses index and the other uses prefix, they're not equal
        return false;
    }

    @Override
    public String toString() {
        if (target == null) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("target", target)
                    .toString();
        }
    }
}

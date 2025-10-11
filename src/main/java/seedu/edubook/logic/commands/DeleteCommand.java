package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.commons.Name;
import seedu.edubook.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by their name or their index number used in the displayed person list.\n"
            + "Parameters when deleting using index: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters when deleting using name: " + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    private final Name targetName;

    /**
     * Creates a {@code DeleteCommand} to remove student at specified {@code targetIndex}
     *
     * @param targetIndex
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a {@code DeleteCommand} to remove student at specified {@code name}
     *
     * @param name
     */
    public DeleteCommand(Name name) {
        this.targetName = name;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (this.targetName == null) {
            return deleteByIndex(model);
        } else {
            return deleteByName(model);
        }
    }

    /**
     * Removes a person identified by their index in the displayed person list.
     *
     * @param model
     * @return
     * @throws CommandException
     */
    public CommandResult deleteByIndex(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Removes a person identified by their name in the displayed person list.
     *
     * @param model
     * @return
     * @throws CommandException
     */
    public CommandResult deleteByName(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person p : lastShownList) {
            if (p.getName().equals(this.targetName)) {
                model.deletePerson(p);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(p)));
            }
        }

        throw new CommandException(Messages.MESSAGE_INVALID_NAME);
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
        if (targetName != null && otherDeleteCommand.targetName != null) {
            return targetName.equals(otherDeleteCommand.targetName);
        }

        // If one uses index and the other uses name, they're not equal
        return false;
    }

    @Override
    public String toString() {
        if (targetName == null) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetName", targetName)
                    .toString();
        }
    }
}

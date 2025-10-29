package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Unassigns an assignment from a student.
 */
public class UnlabelCommand extends Command {

    public static final String COMMAND_WORD = "unlabel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a label from a student. \n"
            + "Parameters: "
            + "{"
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS}\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + COMMAND_WORD + " "
            + PREFIX_CLASS + "Class 1-B";

    private static final Logger logger = LogsCenter.getLogger(UnlabelCommand.class);

    private final Target target;

    /**
     * Creates an UnlabelCommand for the target.
     *
     * @param target The target to unassign from (single student).
     */
    public UnlabelCommand(Target target) {
        requireNonNull(target);
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UnlabelCommand for target: " + target.getDisplayName());

        List<Person> studentsToUnlabel = target.getPersons(model);

        int count = 0;
        int size = studentsToUnlabel.size();

        for (Person person : studentsToUnlabel) {
            if (person.getLabel().equals(Label.EMPTY)) {
                count++;
            }
            model.setPerson(person, person.withRemovedLabel());
        }

        if (count == size) {
            String message = target.getUnlabelFailureMessage();
            return new CommandResult(message);
        }

        // Generate success message
        String message = target.getUnlabelSuccessMessage();
        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("UnlabelCommand completed: " + message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnlabelCommand)) {
            return false;
        }

        UnlabelCommand otherUnlabelCommand = (UnlabelCommand) other;
        return target.equals(otherUnlabelCommand.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .toString();
    }
}

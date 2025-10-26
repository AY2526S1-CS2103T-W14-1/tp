package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Finds and lists a person in address book with the specified name.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the details of a single student, all students in a class "
            + " or all students with a particular assignment. "
            + "Parameters: ["
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS | "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + "Example: " + COMMAND_WORD + " " + PREFIX_CLASS + "Class 1-B"
            + " OR "
            + "Example: " + COMMAND_WORD + " " + PREFIX_ASSIGNMENT_NAME + "Tutorial 1";

    public static final String MESSAGE_VIEW_STUDENT_SUCCESS =
            "Here are the details of %1$s.";

    private static final Logger logger = LogsCenter.getLogger(ViewCommand.class);

    private final Target target;

    /**
     * Creates a {@code ViewCommand} to view student with specified {@code target}
     *
     * @param target The target to assign to (single student or class).
     */
    public ViewCommand(Target target) {
        requireNonNull(target);
        this.target = target;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing ViewCommand for target: " + target.getDisplayName());

        List<Person> studentsToView = target.getPersons(model);
        Predicate<Person> predicate = studentsToView::contains;
        model.updateFilteredPersonList(predicate);
        String message = target.getViewSuccessMessage();

        logger.info("ViewCommand completed: " + message);

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;

        return target.equals(otherViewCommand.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .toString();
    }
}

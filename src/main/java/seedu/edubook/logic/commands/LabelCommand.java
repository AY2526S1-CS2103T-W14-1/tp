package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_LABEL;
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
 * Creates a label for a student
 */
public class LabelCommand extends Command {

    public static final String COMMAND_WORD = "label";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a label for a student.\n"
            + "Parameters: "
            + PREFIX_LABEL + "LABEL "
            + PREFIX_PERSON_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LABEL + "Late for class "
            + PREFIX_PERSON_NAME + "John Doe";

    private static final Logger logger = LogsCenter.getLogger(LabelCommand.class);

    private final Label label;
    private final Target target;

    /**
     * Creates a LabelCommand for a given label and target
     *
     * @param label
     * @param target
     */
    public LabelCommand(Label label, Target target) {
        requireNonNull(label);
        requireNonNull(target);
        this.label = label;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing LabelCommand for target: " + target.getDisplayName());

        List<Person> studentToLabel = target.getPersons(model);

        for (Person person : studentToLabel) {
            model.setPerson(person, person.withAddedLabel(label));
        }

        String message = target.getAssignSuccessMessage(label.toString(), 0, 0);
        assert message != null && !message.isBlank() : "generated message must not be null or empty";


        logger.info("LabelCommand completed: " + message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LabelCommand
                && label.equals(((LabelCommand) other).label)
                && target.equals(((LabelCommand) other).target));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("label", label)
                .add("target", target)
                .toString();
    }

}

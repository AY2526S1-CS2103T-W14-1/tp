package seedu.edubook.logic.commands;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.model.target.Target;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.*;

/**
 * Creates a label for a student
 */
public class LabelCommand {

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

    public LabelCommand(Label label, Target target) {
        requireNonNull(label);
        requireNonNull(target);
        this.label = label;
        this.target = target;
    }
}

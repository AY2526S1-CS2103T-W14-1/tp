package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.logic.commands.exceptions.LabelAlreadyExistsException;
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
            + PREFIX_LABEL + "LABEL {"
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS}\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LABEL + "Tutorial 6 "
            + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + COMMAND_WORD + " "
            + PREFIX_LABEL + "Tutorial 6 "
            + PREFIX_CLASS + "Class 1-B";

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

        List<Person> studentsToLabel = target.getPersons(model);

        // Process all assignments and count successes and skips
        int[] counts = processAssignments(model, studentsToLabel);

        assert counts.length == 2 : "processAssignments must return an array of length 2";
        assert counts[0] >= 0 && counts[1] >= 0 : "success and skip counts must not be negative";
        assert counts[0] + counts[1] == studentsToLabel.size()
                : "sum of success and skipped counts must match total students processed";

        int successCount = counts[0];
        int skippedCount = counts[1];

        handleNoAssignments(successCount);

        // Generate success message
        String message = target.getLabelSuccessMessage(label.labelContent,
                successCount, skippedCount);

        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("LabelCommand completed: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes assignment of labels for each person in the list.
     * <p>
     * Skips students who already have a label and counts successes and skips.
     *
     * @param model The model to update.
     * @param assignees The list of students to assign the label to.
     * @return An array where index 0 is the number of successful assignments,
     *         and index 1 is the number of skipped assignments.
     */
    private int[] processAssignments(Model model, List<Person> assignees) {
        int successCount = 0;
        int skippedCount = 0;

        for (Person person : assignees) {
            try {
                model.setPerson(person, person.withAddedLabel(label));
                successCount++;
            } catch (LabelAlreadyExistsException e) {
                skippedCount++;
                logger.fine(() -> "Skipped " + person.getName() + " (already has label)");
            }
        }
        return new int[]{successCount, skippedCount};
    }

    /**
     * Throws LabelAlreadyExistsException if no new labels were added.
     *
     * @param successCount Number of successful labels added.
     * @throws LabelAlreadyExistsException If no new labels were added.
     */
    private void handleNoAssignments(int successCount) throws LabelAlreadyExistsException {
        if (successCount == 0) {
            if (target.isSinglePersonTarget()) {
                throw LabelAlreadyExistsException.forStudent();
            } else {
                throw LabelAlreadyExistsException.forClass(target.getDisplayName());
            }
        }
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

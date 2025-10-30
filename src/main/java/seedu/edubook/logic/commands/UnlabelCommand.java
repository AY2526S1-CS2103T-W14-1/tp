package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.logic.commands.exceptions.LabelNotFoundException;
import seedu.edubook.model.Model;
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

        List<Person> studentsToUnassign = target.getPersons(model);

        // Process all assignments and count successes and skips
        int[] counts = processAssignments(model, studentsToUnassign);

        assert counts.length == 2 : "processAssignments must return an array of length 2";
        assert counts[0] >= 0 && counts[1] >= 0 : "success and skip counts must not be negative";
        assert counts[0] + counts[1] == studentsToUnassign.size()
                : "sum of success and skipped counts must match total students processed";

        int successCount = counts[0];
        int skippedCount = counts[1];

        handleNoAssignments(successCount);

        // Generate success message
        String message = target.getUnlabelSuccessMessage(successCount, skippedCount);
        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("UnlabelCommand completed: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes label removal for each person in the list.
     * <p>
     * Skips students who already do not have a label and counts successes and skips.
     *
     * @param model The model to update.
     * @param assignees The list of students to remove the label from.
     * @return An array where index 0 is the number of successful assignment removals,
     *         and index 1 is the number of skipped students.
     */
    private int[] processAssignments(Model model, List<Person> assignees) {
        int successCount = 0;
        int skippedCount = 0;

        for (Person person : assignees) {
            try {
                model.setPerson(person, person.withRemovedLabel());
                successCount++;
            } catch (LabelNotFoundException e) {
                skippedCount++;
                logger.fine(() -> "Skipped " + person.getName() + " (already does not have a label)");
            }
        }
        return new int[]{successCount, skippedCount};
    }

    /**
     * Throws LabelNotFoundException if no new labels were removed.
     *
     * @param successCount Number of successful labels unassigned.
     * @throws LabelNotFoundException if no new labels were unassigned.
     */
    private void handleNoAssignments(int successCount) throws LabelNotFoundException {
        if (successCount == 0) {
            if (target.isSinglePersonTarget()) {
                throw LabelNotFoundException.forStudent();
            } else {
                throw LabelNotFoundException.forClass(target.getDisplayName());
            }
        }
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

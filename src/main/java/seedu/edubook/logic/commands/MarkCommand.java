package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.AssignmentMarkedException;
import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Marks an assignment for a person or an entire class.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an assignment for a student or class.\n"
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT {"
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS}\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_CLASS + "Class 1-B";

    public static final String MESSAGE_ASSIGNMENT_ALREADY_MARKED_SINGLE =
            "%s's assignment \"%s\" has already been marked";

    public static final String MESSAGE_ASSIGNMENT_ALREADY_MARKED_CLASS =
            "All %d students in class \"%s\" already have \"%s\" marked";

    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE =
            "%s does not have an assignment named \"%s\"";

    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS =
            "No students in class \"%s\" have an assignment named \"%s\"";

    public static final String MESSAGE_ASSIGNMENT_MIXED_CLASS =
            "No assignments were marked for class \"%s\". %d %s already marked and %d did not exist";

    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);

    private final AssignmentName assignmentName;
    private final Target target;

    /**
     * Constructs a MarkCommand for a given assignment and target.
     *
     * @param assignmentName Name of the assignment to mark.
     * @param target Target to mark (either NameTarget or ClassTarget).
     */
    public MarkCommand(AssignmentName assignmentName, Target target) {
        requireNonNull(assignmentName);
        requireNonNull(target);
        this.assignmentName = assignmentName;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing MarkCommand for target: " + target.getDisplayName());

        List<Person> personsToMark = target.getPersons(model);

        // Process marking and count results
        int[] counts = processMarking(model, personsToMark);

        assert counts.length == 3 : "processMarking must return an array of length 3";
        assert counts[0] >= 0 && counts[1] >= 0 && counts[2] >= 0 : "all counts must be non-negative";
        assert counts[0] + counts[1] + counts[2] == personsToMark.size()
                : "sum of all counts must match total persons processed";

        int markedCount = counts[0];
        int alreadyMarkedCount = counts[1];
        int notExistCount = counts[2];

        handleNoMarks(markedCount, alreadyMarkedCount, notExistCount, personsToMark.size());

        // Generate success message
        String message = target.getMarkSuccessMessage(assignmentName.toString(),
                markedCount, alreadyMarkedCount, notExistCount);

        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("MarkCommand completed successfully: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes marking for each person in the list.
     * <p>
     * Counts successes, already marked, and assignment not exist.
     *
     * @param model The model to update.
     * @param assignees The list of persons to mark the assignment for.
     * @return An array where index 0 = markedCount, 1 = alreadyMarkedCount, 2 = notExistCount.
     */
    private int[] processMarking(Model model, List<Person> assignees) {
        int markedCount = 0;
        int alreadyMarkedCount = 0;
        int notExistCount = 0;

        for (Person person : assignees) {
            try {
                person.markAssignment(assignmentName);
                model.setPerson(person, person);
                markedCount++;
            } catch (AssignmentMarkedException e) {
                alreadyMarkedCount++;
                logger.fine(() -> "Already marked: " + person.getName());
            } catch (AssignmentNotFoundException e) {
                notExistCount++;
                logger.fine(() -> "Assignment does not exist for: " + person.getName());
            }
        }

        return new int[]{markedCount, alreadyMarkedCount, notExistCount};
    }

    /**
     * Throws a CommandException if no assignments were marked, with detailed messages
     * depending on the reason (already marked, not found, or mixed).
     *
     * @param markedCount Number of successfully marked assignments.
     * @param alreadyMarkedCount Number of assignments skipped because they were already marked.
     * @param notExistCount Number of assignments skipped because they did not exist.
     * @param total Total number of persons processed (must be ≥ 1).
     * @throws CommandException if no assignments were marked.
     */
    private void handleNoMarks(int markedCount, int alreadyMarkedCount, int notExistCount, int total)
            throws CommandException {
        assert total >= 1 : "At least one person must be processed";
        assert markedCount >= 0 && alreadyMarkedCount >= 0 && notExistCount >= 0 : "Counts must not be negative";
        assert markedCount + alreadyMarkedCount + notExistCount == total
                : "Sum of all counts must match total persons processed";

        // If at least one was marked, nothing to handle
        if (markedCount > 0) {
            return;
        }

        // Compose detailed failure message
        String message = composeNoMarkMessage(alreadyMarkedCount, notExistCount, total);

        logger.warning("MarkCommand aborted: " + message);
        throw new CommandException(message);
    }

    /**
     * Generates a user-friendly message explaining why no marks were applied.
     *
     * @param alreadyMarkedCount Number of assignments already marked.
     * @param notExistCount Number of assignments that did not exist.
     * @param total Total number of persons processed.
     * @return A detailed failure message for display to the user.
     */
    private String composeNoMarkMessage(int alreadyMarkedCount, int notExistCount, int total) {
        final String targetName = target.getDisplayName();
        final String assignment = assignmentName.toString();

        assert total >= 1 : "composeNoMarkMessage should only be called when total is at least 1";

        if (alreadyMarkedCount == total) {
            return target.isSinglePersonTarget()
                    ? String.format(MESSAGE_ASSIGNMENT_ALREADY_MARKED_SINGLE, targetName, assignment)
                    : String.format(MESSAGE_ASSIGNMENT_ALREADY_MARKED_CLASS, total, targetName, assignment);
        }

        if (notExistCount == total) {
            return target.isSinglePersonTarget()
                    ? String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE, targetName, assignment)
                    : String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS, targetName, assignment);
        }

        // Mixed reason: combination of already marked and not exist
        return String.format(MESSAGE_ASSIGNMENT_MIXED_CLASS,
                targetName,
                alreadyMarkedCount,
                alreadyMarkedCount == 1 ? "was" : "were",
                notExistCount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherCommand = (MarkCommand) other;
        return assignmentName.equals(otherCommand.assignmentName)
                && Objects.equals(target, otherCommand.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", assignmentName)
                .add("target", target)
                .toString();
    }
}

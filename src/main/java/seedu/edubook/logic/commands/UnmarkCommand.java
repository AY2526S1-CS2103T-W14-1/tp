package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.AssignmentUnmarkedException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Unmarks the assignment of a student as not completed.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks an assignment for a student or class.\n"
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

    public static final String MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_SINGLE =
            "%s's assignment \"%s\" has already been unmarked";

    public static final String MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_CLASS =
            "All %d students in class \"%s\" already have \"%s\" unmarked";

    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE =
            "%s does not have an assignment named \"%s\"";

    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS =
            "No students in class \"%s\" have an assignment named \"%s\"";

    public static final String MESSAGE_ASSIGNMENT_MIXED_CLASS =
            "No assignments were unmarked for class \"%s\". %d %s already unmarked and %d did not exist";

    private static final Logger logger = LogsCenter.getLogger(UnmarkCommand.class);

    private final AssignmentName assignmentName;
    private final Target target;

    /**
     * Creates a UnmarkCommand with the specified {@code AssignmentName} and {@code Target}.
     */
    public UnmarkCommand(AssignmentName assignmentName, Target target) {
        requireNonNull(assignmentName);
        requireNonNull(target);
        this.assignmentName = assignmentName;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UnmarkCommand for target: " + target.getDisplayName());

        List<Person> personsToUnmark = target.getPersons(model);

        // Process unmarking and count results
        int[] counts = processUnmarking(model, personsToUnmark);

        assert counts.length == 3 : "processUnmarking must return an array of length 3";
        assert counts[0] >= 0 && counts[1] >= 0 && counts[2] >= 0 : "all counts must be non-negative";
        assert counts[0] + counts[1] + counts[2] == personsToUnmark.size()
                : "sum of all counts must match total persons processed";
        assert !personsToUnmark.isEmpty() : "Target should include at least one person";

        int unmarkedCount = counts[0];
        int alreadyUnmarkedCount = counts[1];
        int notExistCount = counts[2];

        handleNoUnmarks(unmarkedCount, alreadyUnmarkedCount, notExistCount, personsToUnmark.size());

        // Generate success message
        String message = target.getUnmarkSuccessMessage(assignmentName.toString(),
                unmarkedCount, alreadyUnmarkedCount, notExistCount);

        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("UnmarkCommand completed successfully: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes the unmarking of the specified assignment for each student in the list.
     * <p>
     * Counts successes, already unmarked assignments, and target assignments which do not exist for the students.
     *
     * @param model The model to update.
     * @param assignees The list of students to unmark the assignment for.
     * @return An array where index 0 = unmarkedCount, 1 = alreadyUnmarkedCount, 2 = notExistCount.
     */
    private int[] processUnmarking(Model model, List<Person> assignees) {
        int unmarkedCount = 0;
        int alreadyUnmarkedCount = 0;
        int notExistCount = 0;

        for (Person person : assignees) {
            try {
                person.unmarkAssignment(assignmentName);
                model.setPerson(person, person);
                unmarkedCount++;
            } catch (AssignmentUnmarkedException e) {
                alreadyUnmarkedCount++;
                logger.fine(() -> "Already unmarked: " + person.getName());
            } catch (AssignmentNotFoundException e) {
                notExistCount++;
                logger.fine(() -> "Assignment does not exist for: " + person.getName());
            }
        }

        return new int[]{unmarkedCount, alreadyUnmarkedCount, notExistCount};
    }

    /**
     * Throws a CommandException if no assignments were unmarked, with detailed messages
     * depending on the reason (already unmarked, not found, or mixed).
     *
     * @param unmarkedCount Number of successfully unmarked assignments.
     * @param alreadyUnmarkedCount Number of assignments skipped because they were already unmarked.
     * @param notExistCount Number of assignments skipped because they did not exist.
     * @param total Total number of persons processed (must be ≥ 1).
     * @throws CommandException if no assignments were unmarked.
     */
    private void handleNoUnmarks(int unmarkedCount, int alreadyUnmarkedCount, int notExistCount, int total)
            throws CommandException {
        assert total >= 1 : "At least one person must be processed";
        assert unmarkedCount >= 0 && alreadyUnmarkedCount >= 0 && notExistCount >= 0 : "Counts must not be negative";
        assert unmarkedCount + alreadyUnmarkedCount + notExistCount == total
                : "Sum of all counts must match total persons processed";

        // If at least one was unmarked, nothing to handle
        if (unmarkedCount > 0) {
            logger.fine("There is at least one assignment which was unmarked. UnmarkCommand will proceed.");
            return;
        }

        // Compose detailed failure message
        String message = composeNoUnmarkMessage(alreadyUnmarkedCount, notExistCount, total);

        logger.warning("UnmarkCommand aborted: " + message);
        throw new CommandException(message);
    }

    /**
     * Generates a user-friendly message explaining why no unmarking of assignments occurred.
     *
     * @param alreadyUnmarkedCount Number of assignments already unmarked.
     * @param notExistCount Number of assignments that does not exist.
     * @param total Total number of persons processed.
     * @return A detailed failure message for display to the user.
     */
    private String composeNoUnmarkMessage(int alreadyUnmarkedCount, int notExistCount, int total) {
        final String targetName = target.getDisplayName();
        final String assignment = assignmentName.toString();

        assert total >= 1 : "composeNoUnmarkMessage should only be called when total is at least 1";

        if (alreadyUnmarkedCount == total) {
            return target.isSinglePersonTarget()
                    ? String.format(MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_SINGLE, targetName, assignment)
                    : String.format(MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_CLASS, total, targetName, assignment);
        }

        if (notExistCount == total) {
            return target.isSinglePersonTarget()
                    ? String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE, targetName, assignment)
                    : String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS, targetName, assignment);
        }

        // Mixed reason: combination of already unmarked and not exist
        return String.format(MESSAGE_ASSIGNMENT_MIXED_CLASS,
                targetName,
                alreadyUnmarkedCount,
                alreadyUnmarkedCount == 1 ? "was" : "were",
                notExistCount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand otherUnmarkCommand = (UnmarkCommand) other;
        return target.equals(otherUnmarkCommand.target)
                && assignmentName.equals(otherUnmarkCommand.assignmentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .add("assignmentName", assignmentName)
                .toString();
    }
}

package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assign.AssignTarget;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Person;


/**
 * Unassigns an assignment from a student.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns an assignment from a student. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "NAME OF ASSIGNMENT "
            + PREFIX_PERSON_NAME + "NAME OF ASSIGNEE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 1 "
            + PREFIX_PERSON_NAME + "John Doe";

    private static final Logger logger = LogsCenter.getLogger(AssignCommand.class);

    private final Assignment assignment;
    private final AssignTarget target;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Assignment}.
     */
    public UnassignCommand(Assignment assignment, AssignTarget target) {
        requireNonNull(target);
        requireNonNull(assignment);
        this.target = target;
        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UnassignCommand for target: " + target.getDisplayName());

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
        String message = target.getUnassignSuccessMessage(assignment.assignmentName.toString(),
                successCount, skippedCount);

        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("AssignCommand completed: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes assignment removal for each person in the list.
     * <p>
     * Skips students who already do not have the assignment and counts successes and skips.
     *
     * @param model The model to update.
     * @param assignees The list of students to unassign the assignment from.
     * @return An array where index 0 is the number of successful assignment removals,
     *         and index 1 is the number of skipped students.
     */
    private int[] processAssignments(Model model, List<Person> assignees) {
        int successCount = 0;
        int skippedCount = 0;

        for (Person person : assignees) {
            try {
                model.setPerson(person, person.withRemovedAssignment(assignment));
                successCount++;
            } catch (AssignmentNotFoundException e) {
                skippedCount++;
                logger.fine(() -> "Skipped " + person.getName() + " (already does not have this assignment)");
            }
        }
        return new int[]{successCount, skippedCount};
    }

    /**
     * Throws AssignmentNotFoundException if no new assignments were removed.
     *
     * @param successCount Number of successful assignments unassigned.
     * @throws AssignmentNotFoundException if no new assignments were unassigned.
     */
    private void handleNoAssignments(int successCount) throws AssignmentNotFoundException {
        if (successCount == 0) {
            if (target.isSinglePersonTarget()) {
                throw AssignmentNotFoundException.forStudent();
            } else {
                throw AssignmentNotFoundException.forClass(target.getDisplayName(),
                        assignment.assignmentName.toString()
                );
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignCommand)) {
            return false;
        }

        UnassignCommand otherUnassignCommand = (UnassignCommand) other;
        return assignment.equals(otherUnassignCommand.assignment)
                && target.equals(otherUnassignCommand.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignment", assignment)
                .add("target", target)
                .toString();
    }
}

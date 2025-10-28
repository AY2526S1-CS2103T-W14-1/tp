package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.AssignmentAlreadyExistsException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.target.Target;

/**
 * Assigns an assignment to one or more students.
 * <p>
 * Supports assigning by individual student name or entire class.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to a student or class.\n"
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT ["
            + PREFIX_PERSON_NAME + "NAME | "
            + PREFIX_CLASS + "CLASS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_PERSON_NAME + "John Doe"
            + " OR "
            + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_CLASS + "Class 1-B";

    private static final Logger logger = LogsCenter.getLogger(AssignCommand.class);

    private final AssignmentName assignmentName;
    private final Target target;

    /**
     * Creates an AssignCommand for a given assignment name and target.
     *
     * @param assignmentName The name of the assignment to assign.
     * @param target The target to assign to (single student or class).
     */
    public AssignCommand(AssignmentName assignmentName, Target target) {
        requireNonNull(assignmentName);
        requireNonNull(target);
        this.assignmentName = assignmentName;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AssignCommand for target: " + target.getDisplayName());

        List<Person> studentsToAssign = target.getPersons(model);

        // Process all assignments and count successes and skips
        int[] counts = processAssignments(model, studentsToAssign);

        assert counts.length == 2 : "processAssignments must return an array of length 2";
        assert counts[0] >= 0 && counts[1] >= 0 : "success and skip counts must not be negative";
        assert counts[0] + counts[1] == studentsToAssign.size()
                : "sum of success and skipped counts must match total students processed";

        int successCount = counts[0];
        int skippedCount = counts[1];

        handleNoAssignments(successCount);

        // Generate success message
        String message = target.getAssignSuccessMessage(assignmentName.toString(),
                successCount, skippedCount);

        assert message != null && !message.isBlank() : "generated message must not be null or empty";

        logger.info("AssignCommand completed: " + message);
        return new CommandResult(message);
    }

    /**
     * Processes assignment addition for each person in the list.
     * <p>
     * Skips students who already have the assignment and counts successes and skips.
     *
     * @param model The model to update.
     * @param assignees The list of students to assign the assignment to.
     * @return An array where index 0 is the number of successful assignments,
     *         and index 1 is the number of skipped assignments.
     */
    private int[] processAssignments(Model model, List<Person> assignees) {
        int successCount = 0;
        int skippedCount = 0;

        for (Person person : assignees) {
            try {
                Assignment newAssignment = new Assignment(assignmentName);
                model.setPerson(person, person.withAddedAssignment(newAssignment));
                successCount++;
            } catch (AssignmentAlreadyExistsException e) {
                skippedCount++;
                logger.fine(() -> "Skipped " + person.getName() + " (already has assignment)");
            }
        }
        return new int[]{successCount, skippedCount};
    }

    /**
     * Throws AssignmentAlreadyExistsException if no new assignments were added.
     *
     * @param successCount Number of successful assignments added.
     * @throws AssignmentAlreadyExistsException if no new assignments were added.
     */
    private void handleNoAssignments(int successCount) throws AssignmentAlreadyExistsException {
        if (successCount == 0) {
            if (target.isSinglePersonTarget()) {
                throw AssignmentAlreadyExistsException.forStudent();
            } else {
                throw AssignmentAlreadyExistsException.forClass(target.getDisplayName(), assignmentName.toString());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssignCommand
                && assignmentName.equals(((AssignCommand) other).assignmentName)
                && target.equals(((AssignCommand) other).target));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", assignmentName)
                .add("target", target)
                .toString();
    }
}

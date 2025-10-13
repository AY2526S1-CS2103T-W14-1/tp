package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;


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

    public static final String MESSAGE_SUCCESS = "You have successfully unassigned %1$s from student %2$s. ";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook. ";

    private final PersonName unassignee;
    private final Assignment toUnassign;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Assignment}.
     */
    public UnassignCommand(Assignment assignment, PersonName currentAssignee) {
        requireNonNull(currentAssignee);
        requireNonNull(assignment);
        this.unassignee = currentAssignee;
        this.toUnassign = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Person target = model.findPersonByName(unassignee, MESSAGE_STUDENT_NOT_FOUND);
            Person updatedPerson = target.withRemovedAssignment(toUnassign);
            model.setPerson(target, updatedPerson);

            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, toUnassign.assignmentName, updatedPerson.getName())
            );
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
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
        return toUnassign.equals(otherUnassignCommand.toUnassign)
                && unassignee.equals(otherUnassignCommand.unassignee);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toUnassign", toUnassign)
                .add("unassignee", unassignee)
                .toString();
    }
}

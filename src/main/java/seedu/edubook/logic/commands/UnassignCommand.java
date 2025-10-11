package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.AssignmentException;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;

/**
 * Unassigns an assignment from a student.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns an assignment from a student. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT "
            + PREFIX_PERSON_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 1 "
            + PREFIX_PERSON_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "You have successfully unassigned %1$s from student %2$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "This student does not have "
            + "this assignment currently";

    private final PersonName currentAssignee;
    private final Assignment toUnassign;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Assignment}.
     */
    public UnassignCommand(Assignment assignment, PersonName currentAssignee) {
        requireNonNull(currentAssignee);
        requireNonNull(assignment);
        this.currentAssignee = currentAssignee;
        this.toUnassign = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Person target = model.findPersonByName(currentAssignee, MESSAGE_STUDENT_NOT_FOUND);
            Person updatedPerson = target.withRemovedAssignment(toUnassign, MESSAGE_ASSIGNMENT_NOT_FOUND);
            model.setPerson(target, updatedPerson);

            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, toUnassign.assignmentName, updatedPerson.getName())
            );
        } catch (AssignmentException e) {
            throw new CommandException(e.getMessage());
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
    }
}

    /*
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
}
*/

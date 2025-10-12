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
 * Assigns an assignment to a student.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to an existing person. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "NAME OF ASSIGNMENT"
            + PREFIX_PERSON_NAME + "NAME OF ASSIGNEE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_PERSON_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "New assignment %1$s assigned to: %2$s. ";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook. ";

    public static final String MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED = "This assignment "
            + "is already assigned to this student. ";

    private final PersonName assigneeName;
    private final Assignment toAssign;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AssignCommand(Assignment assignment, PersonName assignee) {
        requireNonNull(assignee);
        requireNonNull(assignment);
        this.assigneeName = assignee;
        this.toAssign = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Person assignee = model.findPersonByName(this.assigneeName, MESSAGE_STUDENT_NOT_FOUND);
            Person updatedPerson = assignee.withAddedAssignment(this.toAssign, MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED);
            model.setPerson(assignee, updatedPerson);
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, toAssign.assignmentName, updatedPerson.getName())
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

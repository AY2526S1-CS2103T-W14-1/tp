package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.Set;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;
import seedu.edubook.model.tag.Tag;

/**
 * Assigns an assignment to a student.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to an existing person. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "NAME OF ASSIGNMENT "
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
            Person updatedPerson = assignee.withAddedAssignment(this.toAssign);
            model.setPerson(assignee, updatedPerson);
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, toAssign.assignmentName, updatedPerson.getName())
            );
        } catch (DuplicateAssignmentException e) {
            throw new CommandException(MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED);
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
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return toAssign.equals(otherAssignCommand.toAssign)
                && assigneeName.equals(otherAssignCommand.assigneeName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssign", toAssign)
                .add("assigneeName", assigneeName)
                .toString();
    }

}

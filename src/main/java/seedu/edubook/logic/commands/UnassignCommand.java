package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Name;
import seedu.edubook.model.person.Person;

/**
 * Unassigns an assignment from a student.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns an assignment from a student. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 1 "
            + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "You have successfully unassigned %1$s from student %2$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "Seems like the student does not have "
            + "this assignment currently";
    // public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    // todo add more error messages after settling logic

    private final Name unassignee;
    private final Assignment toUnassign;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Assignment}.
     */
    public UnassignCommand(Name unassignee, Assignment assignment) {
        requireNonNull(unassignee);
        requireNonNull(assignment);
        this.unassignee = unassignee;
        this.toUnassign = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person target = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(unassignee))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_STUDENT_NOT_FOUND));

        // Defensive copy of assignments (avoid mutating internal Set)
        Set<Assignment> currentAssignments = new HashSet<>(target.getAssignments());

        boolean hasAssignment = currentAssignments.remove(toUnassign);
        if (!hasAssignment) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getTuitionClass(),
                target.getTags(),
                currentAssignments
        );

        model.setPerson(target, updatedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toUnassign.assignmentName, updatedPerson.getName()));
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

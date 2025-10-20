package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.logging.Logger;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.commands.exceptions.AssignmentMarkedException;
import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;



/**
 * Marks the assignment of a student as completed.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an assignment of an existing person. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "NAME OF ASSIGNMENT "
            + PREFIX_PERSON_NAME + "NAME OF ASSIGNEE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Tutorial 6 "
            + PREFIX_PERSON_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "Assignment: %1$s has been marked for: %2$s. ";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook. ";

    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);

    private final AssignmentName assignmentName;
    private final PersonName student;

    /**
     * Creates a MarkCommand with the specified {@code AssignmentName} and {@code PersonName}.
     */
    public MarkCommand(AssignmentName assignmentName, PersonName student) {
        requireNonNull(assignmentName);
        requireNonNull(student);
        this.assignmentName = assignmentName;
        this.student = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            logger.info("Attempting to mark assignment: " + assignmentName);
            Person student = model.findPersonByName(this.student, MESSAGE_STUDENT_NOT_FOUND);

            // throws AssignmentNotFoundException and AssignmentMarkedException, both are subtype of CommandException
            student.markAssignment(this.assignmentName);

            model.setPerson(student, student); //triggers rendering of UI
            logger.info(String.format(MESSAGE_SUCCESS, student, assignmentName));
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, this.assignmentName, student.getName())
            );
        } catch (PersonNotFoundException e) {
            logger.info("Marking failed, exception thrown:" + MESSAGE_STUDENT_NOT_FOUND);

            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        } catch (AssignmentMarkedException | AssignmentNotFoundException e) {
            logger.info("Marking failed, exception thrown:" + e.getMessage());

            throw new CommandException(e.getMessage());
        }
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

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return student.equals(otherMarkCommand.student)
                && assignmentName.equals(otherMarkCommand.assignmentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student", student)
                .add("assignmentName", assignmentName)
                .toString();
    }
}

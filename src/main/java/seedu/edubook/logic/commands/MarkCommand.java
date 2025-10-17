package seedu.edubook.logic.commands;

import seedu.edubook.model.Model;

import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

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
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in EduBook. ";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "Assignment does not exist for this student ";
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_USAGE);
    }
}

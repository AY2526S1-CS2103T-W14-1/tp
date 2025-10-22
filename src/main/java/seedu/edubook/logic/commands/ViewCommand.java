package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.function.Predicate;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.Messages;
import seedu.edubook.model.Model;
import seedu.edubook.model.assign.ClassTarget;
import seedu.edubook.model.assign.NameTarget;
import seedu.edubook.model.assign.Target;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;

/**
 * Finds and lists a person in address book with the specified name.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the details of a student or view the details of all the students in a class. "
            + "Parameters when viewing a single student: "
            + PREFIX_PERSON_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON_NAME + "John Doe"
            + "Parameters when viewing a class: "
            + PREFIX_CLASS + "CLASS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CLASS + "Class 1-B";

    public static final String MESSAGE_VIEW_STUDENT_SUCCESS =
            "Here are the details of %1$s.";
    public static final String MESSAGE_VIEW_CLASS_SUCCESS =
            "Here are the details of all the students in %1$s.";

    private final Target target;

    /**
     * Creates a {@code ViewCommand} to view student with specified {@code target}
     *
     * @param target The target to assign to (single student or class).
     */
    public ViewCommand(Target target) {
        requireNonNull(target);
        this.target = target;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (target instanceof NameTarget) {
            NameTarget nameTarget = (NameTarget) target;
            return viewByName(model, nameTarget);
        } else {
            ClassTarget classTarget = (ClassTarget) target;
            return viewByClass(model, classTarget);
        }
    }

    /**
     * Views a student identified by their name in the displayed person list.
     *
     * @param model {@code Model} which the ViewCommand should operate on.
     * @return {@code CommandResult} which will output the result of Command.
     */
    private CommandResult viewByName(Model model, NameTarget nameTarget) {
        PersonName name = nameTarget.getName();
        Predicate<Person> predicate = person -> person.getName().equals(name);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_INVALID_NAME));
        }
        return new CommandResult(
                String.format(MESSAGE_VIEW_STUDENT_SUCCESS, name));
    }

    /**
     * Views all the students in a class identified by their tuition class in the displayed person list.
     *
     * @param model {@code Model} which the ViewCommand should operate on.
     * @return {@code CommandResult} which will output the result of Command.
     */
    private CommandResult viewByClass(Model model, ClassTarget classTarget) {
        TuitionClass tuitionClass = classTarget.getTuitionClass();
        Predicate<Person> predicate = person -> person.getTuitionClass().equals(tuitionClass);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_INVALID_NAME));
        }
        return new CommandResult(
                String.format(MESSAGE_VIEW_CLASS_SUCCESS, tuitionClass));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;

        return target.equals(otherViewCommand.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .toString();
    }
}

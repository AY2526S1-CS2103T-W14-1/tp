package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.function.Predicate;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.Messages;
import seedu.edubook.model.Model;
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

    private final PersonName name;
    private final TuitionClass tuitionClass;

    /**
     * Creates a {@code ViewCommand} to view student with specified {@code name}
     *
     * @param name {@code name} of the student to view.
     */
    public ViewCommand(PersonName name) {
        this.name = name;
        tuitionClass = null;
    }

    /**
     * Creates a {@code ViewCommand} to view class with specified {@code tuitionClass}
     *
     * @param tuitionClass {@code tuitionClass} of the students to view.
     */
    public ViewCommand(TuitionClass tuitionClass) {
        this.tuitionClass = tuitionClass;
        this.name = null;
    }

    @Override
    public CommandResult execute(Model model) {
        if (this.tuitionClass == null) {
            return viewByName(model);
        } else {
            return viewByClass(model);
        }

    }

    /**
     * Views a student identified by their name in the displayed person list.
     *
     * @param model {@code Model} which the ViewCommand should operate on.
     * @return {@code CommandResult} which will output the result of Command.
     */
    public CommandResult viewByName(Model model) {
        requireNonNull(model);
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
    public CommandResult viewByClass(Model model) {
        requireNonNull(model);
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

        // Handle case where both use name
        if (name != null && otherViewCommand.name != null) {
            return name.equals(otherViewCommand.name);
        }

        // Handle case where both use class
        if (tuitionClass != null && otherViewCommand.tuitionClass != null) {
            return tuitionClass.equals(otherViewCommand.tuitionClass);
        }

        // Return false if one uses tuitionClass and one uses name.
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", name)
                .toString();
    }
}

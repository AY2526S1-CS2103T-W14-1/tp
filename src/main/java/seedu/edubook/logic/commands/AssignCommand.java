package seedu.edubook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Name;
import seedu.edubook.model.person.Person;

public class AssignCommand extends Command {
    
    public static final String COMMAND_WORD = "assign";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + "lmao";
    
    public static final String MESSAGE_SUCCESS = "New assignment assigned to: %1$s";
    // public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    // todo add more error messages after settling logic
    
    private final Name assignee;
    private final Assignment toAssign;
    
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AssignCommand(Name assignee, Assignment assignment) {
        requireNonNull(assignee);
        requireNonNull(assignment);
        this.assignee = assignee;
        this.toAssign = assignment;
    }
    
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        
        Person target = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(assignee))
                .findFirst()
                .orElseThrow(() -> new CommandException("hehehehehehehaw"));
        
        Set<Assignment> updatedAssignments = target.getAssignments();
        updatedAssignments.add(toAssign);
        
        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getTuitionClass(),
                target.getTags(),
                updatedAssignments
        );
        
        model.setPerson(target, updatedPerson);
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedPerson.getName()));
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
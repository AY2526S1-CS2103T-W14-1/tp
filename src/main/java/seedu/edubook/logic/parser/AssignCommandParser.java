package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.edubook.logic.commands.AddCommand;
import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Name;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;

public class AssignCommandParser implements Parser<AssignCommand> {
    
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_NAME);
        
        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_NAME, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_NAME);
        
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(argMultimap
                .getValue(PREFIX_ASSIGNMENT_NAME)
                .get()
        );
        Name personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        
        Assignment assignment = new Assignment(assignmentName, personName);
        
        return new AssignCommand(assignment);
    }
    
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

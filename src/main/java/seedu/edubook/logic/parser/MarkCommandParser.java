package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.stream.Stream;

import seedu.edubook.logic.commands.MarkCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;



/**
 * Parses input arguments and creates a new MarkCommand object.
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     *
     * @param args input string entered by the user.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME);

        AssignmentName assignmentName = ParserUtil.parseAssignmentName(argMultimap
                .getValue(PREFIX_ASSIGNMENT_NAME)
                .get());

        PersonName student = ParserUtil.parsePersonName(argMultimap
                .getValue(PREFIX_PERSON_NAME)
                .get());


        return new MarkCommand(assignmentName, student);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

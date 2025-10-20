package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.stream.Stream;

import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;

/**
 * Contains common parsing logic for marking and unmarking commands.
 */
public class MarkingCommandParserUtil {

    /**
     * Parses the given {@code String} of arguments and returns a pair of (AssignmentName, PersonName).
     *
     * @param args input string entered by the user.
     * @param messageUsage usage message of the calling command.
     * @return a ParsedPacket object containing the parsed assignment name and student name.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public static ParsedPacket parseAssignmentAndPerson(String args, String messageUsage)
            throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME);

        AssignmentName assignmentName = ParserUtil.parseAssignmentName(
                argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());

        PersonName student = ParserUtil.parsePersonName(
                argMultimap.getValue(PREFIX_PERSON_NAME).get());

        return new ParsedPacket(assignmentName, student);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /** Holder class for returning both parsed values together. */
    public static class ParsedPacket {
        public final AssignmentName assignmentName;
        public final PersonName student;

        /**
         * Constructs a new ParsedPacket containing AssignmentName and PersonName.
         */
        public ParsedPacket(AssignmentName assignmentName, PersonName student) {
            this.assignmentName = assignmentName;
            this.student = student;
        }
    }
}


package seedu.edubook.logic.parser;

import seedu.edubook.logic.commands.UnmarkCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkCommand object.
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns a UnmarkCommand object for execution.
     *
     * @param args input string entered by the user.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public UnmarkCommand parse(String args) throws ParseException {

        MarkingCommandParserUtil.ParsedPacket data =
                MarkingCommandParserUtil.parseAssignmentAndPerson(args, UnmarkCommand.MESSAGE_USAGE);

        assert data != null : "data should not be null";
        assert data.assignmentName != null : "data should contain assignmentName";
        assert data.student != null : "data should contain student";

        return new UnmarkCommand(data.assignmentName, data.student);
    }

}

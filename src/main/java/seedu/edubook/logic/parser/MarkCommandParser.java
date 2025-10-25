package seedu.edubook.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.edubook.logic.commands.MarkCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;

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
        requireNonNull(args);
        MarkingCommandParserUtil.ParsedPacket data =
                MarkingCommandParserUtil.parseAssignmentAndPerson(args, MarkCommand.MESSAGE_USAGE);

        assert data != null : "data should not be null";
        assert data.assignmentName != null : "data should contain assignmentName";
        assert data.student != null : "data should contain student";

        return new MarkCommand(data.assignmentName, data.student);
    }

}

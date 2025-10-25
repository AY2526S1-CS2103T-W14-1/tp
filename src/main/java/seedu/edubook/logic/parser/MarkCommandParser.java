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

        // Parse the assignment name and target (student or class)
        MarkingCommandParserUtil.ParsedPacket data =
                MarkingCommandParserUtil.parseAssignmentAndTarget(args, MarkCommand.MESSAGE_USAGE);

        assert data != null : "Parsed data should not be null";
        assert data.assignmentName != null : "Parsed data should contain assignmentName";
        assert data.target != null : "Parsed data should contain a non-null target";

        return new MarkCommand(data.assignmentName, data.target);
    }
}

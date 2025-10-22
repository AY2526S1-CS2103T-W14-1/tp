package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.model.assign.NameTarget;
import seedu.edubook.model.person.PersonName;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // no leading and trailing whitespaces
        PersonName name = new PersonName("Alice");
        NameTarget target = new NameTarget(name);
        ViewCommand expectedViewCommand =
                new ViewCommand(target);
        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME + name, expectedViewCommand);
    }
}

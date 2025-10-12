package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;


public class AssignCommandParserTest {
    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgs_returnsAssignCommand() throws Exception {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY,
                new AssignCommand(ASSIGNMENT_HOMEWORK, AMY.getName()));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // Missing assignment prefix
        assertThrows(ParseException.class, () -> parser.parse("Hello"));

        // Missing person prefix
        assertThrows(ParseException.class, () -> parser.parse(PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY,
                "Multiple values specified for the following single-valued field(s): a/");
    }

    @Test
    public void parse_invalidValues_failure() {
        // Invalid assignment name
        assertParseFailure(parser,
                " " + PREFIX_ASSIGNMENT_NAME + "!!!" + NAME_DESC_BOB,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // Invalid person name
        assertParseFailure(parser,
                ASSIGNMENT_DESC_HOMEWORK + " n/!!! ",
                PersonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

}

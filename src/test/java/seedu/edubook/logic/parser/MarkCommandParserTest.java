package seedu.edubook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.MarkCommand;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;



public class MarkCommandParserTest {

    // Messages for different duplicate prefix scenarios
    private static final String DUPLICATE_ASSIGNMENT_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_NAME);

    private static final String DUPLICATE_PERSON_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_NAME);

    private static final String DUPLICATE_BOTH_PREFIXES_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME);

    private MarkCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new MarkCommandParser();
    }

    @Test
    public void parse_personPrefix_success() {
        assertParseSuccess(parser,
                ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY,
                new MarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY));
    }

    @Test
    public void parse_classPrefix_success() {
        assertParseSuccess(parser,
                ASSIGNMENT_DESC_HOMEWORK + CLASS_DESC_AMY,
                new MarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY));
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_missingAssignmentPrefix_failure() {
        // Missing assignment prefix
        assertParseFailure(parser, NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPersonPrefix_failure() {
        // Missing person prefix
        assertParseFailure(parser, ASSIGNMENT_DESC_HOMEWORK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateAssignmentPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_TUTORIAL + NAME_DESC_AMY;
        assertParseFailure(parser, input, DUPLICATE_ASSIGNMENT_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicatePersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, DUPLICATE_PERSON_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicateAssignmentAndPersonPrefixes_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_TUTORIAL
                + NAME_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, DUPLICATE_BOTH_PREFIXES_MESSAGE);
    }

    @Test
    public void parse_invalidAssignmentName_failure() {
        // Invalid assignment name
        assertParseFailure(parser, INVALID_ASSIGNMENT_DESC + NAME_DESC_AMY,
                AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPersonName_failure() {
        // Invalid person name
        assertParseFailure(parser, ASSIGNMENT_DESC_HOMEWORK + INVALID_NAME_DESC,
                PersonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}

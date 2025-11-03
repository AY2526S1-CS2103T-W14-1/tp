package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;

public class ViewCommandParserTest {

    // Messages for different duplicate prefix scenarios
    private static final String DUPLICATE_ASSIGNMENT_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_NAME);

    private static final String DUPLICATE_PERSON_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_NAME);

    private static final String DUPLICATE_CLASS_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS);

    private static final String BENSON_ASSIGNMENT = " " + PREFIX_ASSIGNMENT_NAME + "Tutorial 1";

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTargetPrefix_failure() {
        // Missing assignment, person and class prefix
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateAssignmentPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_TUTORIAL;
        assertParseFailure(parser, input, DUPLICATE_ASSIGNMENT_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicatePersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + NAME_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, DUPLICATE_PERSON_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicateClassPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + CLASS_DESC_AMY + CLASS_DESC_BOB;;
        assertParseFailure(parser, input, DUPLICATE_CLASS_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicateAssignmentAndPersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + NAME_DESC_AMY + ASSIGNMENT_DESC_TUTORIAL;
        assertParseFailure(parser, input, Messages.MESSAGE_NAME_ASSIGNMENT_CONFLICTING_PREFIXES);
    }

    @Test
    public void parse_duplicateClassAndPersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + CLASS_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES);
    }

    @Test
    public void parse_duplicateClassAndAssignmentPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + CLASS_DESC_BOB;;
        assertParseFailure(parser, input, Messages.MESSAGE_CLASS_ASSIGNMENT_CONFLICTING_PREFIXES);
    }

    @Test
    public void parse_duplicateNameClassAndAssignmentPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + ASSIGNMENT_DESC_HOMEWORK + CLASS_DESC_BOB + NAME_DESC_AMY;;
        assertParseFailure(parser, input, Messages.MESSAGE_NAME_CLASS_ASSIGNMENT_CONFLICTING_PREFIXES);
    }

    @Test
    public void parse_invalidAssignmentName_failure() {
        // Invalid assignment name
        assertParseFailure(parser, INVALID_ASSIGNMENT_DESC,
                AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPersonName_failure() {
        // Invalid person name
        assertParseFailure(parser, INVALID_NAME_DESC,
                PersonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidClass_failure() {
        String input = " c/";
        assertParseFailure(parser, input, TuitionClass.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + NAME_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_personPrefix_success() {
        assertParseSuccess(parser,
                NAME_DESC_AMY,
                new ViewCommand(NAME_TARGET_AMY));
    }

    @Test
    public void parse_classPrefix_success() {
        assertParseSuccess(parser,
                CLASS_DESC_AMY,
                new ViewCommand(CLASS_TARGET_AMY));
    }

    @Test
    public void parse_assignmentPrefix_success() {
        assertParseSuccess(parser,
                BENSON_ASSIGNMENT,
                new ViewCommand(ASSIGNMENT_TARGET_BENSON));
    }
}

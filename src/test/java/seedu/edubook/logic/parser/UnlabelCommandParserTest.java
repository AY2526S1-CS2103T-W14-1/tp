package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.UnlabelCommand;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;

class UnlabelCommandParserTest {

    // Messages for different duplicate prefix scenarios
    private static final String DUPLICATE_PERSON_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_NAME);

    private static final String DUPLICATE_CLASS_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_CLASS);

    private UnlabelCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new UnlabelCommandParser();
    }

    @Test
    public void parse_personPrefix_success() {
        assertParseSuccess(parser,
                NAME_DESC_AMY,
                new UnlabelCommand(NAME_TARGET_AMY));
    }

    @Test
    public void parse_classPrefix_success() {
        assertParseSuccess(parser,
                CLASS_DESC_AMY,
                new UnlabelCommand(CLASS_TARGET_AMY));
    }

    @Test
    public void parse_missingTargetPrefix_failure() {
        // Missing both person and class prefixes
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + NAME_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, DUPLICATE_PERSON_PREFIX_MESSAGE);
    }

    @Test
    public void parse_duplicateClassPrefix_failure() {
        String input = " c/A c/B";
        assertParseFailure(parser, input, DUPLICATE_CLASS_PREFIX_MESSAGE);
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
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}

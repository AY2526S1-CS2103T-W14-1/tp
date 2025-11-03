package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalPersonNames.NAME_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.DeleteCommand;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.target.NameTarget;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    // Messages for different duplicate prefix scenarios
    private static final String DUPLICATE_PERSON_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_NAME);

    private static final String DUPLICATE_CLASS_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS);

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsName_returnsDeleteCommand() {
        assertParseSuccess(parser, " n/Alice Pauline", new DeleteCommand(new NameTarget(NAME_FIRST_PERSON)));
    }

    @Test
    public void parse_invalidArgsName_returnDeleteCommand() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nameWithPreamble_throwsParseException() {
        assertParseFailure(parser, "some text n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
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
    public void parse_duplicateClassAndPersonPrefix_failure() {
        String input = PREAMBLE_WHITESPACE + CLASS_DESC_AMY + NAME_DESC_BOB;
        assertParseFailure(parser, input, Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES);
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
    public void parse_personPrefix_success() {
        assertParseSuccess(parser,
                NAME_DESC_AMY,
                new DeleteCommand(NAME_TARGET_AMY));
    }

    @Test
    public void parse_classPrefix_success() {
        assertParseSuccess(parser,
                CLASS_DESC_AMY,
                new DeleteCommand(CLASS_TARGET_AMY));
    }
}

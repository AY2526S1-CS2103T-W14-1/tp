package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.*;
import static seedu.edubook.logic.parser.CliSyntax.*;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalLabel.LABEL_GOOD;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.commands.LabelCommand;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;


public class LabelCommandParserTest {

    // Messages for different duplicate prefix scenarios
    private static final String DUPLICATE_ASSIGNMENT_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LABEL);

    private static final String DUPLICATE_PERSON_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_NAME);

    /*private static final String DUPLICATE_CLASS_PREFIX_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_CLASS);*/

    private static final String DUPLICATE_BOTH_PREFIXES_MESSAGE =
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LABEL, PREFIX_PERSON_NAME);

    private LabelCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new LabelCommandParser();
    }

    @Test
    public void parse_personPrefix_success() {
        assertParseSuccess(parser,
                LABEL_DESC_GOOD + NAME_DESC_AMY,
                new LabelCommand(LABEL_GOOD, NAME_TARGET_AMY));
    }
    /*
    @Test
    public void parse_classPrefix_success() {
        assertParseSuccess(parser,
                ASSIGNMENT_DESC_HOMEWORK + CLASS_DESC_AMY,
                new AssignCommand(ASSIGNMENT_HOMEWORK, CLASS_TARGET_AMY));
    }
    */
    @Test
    public void parse_missingLabelPrefix_failure() {
        // Missing assignment prefix
        assertParseFailure(parser, NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTargetForLabelPrefix_failure() {
        // Missing both person and class prefixes
        assertParseFailure(parser, LABEL_DESC_GOOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE));
    }
    /*
    @Test
    public void parse_allPrefixesPresent_failure() {
        String input = " a/Homework 2 n/Amy c/A";
        assertParseFailure(parser, input,
                "Specify only n/NAME or c/CLASS, not both.");
    }
    */

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
    public void parse_duplicateClassPrefix_failure() {
        String input = " a/Homework 2 c/A c/B";
        assertParseFailure(parser, input, DUPLICATE_CLASS_PREFIX_MESSAGE);
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
    public void parse_invalidClass_failure() {
        String input = " a/Homework 2 c/";
        assertParseFailure(parser, input, TuitionClass.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}

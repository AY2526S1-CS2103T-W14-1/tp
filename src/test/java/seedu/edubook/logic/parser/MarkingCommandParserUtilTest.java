package seedu.edubook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_VALID_STUDENT;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.parser.MarkingCommandParserUtil.ParsedPacket;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;


public class MarkingCommandParserUtilTest {

    private static final String MESSAGE_USAGE = "dummy";

    @Test
    public void parseAssignmentAndTarget_validPerson_success() throws Exception {
        MarkingCommandParserUtil.ParsedPacket packet =
                MarkingCommandParserUtil.parseAssignmentAndTarget(ASSIGNMENT_DESC_TUTORIAL + NAME_DESC_AMY,
                        MESSAGE_USAGE);

        assertEquals(ASSIGNMENT_TUTORIAL.assignmentName, packet.assignmentName);
        assertEquals(NAME_TARGET_AMY, packet.target);
    }

    @Test
    public void parseAssignmentAndTarget_validClass_success() throws Exception {
        MarkingCommandParserUtil.ParsedPacket packet =
                MarkingCommandParserUtil.parseAssignmentAndTarget(ASSIGNMENT_DESC_TUTORIAL + CLASS_DESC_AMY,
                        MESSAGE_USAGE);

        assertEquals(ASSIGNMENT_TUTORIAL.assignmentName, packet.assignmentName);
        assertEquals(CLASS_TARGET_AMY, packet.target);
    }

    @Test
    public void parseAssignmentAndTarget_missingAssignmentPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(NAME_DESC_AMY, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_missingTargetPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(ASSIGNMENT_DESC_TUTORIAL, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_conflictingTargetPrefixes_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(
                        ASSIGNMENT_DESC_TUTORIAL + NAME_DESC_AMY + CLASS_DESC_AMY, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_extraPreamble_throwsParseException() {
        String args = PREAMBLE_NON_EMPTY + VALID_ASSIGNMENT_VALID_STUDENT;

        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(args, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_duplicatePrefixes_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(
                        ASSIGNMENT_DESC_TUTORIAL + NAME_DESC_AMY + NAME_DESC_AMY, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_DUPLICATE_FIELDS, MESSAGE_USAGE)));
    }

    @Test
    public void parseNullArgs_throwsAssertionError() {
        AssertionError e = assertThrows(AssertionError.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(null, MESSAGE_USAGE));

        assertEquals("args should not be null", e.getMessage());
    }

    @Test
    public void parseNullMessageUsage_throwsAssertionError() {
        AssertionError e = assertThrows(AssertionError.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget(ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY, null));

        assertEquals("messageUsage should not be null", e.getMessage());
    }

    @Test
    public void parseEmptyArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndTarget("", MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parsedPacket_equalsFieldsCorrectly() {
        AssignmentName name = new AssignmentName("Essay");
        PersonName student = new PersonName("Bob");
        ParsedPacket packet = new ParsedPacket(name, student);

        assertSame(name, packet.assignmentName);
        assertSame(student, packet.person);
    }
}

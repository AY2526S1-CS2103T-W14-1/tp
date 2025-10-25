package seedu.edubook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_VALID_STUDENT;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.parser.MarkingCommandParserUtil.ParsedPacket;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;



public class MarkingCommandParserUtilTest {

    private static final String MESSAGE_USAGE = "dummy";

    @Test
    public void parseAssignmentAndPerson_validArgs_returnsParsedPacket() throws Exception {
        ParsedPacket result =
                MarkingCommandParserUtil.parseAssignmentAndPerson(VALID_ASSIGNMENT_VALID_STUDENT, MESSAGE_USAGE);

        assertEquals(new AssignmentName(VALID_ASSIGNMENT_HOMEWORK), result.assignmentName);
        assertEquals(new PersonName(VALID_NAME_AMY), result.person);
    }

    @Test
    public void parseAssignmentAndPerson_missingAssignmentPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson(NAME_DESC_AMY, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_missingPersonPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson(ASSIGNMENT_DESC_TUTORIAL, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseAssignmentAndPerson_extraPreamble_throwsParseException() {
        String args = PREAMBLE_NON_EMPTY + VALID_ASSIGNMENT_VALID_STUDENT;

        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson(args, MESSAGE_USAGE));

        assertTrue(exception.getMessage().contains(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
    }

    @Test
    public void parseNullArgs_throwsAssertionError() {
        AssertionError e = assertThrows(AssertionError.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson(null, MESSAGE_USAGE));

        assertEquals("args should not be null", e.getMessage());
    }

    @Test
    public void parseNullMessageUsage_throwsAssertionError() {
        AssertionError e = assertThrows(AssertionError.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson(VALID_ASSIGNMENT_VALID_STUDENT, null));

        assertEquals("messageUsage should not be null", e.getMessage());
    }

    @Test
    public void parseEmptyArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                MarkingCommandParserUtil.parseAssignmentAndPerson("", MESSAGE_USAGE));

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

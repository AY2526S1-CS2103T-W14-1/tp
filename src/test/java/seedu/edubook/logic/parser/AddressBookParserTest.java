package seedu.edubook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.AddCommand;
import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.commands.ClearCommand;
import seedu.edubook.logic.commands.DeleteCommand;
import seedu.edubook.logic.commands.EditCommand;
import seedu.edubook.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.edubook.logic.commands.ExitCommand;
import seedu.edubook.logic.commands.FindCommand;
import seedu.edubook.logic.commands.HelpCommand;
import seedu.edubook.logic.commands.ListCommand;
import seedu.edubook.logic.commands.UnassignCommand;
import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.PersonNameContainsKeywordsPredicate;
import seedu.edubook.testutil.EditPersonDescriptorBuilder;
import seedu.edubook.testutil.PersonBuilder;
import seedu.edubook.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_assign() throws Exception {
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(VALID_ASSIGNMENT_HOMEWORK);
        Assignment assignment = new Assignment(assignmentName);
        PersonName personName = ParserUtil.parsePersonName(VALID_NAME_AMY);

        AssignCommand command = (AssignCommand) parser.parseCommand(
                AssignCommand.COMMAND_WORD + ASSIGNMENT_DESC_HOMEWORK + NAME_DESC_AMY);

        assertEquals(new AssignCommand(assignment, personName), command);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(VALID_ASSIGNMENT_TUTORIAL);
        Assignment assignment = new Assignment(assignmentName);
        PersonName personName = ParserUtil.parsePersonName(VALID_NAME_BOB);

        UnassignCommand command = (UnassignCommand) parser.parseCommand(
                UnassignCommand.COMMAND_WORD + ASSIGNMENT_DESC_TUTORIAL + NAME_DESC_BOB);

        assertEquals(new UnassignCommand(assignment, personName), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_view() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewCommand(new PersonNameContainsKeywordsPredicate(keywords)), command);
    }
}

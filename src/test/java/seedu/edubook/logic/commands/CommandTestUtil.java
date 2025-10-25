package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edubook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.AddressBook;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.PersonNameContainsKeywordsPredicate;
import seedu.edubook.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_CLASS_AMY = "Class A";
    public static final String VALID_CLASS_BOB = "Class B";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_ASSIGNMENT_HOMEWORK = "Homework 2";
    public static final String VALID_ASSIGNMENT_TUTORIAL = "Tutorial 2";

    public static final String NAME_DESC_AMY = " " + PREFIX_PERSON_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_PERSON_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String CLASS_DESC_AMY = " " + PREFIX_CLASS + VALID_CLASS_AMY;
    public static final String CLASS_DESC_BOB = " " + PREFIX_CLASS + VALID_CLASS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ASSIGNMENT_DESC_HOMEWORK = " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_HOMEWORK;
    public static final String ASSIGNMENT_DESC_TUTORIAL = " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_TUTORIAL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_PERSON_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_CLASS_DESC = " " + PREFIX_CLASS; // empty string not allowed for classes
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ASSIGNMENT_DESC =
            " " + PREFIX_ASSIGNMENT_NAME + "Homework!1"; // '!' not allowed in assignment names

    public static final String INVALID_CLASS_LENGTH =
            " " + PREFIX_CLASS + String.join("", java.util.Collections.nCopies(10, "test"));
    public static final String INVALID_EMAIL_LENGTH =
            " " + PREFIX_EMAIL + String.join("", java.util.Collections.nCopies(80, "test"))
                    + "@example.com";
    public static final String INVALID_NAME_LENGTH =
            " " + PREFIX_PERSON_NAME + String.join("", java.util.Collections.nCopies(50, "test"));
    public static final String INVALID_PHONE_LENGTH =
            " " + PREFIX_PHONE + String.join("", java.util.Collections.nCopies(10, "1234"));

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_ASSIGNMENT_VALID_STUDENT =
           " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_HOMEWORK + " " + PREFIX_PERSON_NAME + VALID_NAME_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withClass(VALID_CLASS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withClass(VALID_CLASS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            System.out.println(actualModel.getAddressBook());
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new PersonNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person with the given {@code targetName} in the
     *
     * @param model
     * @param targetName
     */
    public static void showPersonAtName(Model model, PersonName targetName) {
        // Filter to show only persons with the exact name
        model.updateFilteredPersonList(person -> person.getName().equals(targetName));

        // Verify that exactly one person with that name exists
        assertEquals(1, model.getFilteredPersonList().size());

        // Optional: Verify it's the right person
        Person foundPerson = model.getFilteredPersonList().get(0);
        assertEquals(targetName, foundPerson.getName());
    }

}

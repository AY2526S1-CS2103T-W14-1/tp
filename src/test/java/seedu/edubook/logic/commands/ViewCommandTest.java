package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.UserPrefs;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;


public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonName firstName = new PersonName("first");
        PersonName secondName = new PersonName("second");

        ViewCommand viewFirstCommand = new ViewCommand(firstName);
        ViewCommand viewSecondCommand = new ViewCommand(secondName);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand findFirstCommandCopy = new ViewCommand(firstName);
        assertTrue(viewFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_noMatchingName_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonName name = new PersonName("Jake");
        ViewCommand command = new ViewCommand(name);
        expectedModel.updateFilteredPersonList(preparePredicate(name));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_matchingName_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonName name = new PersonName("Alice Pauline");
        ViewCommand command = new ViewCommand(name);
        expectedModel.updateFilteredPersonList(preparePredicate(name));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonName name = new PersonName("Alice Pauline");
        ViewCommand command = new ViewCommand(name);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + name.toString() + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private Predicate<Person> preparePredicate(PersonName name) {
        return person -> person.getName().equals(name);
    }
}

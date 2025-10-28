package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edubook.logic.commands.ViewCommand.MESSAGE_VIEW_STUDENT_SUCCESS;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.UserPrefs;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.NameTarget;

public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonName firstName = new PersonName("first");
        PersonName secondName = new PersonName("second");

        NameTarget firstTarget = new NameTarget(firstName);
        NameTarget secondTarget = new NameTarget(secondName);

        ViewCommand viewFirstCommand = new ViewCommand(firstTarget);
        ViewCommand viewSecondCommand = new ViewCommand(secondTarget);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand findFirstCommandCopy = new ViewCommand(firstTarget);
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
        PersonName name = new PersonName("Jake");
        String expectedMessage = String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, name);
        NameTarget target = new NameTarget(name);
        ViewCommand command = new ViewCommand(target);
        expectedModel.updateFilteredPersonList(preparePredicate(name));
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_matchingName_personFound() {
        String expectedMessage = String.format(MESSAGE_VIEW_STUDENT_SUCCESS, "Alice Pauline");
        PersonName name = new PersonName("Alice Pauline");
        NameTarget target = new NameTarget(name);
        ViewCommand command = new ViewCommand(target);
        expectedModel.updateFilteredPersonList(preparePredicate(name));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonName name = new PersonName("Alice Pauline");
        NameTarget target = new NameTarget(name);
        ViewCommand command = new ViewCommand(target);
        String expected = ViewCommand.class.getCanonicalName() + "{target=" + target.toString() + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private Predicate<Person> preparePredicate(PersonName name) {
        return person -> person.getName().equals(name);
    }
}

package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edubook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.edubook.logic.commands.CommandTestUtil.showPersonAtName;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edubook.testutil.TypicalNames.NAME_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalNames.NAME_SECOND_PERSON;
import static seedu.edubook.testutil.TypicalNames.NAME_THIRD_PERSON;
import static seedu.edubook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.logic.Messages;
import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.UserPrefs;
import seedu.edubook.model.person.Name;
import seedu.edubook.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validNameUnfilteredList_success() {
        //Initialise to first person
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        for (Person p : model.getFilteredPersonList()) {
            if (p.getName().equals(NAME_FIRST_PERSON)) {
                personToDelete = p;
            }
        }
        DeleteCommand deleteCommand = new DeleteCommand(NAME_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name invalidName = new Name("John Doe");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtName(model, NAME_FIRST_PERSON);

        //Initialise to first person
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        for (Person p : model.getFilteredPersonList()) {
            if (p.getName().equals(NAME_FIRST_PERSON)) {
                personToDelete = p;
            }
        }
        DeleteCommand deleteCommand = new DeleteCommand(NAME_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showPersonAtName(model, NAME_FIRST_PERSON);

        Name invalidName = NAME_SECOND_PERSON;
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteThirdCommand = new DeleteCommand(NAME_FIRST_PERSON);
        DeleteCommand deleteFourthCommand = new DeleteCommand(NAME_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteThirdCommand.equals(deleteThirdCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeleteCommand deleteThirdCommandCopy = new DeleteCommand(NAME_FIRST_PERSON);
        assertTrue(deleteThirdCommand.equals(deleteThirdCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteThirdCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteThirdCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteThirdCommand.equals(deleteFourthCommand));

        // different types of delete -> returns false
        assertFalse(deleteFirstCommand.equals(deleteThirdCommand));
    }

    @Test
    public void toStringMethodIndex() {
        // When DeleteCommand using Index
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toStringMethodName() {
        // When DeleteCommand using Name
        Name targetName = NAME_THIRD_PERSON;
        DeleteCommand deleteCommand = new DeleteCommand(targetName);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

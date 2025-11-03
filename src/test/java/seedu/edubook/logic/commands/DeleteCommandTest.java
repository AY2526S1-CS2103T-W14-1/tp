package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edubook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.edubook.logic.commands.CommandTestUtil.showPersonAtName;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalPersonNames.NAME_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalPersonNames.NAME_SECOND_PERSON;
import static seedu.edubook.testutil.TypicalPersonNames.NAME_THIRD_PERSON;
import static seedu.edubook.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.UserPrefs;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;

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
                personToDelete.getName());

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

        DeleteCommand deleteCommand = new DeleteCommand(new NameTarget(NAME_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonNameUnfilteredList_throwsCommandException() {
        PersonName invalidName = new PersonName("John Doe");
        DeleteCommand deleteCommand = new DeleteCommand(new NameTarget(invalidName));

        assertCommandFailure(deleteCommand, model,
                String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_validClassUnfilteredList_success() throws CommandException {
        DeleteCommand command = new DeleteCommand(CLASS_TARGET_BENSON);
        String expectedMessage = String.format(
                CLASS_TARGET_BENSON.getDeleteSuccessMessage(), CLASS_TARGET_BENSON.getDisplayName());

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        List<Person> studentsToDelete = CLASS_TARGET_BENSON.getPersons(model);

        for (Person p : studentsToDelete) {
            expectedModel.deletePerson(p);
        }

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentClass_throwsCommandException() {
        String expectedMessage = String.format(
                ClassTarget.MESSAGE_NO_STUDENTS_FOUND, CLASS_TARGET_NONEXISTENT.getDisplayName());

        DeleteCommand command = new DeleteCommand(CLASS_TARGET_NONEXISTENT);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName());

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
        DeleteCommand deleteCommand = new DeleteCommand(new NameTarget(NAME_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        NameTarget first = new NameTarget(NAME_FIRST_PERSON);
        NameTarget second = new NameTarget(NAME_SECOND_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteThirdCommand = new DeleteCommand(first);
        DeleteCommand deleteFourthCommand = new DeleteCommand(second);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteThirdCommand.equals(deleteThirdCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeleteCommand deleteThirdCommandCopy = new DeleteCommand(first);
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
    public void equals_nameTarget() {
        DeleteCommand deleteAmy1 = new DeleteCommand(NAME_TARGET_AMY);
        DeleteCommand deleteBenson = new DeleteCommand(NAME_TARGET_BENSON);
        DeleteCommand deleteAmy2 = new DeleteCommand(NAME_TARGET_AMY);

        // same object -> true
        assertEquals(deleteAmy1, deleteAmy2);

        // different types -> false
        assertNotEquals(1, deleteAmy1);

        // null -> false
        assertNotEquals(null, deleteAmy1);

        // same assignment but different person name -> false
        assertNotEquals(deleteBenson, deleteAmy1);
    }

    @Test
    public void equals_classTarget() {
        DeleteCommand deleteClassA1 = new DeleteCommand(CLASS_TARGET_AMY);
        DeleteCommand deleteClassA2 = new DeleteCommand(CLASS_TARGET_AMY);
        DeleteCommand deleteClassB = new DeleteCommand(CLASS_TARGET_BENSON);

        // same object -> true
        assertEquals(deleteClassA1, deleteClassA2);

        // different class target -> false
        assertNotEquals(deleteClassA1, deleteClassB);

        // different type -> false
        assertNotEquals(1, deleteClassA1);

        // null -> false
        assertNotEquals(null, deleteClassA1);
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
        NameTarget targetName = new NameTarget(NAME_THIRD_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(targetName);
        String expected = DeleteCommand.class.getCanonicalName() + "{target=" + targetName + "}";
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

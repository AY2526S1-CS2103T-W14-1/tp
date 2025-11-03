package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edubook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_JANE;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalPersons.BENSON;
import static seedu.edubook.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.UserPrefs;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.AssignmentTarget;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

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
    public void execute_nonExistentPerson_throwsCommandException() {
        String expectedMessage = String.format(
                NameTarget.MESSAGE_PERSON_NOT_FOUND, NAME_TARGET_NONEXISTENT.getDisplayName());

        ViewCommand command = new ViewCommand(NAME_TARGET_NONEXISTENT);
        assertCommandFailure(command, expectedModel, expectedMessage);
    }

    @Test
    public void execute_nameTarget_success() throws CommandException {
        ViewCommand command = new ViewCommand(NAME_TARGET_BENSON);
        String expectedMessage = String.format(
                NAME_TARGET_BENSON.getViewSuccessMessage(), NAME_TARGET_BENSON.getDisplayName());

        expectedModel.updateFilteredPersonList(preparePredicate(NAME_TARGET_BENSON));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonExistentClass_throwsCommandException() {
        String expectedMessage = String.format(
                ClassTarget.MESSAGE_NO_STUDENTS_FOUND, CLASS_TARGET_NONEXISTENT.getDisplayName());

        ViewCommand command = new ViewCommand(CLASS_TARGET_NONEXISTENT);

        assertCommandFailure(command, expectedModel, expectedMessage);
    }

    @Test
    public void execute_classTarget_success() throws CommandException {
        ViewCommand command = new ViewCommand(CLASS_TARGET_BENSON);
        String expectedMessage = String.format(
                CLASS_TARGET_BENSON.getViewSuccessMessage(), CLASS_TARGET_BENSON.getDisplayName());

        expectedModel.updateFilteredPersonList(preparePredicate(CLASS_TARGET_BENSON));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonExistentAssignment_throwsCommandException() {
        String expectedMessage = String.format(
                AssignmentTarget.MESSAGE_NO_ASSIGNMENT_FOUND, ASSIGNMENT_TARGET_NONEXISTENT.getDisplayName());

        ViewCommand command = new ViewCommand(ASSIGNMENT_TARGET_NONEXISTENT);

        assertCommandFailure(command, expectedModel, expectedMessage);
    }

    @Test
    public void execute_assignmentTarget_success() throws CommandException {
        ViewCommand command = new ViewCommand(ASSIGNMENT_TARGET_BENSON);
        String expectedMessage = String.format(
                ASSIGNMENT_TARGET_BENSON.getViewSuccessMessage(), ASSIGNMENT_TARGET_BENSON.getDisplayName());

        expectedModel.updateFilteredPersonList(preparePredicate(ASSIGNMENT_TARGET_BENSON));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Target target = NAME_TARGET_BENSON;
        ViewCommand command = new ViewCommand(target);
        String expected = ViewCommand.class.getCanonicalName() + "{target=" + target.toString() + "}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void equals_nameTarget() {
        ViewCommand viewAmy1 = new ViewCommand(NAME_TARGET_AMY);
        ViewCommand viewBenson = new ViewCommand(NAME_TARGET_BENSON);
        ViewCommand viewAmy2 = new ViewCommand(NAME_TARGET_AMY);

        // same object -> true
        assertEquals(viewAmy1, viewAmy2);

        // different types -> false
        assertNotEquals(1, viewAmy1);

        // null -> false
        assertNotEquals(null, viewAmy1);

        // same assignment but different person name -> false
        assertNotEquals(viewBenson, viewAmy1);
    }

    @Test
    public void equals_classTarget() {
        ViewCommand viewClassA1 = new ViewCommand(CLASS_TARGET_AMY);
        ViewCommand viewClassA2 = new ViewCommand(CLASS_TARGET_AMY);
        ViewCommand viewClassB = new ViewCommand(CLASS_TARGET_BENSON);

        // same object -> true
        assertEquals(viewClassA1, viewClassA2);

        // different class target -> false
        assertNotEquals(viewClassA1, viewClassB);

        // different type -> false
        assertNotEquals(1, viewClassA1);

        // null -> false
        assertNotEquals(null, viewClassA1);
    }

    @Test
    public void equals_assignmentTarget() {
        ViewCommand viewAssignmentB1 = new ViewCommand(ASSIGNMENT_TARGET_BENSON);
        ViewCommand viewAssignmentB2 = new ViewCommand(ASSIGNMENT_TARGET_BENSON);
        ViewCommand viewAssignmentJ = new ViewCommand(ASSIGNMENT_TARGET_JANE);

        // same object -> true
        assertEquals(viewAssignmentB2, viewAssignmentB1);

        // different class target -> false
        assertNotEquals(viewAssignmentB1, viewAssignmentJ);

        // different type -> false
        assertNotEquals(1, viewAssignmentB1);

        // null -> false
        assertNotEquals(null, viewAssignmentB1);
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private Predicate<Person> preparePredicate(Target target) throws CommandException {
        List<Person> studentsToView = target.getPersons(model);
        return studentsToView::contains;
    }
}

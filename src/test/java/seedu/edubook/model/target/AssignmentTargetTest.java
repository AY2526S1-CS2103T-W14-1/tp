package seedu.edubook.model.target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalAssignmentTargets.ASSIGNMENT_TARGET_JANE;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Person;

public class AssignmentTargetTest {

    /** Template for unexpected method call on AssignmentTarget. */
    private static final String MESSAGE_UNEXPECTED_CALL =
            "Error: getSuccessMessage should not be called on AssignmentTarget";

    @Test
    public void getPersons_noStudents_throwsCommandException() {
        ModelStub model = new ModelStub();

        CommandException e = assertThrows(CommandException.class, () -> ASSIGNMENT_TARGET_BENSON.getPersons(model));
        assertTrue(e.getMessage().contains("No students found with assignment "));
    }

    @Test
    public void getPersons_withStudents_returnsList() throws CommandException {
        ModelStub model = new ModelStub();
        model.addPerson(BENSON);

        List<Person> result = ASSIGNMENT_TARGET_BENSON.getPersons(model);

        assertEquals(1, result.size());
        assertTrue(result.contains(BENSON));
    }

    @Test
    public void getDisplayName_returnsTuitionClassName() {
        assertEquals("Tutorial 1", ASSIGNMENT_TARGET_BENSON.getDisplayName());
    }

    @Test
    public void isSinglePersonTarget_returnsFalse() {
        assertFalse(CLASS_TARGET_BENSON.isSinglePersonTarget());
    }

    @Test
    public void getAssignSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getAssignSuccessMessage("Tutorial 1", 3, 1);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getUnassignSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getUnassignSuccessMessage("Tutorial 1", 2, 0);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getMarkSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getMarkSuccessMessage("Tutorial 1" , 1, 0, 0);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getUnmarkSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getUnmarkSuccessMessage("Tutorial 1", 1, 0, 0);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getLabelSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getLabelSuccessMessage("Top Student", 1, 0);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getUnlabelSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getUnlabelSuccessMessage(1, 0);

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void getViewSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getViewSuccessMessage();

        assertEquals("Here are the details of all students with assignment \"Tutorial 1\": ", result);
    }

    @Test
    public void getDeleteSuccessMessage_formatsCorrectly() {
        String result = ASSIGNMENT_TARGET_BENSON.getDeleteSuccessMessage();

        assertEquals(MESSAGE_UNEXPECTED_CALL, result);
    }

    @Test
    public void equals() {
        Target assignmentTargetBenson1 = ASSIGNMENT_TARGET_BENSON;
        Target assignmentTargetBenson2 = new AssignmentTarget(new AssignmentName("Tutorial 1"));

        // same object -> returns true
        assertEquals(assignmentTargetBenson1, assignmentTargetBenson1);

        // same values -> returns true
        assertEquals(assignmentTargetBenson1, assignmentTargetBenson2);

        // different values -> returns false
        assertNotEquals(ASSIGNMENT_TARGET_JANE, assignmentTargetBenson1);

        // null -> returns false
        assertNotEquals(assignmentTargetBenson1, null);

        // different type -> returns false
        assertNotEquals("Hi I'm a string", assignmentTargetBenson1);
    }

    @Test
    public void hashCodeTest() {
        Target assignmentTargetBenson1 = ASSIGNMENT_TARGET_BENSON;
        Target assignmentTargetBenson2 = new AssignmentTarget(new AssignmentName("Tutorial 1"));

        // same object -> same hash code
        assertEquals(assignmentTargetBenson1.hashCode(), assignmentTargetBenson1.hashCode());

        // equal objects -> same hash code
        assertEquals(assignmentTargetBenson1.hashCode(), assignmentTargetBenson2.hashCode());

        // different objects -> likely different hash code
        assertNotEquals(assignmentTargetBenson1.hashCode(), ASSIGNMENT_TARGET_JANE.hashCode());
    }

    @Test
    public void toStringMethod() {
        String result = ASSIGNMENT_TARGET_BENSON.toString();
        assertTrue(result.contains("AssignmentTarget"));
        assertTrue(result.contains("Tutorial 1"));
    }

    private static class ModelStub extends ModelManager {
        private final List<Person> personsWithAssignment = new ArrayList<>();

        public void addPerson(Person person) {
            personsWithAssignment.add(person);
        }

        @Override
        public List<Person> findPersonsByAssignmentName(AssignmentName name) {
            return personsWithAssignment;
        }
    }
}

package seedu.edubook.model.assign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.TuitionClass;

public class ClassTargetTest {

    @Test
    public void getPersons_noStudents_throwsCommandException() {
        ModelStub model = new ModelStub();

        CommandException e = assertThrows(CommandException.class, () -> CLASS_TARGET_AMY.getPersons(model));
        assertTrue(e.getMessage().contains("No students found in class"));
    }

    @Test
    public void getPersons_withStudents_returnsList() throws CommandException {
        ModelStub model = new ModelStub();
        model.addPerson(AMY);
        model.addPerson(BENSON);

        List<Person> result = CLASS_TARGET_AMY.getPersons(model);

        assertEquals(2, result.size());
        assertTrue(result.contains(AMY));
        assertTrue(result.contains(BENSON));
    }

    @Test
    public void getDisplayName_returnsTuitionClassName() {
        assertEquals("Class A", CLASS_TARGET_AMY.getDisplayName());
    }

    @Test
    public void isSinglePersonTarget_returnsFalse() {
        assertFalse(CLASS_TARGET_BENSON.isSinglePersonTarget());
    }

    @Test
    public void getAssignSuccessMessage_formatsCorrectly() {
        String result = CLASS_TARGET_AMY.getAssignSuccessMessage("Tutorial 1", 3, 1);

        assertEquals("New assignment 'Tutorial 1' assigned to class: 'Class A' (3 assigned, 1 skipped).", result);
    }

    @Test
    public void getUnassignSuccessMessage_formatsCorrectly() {
        String result = CLASS_TARGET_AMY.getUnassignSuccessMessage("Tutorial 1", 2, 0);

        assertEquals("Assignment 'Tutorial 1' unassigned from class: 'Class A' (2 unassigned, 0 skipped).", result);
    }

    @Test
    public void equals() {
        Target classTargetAmy1 = CLASS_TARGET_AMY;
        Target classTargetAmy2 = new ClassTarget(new TuitionClass("Class A"));

        // same object -> returns true
        assertEquals(classTargetAmy1, classTargetAmy1);

        // same values -> returns true
        assertEquals(classTargetAmy1, classTargetAmy2);

        // different values -> returns false
        assertNotEquals(CLASS_TARGET_BENSON, classTargetAmy1);

        // null -> returns false
        assertNotEquals(classTargetAmy1, null);

        // different type -> returns false
        assertNotEquals("Hi I'm a string", classTargetAmy1);
    }

    @Test
    public void hashCodeTest() {
        Target classTargetAmy1 = CLASS_TARGET_AMY;
        Target classTargetAmy2 = new ClassTarget(new TuitionClass("Class A"));

        // same object -> same hash code
        assertEquals(classTargetAmy1.hashCode(), classTargetAmy1.hashCode());

        // equal objects -> same hash code
        assertEquals(classTargetAmy1.hashCode(), classTargetAmy2.hashCode());

        // different objects -> likely different hash code
        assertNotEquals(classTargetAmy1.hashCode(), CLASS_TARGET_BENSON.hashCode());
    }

    @Test
    public void toStringMethod() {
        String result = CLASS_TARGET_AMY.toString();
        assertTrue(result.contains("ClassTarget"));
        assertTrue(result.contains("Class A"));
    }

    private static class ModelStub extends ModelManager {
        private final List<Person> personsInClass = new ArrayList<>();

        public void addPerson(Person person) {
            personsInClass.add(person);
        }

        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            return personsInClass;
        }
    }
}

package seedu.edubook.model.target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalPersons.AMY;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;

public class NameTargetTest {

    @Test
    public void getPersons_personFound_returnsList() throws CommandException {
        ModelStub model = new ModelStub();
        model.addPerson(AMY);

        List<Person> result = NAME_TARGET_AMY.getPersons(model);

        assertEquals(1, result.size());
        assertTrue(result.contains(AMY));
    }

    @Test
    public void getPersons_personNotFound_throwsCommandException() {
        ModelStub model = new ModelStub();

        CommandException e = assertThrows(CommandException.class, () -> NAME_TARGET_NONEXISTENT.getPersons(model));
        assertTrue(e.getMessage().contains("Student \"Nonexistent\" not found"));
    }

    @Test
    public void getDisplayName_returnsPersonName() {
        assertEquals("Amy Bee", NAME_TARGET_AMY.getDisplayName());
    }

    @Test
    public void isSinglePersonTarget_returnsTrue() {
        assertTrue(NAME_TARGET_AMY.isSinglePersonTarget());
    }

    @Test
    public void getAssignSuccessMessage_formatsCorrectly() {
        String result = NAME_TARGET_AMY.getAssignSuccessMessage("Tutorial 1", 1, 0);

        assertEquals("New assignment \"Tutorial 1\" assigned to student: \"Amy Bee\"", result);
    }

    @Test
    public void getUnassignSuccessMessage_formatsCorrectly() {
        String result = NAME_TARGET_AMY.getUnassignSuccessMessage("Tutorial 1", 1, 0);

        assertEquals("Assignment \"Tutorial 1\" unassigned from student: \"Amy Bee\"", result);
    }

    @Test
    public void equalsAndHashCode() {
        Target nameTargetAmy1 = NAME_TARGET_AMY;
        Target nameTargetAmy2 = new NameTarget(new PersonName("Amy Bee"));

        // same object
        assertEquals(nameTargetAmy1, nameTargetAmy1);

        // same values
        assertEquals(nameTargetAmy1, nameTargetAmy2);

        // different values
        assertNotEquals(NAME_TARGET_BENSON, nameTargetAmy1);

        // null
        assertNotEquals(null, nameTargetAmy1);

        // different type
        assertNotEquals("Hi I'm a String", nameTargetAmy1);
    }

    @Test
    public void hashCodeTest() {
        Target nameTargetAmy1 = NAME_TARGET_AMY;
        Target nameTargetAmy2 = new NameTarget(new PersonName("Amy Bee"));

        // same object -> same hash code
        assertEquals(nameTargetAmy1.hashCode(), nameTargetAmy1.hashCode());

        // equal objects -> same hash code
        assertEquals(nameTargetAmy1.hashCode(), nameTargetAmy2.hashCode());

        // different objects -> likely different hash code
        assertNotEquals(nameTargetAmy1.hashCode(), NAME_TARGET_BENSON.hashCode());
    }

    @Test
    public void toStringMethod() {
        String result = NAME_TARGET_AMY.toString();

        assertTrue(result.contains("NameTarget"));
        assertTrue(result.contains("Amy Bee"));
    }

    private static class ModelStub extends ModelManager {
        private final List<Person> persons = new ArrayList<>();

        public void addPerson(Person person) {
            persons.add(person);
        }

        @Override
        public Person findPersonByName(PersonName name) throws PersonNotFoundException {
            return persons.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .orElseThrow(PersonNotFoundException::new);
        }
    }
}

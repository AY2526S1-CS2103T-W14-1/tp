package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;

public class UnlabelCommandTest {

    @Test
    public void constructor_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnlabelCommand(null));
    }


    @Test
    public void execute_nameTarget_failure() throws CommandException {
        ModelStub model = new ModelStub();

        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_BENSON);

        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_BENSON.getUnlabelFailureMessage();

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        String expectedMessage = CLASS_TARGET_AMY.getUnlabelFailureMessage();

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void execute_emptyClass_throwsCommandException() {
        ModelStubEmptyClass model = new ModelStubEmptyClass();
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(ClassTarget.MESSAGE_NO_STUDENTS_FOUND,
                        CLASS_TARGET_NONEXISTENT.getDisplayName()),
                e.getMessage());
    }

    @Test
    public void equals_nameTarget() {
        UnlabelCommand unlabelAmy = new UnlabelCommand(NAME_TARGET_AMY);
        UnlabelCommand unlabelBenson = new UnlabelCommand(NAME_TARGET_BENSON);

        // same object -> true
        assertEquals(unlabelAmy, unlabelAmy);

        // same values -> true
        UnlabelCommand copy = new UnlabelCommand(NAME_TARGET_AMY);
        assertEquals(unlabelAmy, copy);

        // different target -> false
        assertNotEquals(unlabelAmy, unlabelBenson);

        // null -> false
        assertNotEquals(null, unlabelAmy);

        // different type -> false
        assertNotEquals(1, unlabelAmy);

        // string type -> false
        Object other = "I am a string";
        assertNotEquals(unlabelAmy, other);

    }

    @Test
    public void equals_classTarget() {
        UnlabelCommand unlabelClassA = new UnlabelCommand(CLASS_TARGET_AMY);
        UnlabelCommand unlabelClassACopy = new UnlabelCommand(CLASS_TARGET_AMY);
        UnlabelCommand unlabelClassB = new UnlabelCommand(CLASS_TARGET_BENSON);

        // same object -> true
        assertEquals(unlabelClassA, unlabelClassA);

        // same values -> true
        assertEquals(unlabelClassA, unlabelClassACopy);

        // different class -> false
        assertNotEquals(unlabelClassA, unlabelClassB);

        // different type -> false
        assertNotEquals(1, unlabelClassA);

        // null -> false
        assertNotEquals(null, unlabelClassA);
    }

    @Test
    public void toString_nameTarget() {
        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_AMY);
        String str = command.toString();

        assertTrue(str.contains("target"));
        assertTrue(str.contains("Amy"));
    }

    @Test
    public void toString_classTarget() {
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_AMY);
        String str = command.toString();

        assertTrue(str.contains("target"));
        assertTrue(str.contains("Class A"));
    }

    @Test
    public void execute_nameTarget_success() throws CommandException {
        ModelStub model = new ModelStub() {
            @Override
            public PersonStub findPersonByName(PersonName name) {
                Phone dummyPhone = new Phone("98765432");
                Email dummyEmail = new Email("test@gmail.com");
                TuitionClass dummyClass = new TuitionClass(VALID_CLASS_AMY);

                PersonStub person = new PersonStub(name, dummyPhone, dummyEmail, dummyClass, new HashSet<>()) {
                    @Override
                    public Label getLabel() {
                        return new Label("Project A");
                    }

                    @Override
                    public Person withRemovedLabel() {
                        return this;
                    }
                };
                return person;
            }
        };

        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_AMY);
        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_AMY.getUnlabelSuccessMessage();
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    static class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name) throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }
            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("test@gmail.com");
            TuitionClass dummyClass = new TuitionClass(VALID_CLASS_AMY);
            return new PersonStub(name, dummyPhone, dummyEmail, dummyClass, new HashSet<>());
        }

        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("test@gmail.com");
            PersonStub student = new PersonStub(ALICE.getName(), dummyPhone, dummyEmail, tuitionClass, new HashSet<>());
            return List.of(student);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {}
    }

    static class ModelStubEmptyClass extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            return List.of();
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;
        private boolean missingAssignment = false;

        PersonStub(PersonName name, Phone phone, Email email, TuitionClass tuitionClass, Set<Tag> tags) {
            super(name, phone, email, tuitionClass, tags);
            this.name = name;
        }

        @Override
        public PersonName getName() {
            return name;
        }

        @Override
        public PersonStub withRemovedAssignment(Assignment assignment) throws AssignmentNotFoundException {
            if (missingAssignment) {
                throw AssignmentNotFoundException.forStudent();
            }
            return this;
        }


    }
}

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

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.logic.commands.exceptions.LabelNotFoundException;
import seedu.edubook.model.ModelManager;
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
    public void execute_classTargetSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        String expectedMessage = CLASS_TARGET_AMY.getUnlabelSuccessMessage(1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetMultipleStudents_successAndSkip() throws CommandException {
        ModelStubMultipleStudents model = new ModelStubMultipleStudents();
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        // 3 students: 1 skipped (no assignment), 2 successful removals
        String expectedMessage = CLASS_TARGET_AMY.getUnlabelSuccessMessage(2, 1);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nameTargetNoLabel_throwsLabelNotFoundException() {
        ModelStubOneStudentSkipped model = new ModelStubOneStudentSkipped();

        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_AMY);

        CommandException e = assertThrows(LabelNotFoundException.class, () -> command.execute(model));
        assertEquals(LabelNotFoundException.forStudent().getMessage(),
                e.getMessage());
    }

    @Test
    public void execute_classTargetNoLabel_throwsLabelNotFoundException() {
        ModelStubAllStudentsSkipped model = new ModelStubAllStudentsSkipped();
        UnlabelCommand command = new UnlabelCommand(CLASS_TARGET_AMY);

        CommandException e = assertThrows(LabelNotFoundException.class, () -> command.execute(model));
        assertEquals(LabelNotFoundException.forClass(CLASS_TARGET_AMY.getDisplayName()).getMessage(),
                e.getMessage());
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
                    public PersonStub withRemovedLabel() {
                        return this;
                    }
                };
                return person;
            }
        };

        UnlabelCommand command = new UnlabelCommand(NAME_TARGET_AMY);
        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_AMY.getUnlabelSuccessMessage(1, 0);
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

    static class ModelStubMultipleStudents extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("99999999");
            Email email = new Email("dummy@edu.com");

            PersonStub alice = new PersonStub(ALICE.getName(), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());
            PersonStub tom = new PersonStub(new PersonName("Tom"), phone, email, tuitionClass, new HashSet<>());

            // Bob has no label (skip), Alice & Tom can be unlabeled successfully
            bob.missingLabel = true;
            return List.of(alice, bob, tom);
        }
    }

    static class ModelStubAllStudentsSkipped extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("11111111");
            Email email = new Email("dummy@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());

            // both don't have a label
            alice.missingLabel = true;
            bob.missingLabel = true;

            return List.of(alice, bob);
        }
    }

    static class ModelStubOneStudentSkipped extends ModelStub {
        @Override
        public PersonStub findPersonByName(PersonName name) throws PersonNotFoundException {
            Phone phone = new Phone("11111111");
            Email email = new Email("dummy@edu.com");
            TuitionClass tuitionClass = new TuitionClass(VALID_CLASS_AMY);

            PersonStub alice = new PersonStub(name, phone, email, tuitionClass, new HashSet<>());

            alice.missingLabel = true;

            return alice;
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;
        private boolean missingLabel = false;

        PersonStub(PersonName name, Phone phone, Email email, TuitionClass tuitionClass, Set<Tag> tags) {
            super(name, phone, email, tuitionClass, tags);
            this.name = name;
        }

        @Override
        public PersonName getName() {
            return name;
        }

        @Override
        public PersonStub withRemovedLabel() throws LabelNotFoundException {
            if (missingLabel) {
                throw LabelNotFoundException.forStudent();
            }
            return this;
        }
    }
}

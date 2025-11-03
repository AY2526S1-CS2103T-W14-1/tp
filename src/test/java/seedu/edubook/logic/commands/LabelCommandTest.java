package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_AMY;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalClassTargets.CLASS_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalLabel.LABEL_BAD;
import static seedu.edubook.testutil.TypicalLabel.LABEL_GOOD;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.logic.commands.exceptions.LabelAlreadyExistsException;
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

public class LabelCommandTest {

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LabelCommand(null, NAME_TARGET_AMY));
    }

    @Test
    public void constructor_nullLabelTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LabelCommand(LABEL_GOOD, null));
    }

    @Test
    public void execute_nameTargetForLabel_success() throws CommandException {
        ModelStub model = new ModelStub();
        LabelCommand command = new LabelCommand(LABEL_GOOD, NAME_TARGET_BENSON);

        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_BENSON.getLabelSuccessMessage(
                LABEL_GOOD.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nameTargetWithLabel_throwsLabelAlreadyExistsException() {
        ModelStub model = new ModelStub();
        Label duplicateLabel = new Label("Duplicate");
        LabelCommand command = new LabelCommand(duplicateLabel, NAME_TARGET_AMY);

        LabelAlreadyExistsException e = assertThrows(
                LabelAlreadyExistsException.class, () -> command.execute(model));

        assertEquals(LabelAlreadyExistsException.forStudent().getMessage(), e.getMessage());
    }

    @Test
    public void execute_classTargetAllStudentsSkipped_throwsLabelAlreadyExistsException() {
        ModelStubAllStudentsSkipped model = new ModelStubAllStudentsSkipped();
        LabelCommand command = new LabelCommand(LABEL_GOOD, CLASS_TARGET_AMY);

        LabelAlreadyExistsException e = assertThrows(
                LabelAlreadyExistsException.class, () -> command.execute(model));

        assertEquals(
                LabelAlreadyExistsException.forClass(CLASS_TARGET_AMY.getDisplayName()).getMessage(),
                e.getMessage());
    }

    @Test
    public void execute_classTargetForLabelSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        LabelCommand command = new LabelCommand(LABEL_BAD, CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        String expectedMessage = CLASS_TARGET_AMY.getLabelSuccessMessage(
                LABEL_BAD.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nonExistentPersonForLabel_throwsCommandException() {
        ModelStub model = new ModelStub();
        LabelCommand command = new LabelCommand(LABEL_BAD, NAME_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void execute_emptyClass_throwsCommandException() {
        ModelStub model = new ModelStubEmptyClass();
        LabelCommand command = new LabelCommand(LABEL_GOOD, CLASS_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(ClassTarget.MESSAGE_NO_STUDENTS_FOUND,
                        CLASS_TARGET_NONEXISTENT.getDisplayName()),
                e.getMessage());
    }

    @Test
    public void equals_nameAssignTarget() {
        LabelCommand labelBadToAmyCommand = new LabelCommand(LABEL_BAD, NAME_TARGET_AMY);
        LabelCommand labelBadToBensonCommand = new LabelCommand(LABEL_BAD, NAME_TARGET_BENSON);
        LabelCommand labelGoodToAliceCommand = new LabelCommand(LABEL_GOOD, NAME_TARGET_AMY);

        // same object -> true
        assertEquals(labelBadToAmyCommand, labelBadToAmyCommand);

        // same assignment and same person name -> true
        LabelCommand labelBadToAmyCommandCopy = new LabelCommand(LABEL_BAD, NAME_TARGET_AMY);
        assertEquals(labelBadToAmyCommand, labelBadToAmyCommandCopy);

        // different types -> false
        assertNotEquals(1, labelBadToAmyCommand);

        // null -> false
        assertNotEquals(null, labelBadToAmyCommand);

        // same assignment but different person name -> false
        assertNotEquals(labelBadToAmyCommand, labelBadToBensonCommand);

        // different assignment but same person name -> false
        assertNotEquals(labelBadToAmyCommand, labelGoodToAliceCommand);
    }

    @Test
    public void equals_classAssignTarget() {
        LabelCommand labelGoodToClassA = new LabelCommand(LABEL_GOOD, CLASS_TARGET_AMY);
        LabelCommand labelGoodToClassACopy = new LabelCommand(LABEL_GOOD, CLASS_TARGET_AMY);
        LabelCommand labelGoodToClassB = new LabelCommand(LABEL_GOOD, CLASS_TARGET_BENSON);
        LabelCommand labelBadToClassA = new LabelCommand(LABEL_BAD, CLASS_TARGET_AMY);

        // same object -> true
        assertEquals(labelGoodToClassA, labelGoodToClassA);

        // same values -> true
        assertEquals(labelGoodToClassA, labelGoodToClassACopy);

        // different class target -> false
        assertNotEquals(labelGoodToClassA, labelGoodToClassB);

        // different assignment but same class -> false
        assertNotEquals(labelBadToClassA, labelGoodToClassA);

        // different type -> false
        assertNotEquals(1, labelGoodToClassA);

        // null -> false
        assertNotEquals(null, labelGoodToClassA);
    }

    @Test
    public void toString_nameLabelTarget() {
        LabelCommand labelCommand = new LabelCommand(LABEL_GOOD, NAME_TARGET_AMY);

        String str = labelCommand.toString();

        assertTrue(str.contains("label"));
        assertTrue(str.contains("Top student"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Amy"));
    }

    @Test
    public void toString_classLabelTarget() {
        LabelCommand labelCommand = new LabelCommand(LABEL_GOOD, CLASS_TARGET_AMY);

        String str = labelCommand.toString();

        assertTrue(str.contains("label"));
        assertTrue(str.contains("Top student"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Class A"));
    }

    static class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name) throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }

            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dumb@gmail.com");
            TuitionClass dummyTuitionClass = new TuitionClass(VALID_CLASS_AMY);
            return new PersonStub(name, dummyPhone, dummyEmail, dummyTuitionClass, new HashSet<>());
        }

        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dumb@gmail.com");
            PersonStub student = new PersonStub(ALICE.getName(), dummyPhone, dummyEmail, tuitionClass, new HashSet<>());
            return List.of(student);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }
    }

    static class ModelStubEmptyClass extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            return List.of(); // empty class
        }
    }

    static class ModelStubAllStudentsSkipped extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("99999999");
            Email email = new Email("dummy@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());

            alice.hasLabel = true;
            bob.hasLabel = true;

            return List.of(alice, bob);
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;
        private boolean hasLabel = false;

        PersonStub(PersonName name, Phone phone, Email email, TuitionClass tuitionClass, Set<Tag> tags) {
            super(name, phone, email, tuitionClass, tags);
            this.name = name;
        }

        @Override
        public PersonName getName() {
            return name;
        }

        @Override
        public PersonStub withAddedLabel(Label label) throws LabelAlreadyExistsException {
            if (hasLabel || label.labelContent.equals("Duplicate")) {
                throw LabelAlreadyExistsException.forStudent();
            }
            return this;
        }
    }

}

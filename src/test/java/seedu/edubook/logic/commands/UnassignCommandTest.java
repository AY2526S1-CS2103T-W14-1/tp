package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB;
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
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;

public class UnassignCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(null, NAME_TARGET_AMY));
    }

    @Test
    public void constructor_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, null));
    }

    @Test
    public void execute_nameTarget_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_BENSON);

        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_BENSON.getUnassignSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        String expectedMessage = CLASS_TARGET_AMY.getUnassignSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetMultipleStudents_successAndSkip() throws CommandException {
        ModelStubMultipleStudents model = new ModelStubMultipleStudents();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        // 3 students: 1 skipped (no assignment), 2 successful removals
        String expectedMessage = CLASS_TARGET_AMY.getUnassignSuccessMessage(VALID_ASSIGNMENT_HOMEWORK, 2, 1);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetAllStudentsSkipped_throwsAssignmentNotFoundException() {
        ModelStubAllStudentsSkipped model = new ModelStubAllStudentsSkipped();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        AssignmentNotFoundException e = assertThrows(
                AssignmentNotFoundException.class, () -> command.execute(model));

        assertEquals(
                AssignmentNotFoundException
                        .forClass(CLASS_TARGET_AMY.getDisplayName(), ASSIGNMENT_HOMEWORK.assignmentName.toString())
                        .getMessage(),
                e.getMessage());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void execute_emptyClass_throwsCommandException() {
        ModelStubEmptyClass model = new ModelStubEmptyClass();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(ClassTarget.MESSAGE_NO_STUDENTS_FOUND,
                        CLASS_TARGET_NONEXISTENT.getDisplayName()),
                e.getMessage());
    }

    @Test
    public void equals_nameTarget() {
        UnassignCommand unassignHomeworkAmy = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
        UnassignCommand unassignHomeworkBenson = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                NAME_TARGET_BENSON);
        UnassignCommand unassignLabAmy = new UnassignCommand(ASSIGNMENT_LAB.assignmentName, NAME_TARGET_AMY);

        // same object -> true
        assertEquals(unassignHomeworkAmy, unassignHomeworkAmy);

        // same values -> true
        UnassignCommand copy = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
        assertEquals(unassignHomeworkAmy, copy);

        // different assignment -> false
        assertNotEquals(unassignHomeworkAmy, unassignLabAmy);

        // different target -> false
        assertNotEquals(unassignHomeworkAmy, unassignHomeworkBenson);

        // null -> false
        assertNotEquals(null, unassignHomeworkAmy);

        // different type -> false
        assertNotEquals(1, unassignHomeworkAmy);
    }

    @Test
    public void equals_classTarget() {
        UnassignCommand unassignHomeworkClassA = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_AMY);
        UnassignCommand unassignHomeworkClassACopy = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_AMY);
        UnassignCommand unassignHomeworkClassB = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_BENSON);
        UnassignCommand unassignLabClassA = new UnassignCommand(ASSIGNMENT_LAB.assignmentName, CLASS_TARGET_AMY);

        // same object -> true
        assertEquals(unassignHomeworkClassA, unassignHomeworkClassA);

        // same values -> true
        assertEquals(unassignHomeworkClassA, unassignHomeworkClassACopy);

        // different class -> false
        assertNotEquals(unassignHomeworkClassA, unassignHomeworkClassB);

        // different assignment -> false
        assertNotEquals(unassignHomeworkClassA, unassignLabClassA);

        // different type -> false
        assertNotEquals(1, unassignHomeworkClassA);

        // null -> false
        assertNotEquals(null, unassignHomeworkClassA);
    }

    @Test
    public void toString_nameTarget() {
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
        String str = command.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Amy"));
    }

    @Test
    public void toString_classTarget() {
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);
        String str = command.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
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

            // Bob has no assignment (skip), Alice & Tom can be unassigned successfully
            bob.missingAssignment = true;
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

            // both don't have the assignment
            alice.missingAssignment = true;
            bob.missingAssignment = true;

            return List.of(alice, bob);
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

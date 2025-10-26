package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edubook.logic.commands.UnmarkCommand.MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_CLASS;
import static seedu.edubook.logic.commands.UnmarkCommand.MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_SINGLE;
import static seedu.edubook.logic.commands.UnmarkCommand.MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS;
import static seedu.edubook.logic.commands.UnmarkCommand.MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE;
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
import seedu.edubook.logic.commands.exceptions.AssignmentUnmarkedException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;

public class UnmarkCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnmarkCommand(null, NAME_TARGET_AMY));
    }

    @Test
    public void constructor_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, null));
    }

    @Test
    public void execute_nameTarget_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_BENSON);

        CommandResult result = command.execute(model);

        String expectedMessage = NAME_TARGET_BENSON.getUnmarkSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        String expectedMessage = CLASS_TARGET_AMY.getUnmarkSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetMultipleStudents_successAndSkip() throws CommandException {
        ModelStubMultipleStudents model = new ModelStubMultipleStudents();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandResult result = command.execute(model);

        // 3 students: 1 skipped (already unmarked), 2 successful unmarks
        String expectedMessage = CLASS_TARGET_AMY.getUnmarkSuccessMessage(VALID_ASSIGNMENT_HOMEWORK, 2, 1, 0);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nameAlreadyUnmarked_throwsCommandException() {
        ModelStubSingleStudentAlreadyUnmarked model = new ModelStubSingleStudentAlreadyUnmarked();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(e.getMessage(),
                String.format(MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_SINGLE,
                        VALID_NAME_AMY,
                        ASSIGNMENT_HOMEWORK.assignmentName)
        );
    }

    @Test
    public void execute_classTargetAllStudentsSkipped_throwsCommandException() {
        ModelStubAllStudentsSkipped model = new ModelStubAllStudentsSkipped();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));

        assertEquals(e.getMessage(),
                String.format(MESSAGE_ASSIGNMENT_ALREADY_UNMARKED_CLASS, 2,
                        VALID_CLASS_AMY,
                        ASSIGNMENT_HOMEWORK.assignmentName)
        );
    }

    @Test
    public void execute_singleAssignmentNotFound_throwsCommandException() {
        ModelStubAssignmentMissing model = new ModelStubAssignmentMissing();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(
                String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_SINGLE, NAME_TARGET_AMY.getDisplayName(),
                        ASSIGNMENT_HOMEWORK.assignmentName),
                e.getMessage()
        );
    }

    @Test
    public void execute_classAllMissingAssignment_throwsCommandException() {
        ModelStubAllMissingAssignments model = new ModelStubAllMissingAssignments();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(
                String.format(MESSAGE_ASSIGNMENT_NOT_FOUND_CLASS,
                        CLASS_TARGET_AMY.getDisplayName(),
                        ASSIGNMENT_HOMEWORK.assignmentName),
                e.getMessage()
        );
    }

    @Test
    public void execute_classMixedErrorOneUnmarked_throwsCommandException() {
        ModelStubMixedClassOneUnmarked model = new ModelStubMixedClassOneUnmarked();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("No assignments were unmarked for class \"Class A\". 1 was already unmarked and 1 did not exist.",
                e.getMessage());
    }

    @Test
    public void execute_classMixedErrorMoreThanOneUnmarked_throwsCommandException() {
        ModelStubMixedClassMoreThanOneUnmarked model = new ModelStubMixedClassMoreThanOneUnmarked();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("No assignments were unmarked for class \"Class A\". 2 were already unmarked and 1 did not exist.",
                e.getMessage());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void execute_emptyClass_throwsCommandException() {
        ModelStubEmptyClass model = new ModelStubEmptyClass();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_NONEXISTENT);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(ClassTarget.MESSAGE_NO_STUDENTS_FOUND,
                        CLASS_TARGET_NONEXISTENT.getDisplayName()),
                e.getMessage());
    }

    @Test
    public void equals_nameTarget() {
        UnmarkCommand unassignHomeworkAmy = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
        UnmarkCommand unassignHomeworkBenson = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                NAME_TARGET_BENSON);
        UnmarkCommand unassignLabAmy = new UnmarkCommand(ASSIGNMENT_LAB.assignmentName, NAME_TARGET_AMY);

        // same object -> true
        assertEquals(unassignHomeworkAmy, unassignHomeworkAmy);

        // same values -> true
        UnmarkCommand copy = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
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
        UnmarkCommand unassignHomeworkClassA = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_AMY);
        UnmarkCommand unassignHomeworkClassACopy = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_AMY);
        UnmarkCommand unassignHomeworkClassB = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName,
                CLASS_TARGET_BENSON);
        UnmarkCommand unassignLabClassA = new UnmarkCommand(ASSIGNMENT_LAB.assignmentName, CLASS_TARGET_AMY);

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
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, NAME_TARGET_AMY);
        String str = command.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Amy"));
    }

    @Test
    public void toString_classTarget() {
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK.assignmentName, CLASS_TARGET_AMY);
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

            bob.alreadyUnmarked = true;
            return List.of(alice, bob, tom);
        }
    }

    static class ModelStubSingleStudentAlreadyUnmarked extends ModelStub {
        @Override
        public PersonStub findPersonByName(PersonName name) {
            Phone phone = new Phone("99999999");
            Email email = new Email("dummy@edu.com");
            TuitionClass tuitionClass = new TuitionClass(VALID_CLASS_AMY);
            PersonStub alice = new PersonStub(name, phone, email, tuitionClass, new HashSet<>());
            alice.alreadyUnmarked = true;
            return alice;
        }
    }

    static class ModelStubAllStudentsSkipped extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("11111111");
            Email email = new Email("dummy@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());

            alice.alreadyUnmarked = true;
            bob.alreadyUnmarked = true;

            return List.of(alice, bob);
        }
    }

    static class ModelStubAllMissingAssignments extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("11112222");
            Email email = new Email("missing@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());
            alice.missingAssignment = true;
            bob.missingAssignment = true;
            return List.of(alice, bob);
        }
    }

    static class ModelStubMixedClassOneUnmarked extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("33333333");
            Email email = new Email("mix@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());
            alice.alreadyUnmarked = true;
            bob.missingAssignment = true;
            return List.of(alice, bob);
        }
    }

    static class ModelStubMixedClassMoreThanOneUnmarked extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("33333333");
            Email email = new Email("mix@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());
            PersonStub claire = new PersonStub(new PersonName("Claire"), phone, email, tuitionClass, new HashSet<>());
            alice.alreadyUnmarked = true;
            bob.alreadyUnmarked = true;
            claire.missingAssignment = true;
            return List.of(alice, bob, claire);
        }
    }

    static class ModelStubAssignmentMissing extends ModelStub {
        @Override
        public PersonStub findPersonByName(PersonName name) {
            Phone phone = new Phone("44444444");
            Email email = new Email("missing@edu.com");
            TuitionClass tuitionClass = new TuitionClass(VALID_CLASS_AMY);
            PersonStub person = new PersonStub(name, phone, email, tuitionClass, new HashSet<>());
            person.missingAssignment = true;
            return person;
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;
        private boolean alreadyUnmarked = false;
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
        public void unmarkAssignment(AssignmentName assignmentName)
                throws AssignmentUnmarkedException, AssignmentNotFoundException {
            if (alreadyUnmarked) {
                throw new AssignmentUnmarkedException();
            }
            if (missingAssignment) {
                throw new AssignmentNotFoundException("dummy");
            }
        }
    }
}

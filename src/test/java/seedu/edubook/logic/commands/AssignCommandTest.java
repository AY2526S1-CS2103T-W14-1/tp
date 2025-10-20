package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.HOON;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentAlreadyExistsException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assign.AssignTarget;
import seedu.edubook.model.assign.ClassAssignTarget;
import seedu.edubook.model.assign.NameAssignTarget;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.person.exceptions.PersonNotFoundException;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.testutil.PersonBuilder;

public class AssignCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(null,
                new NameAssignTarget(HOON.getName())));
    }

    @Test
    public void constructor_nullAssignTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(ASSIGNMENT_HOMEWORK, null));
    }

    @Test
    public void execute_nameTarget_success() throws CommandException {
        ModelStub model = new ModelStub();
        AssignTarget target = new NameAssignTarget(ALICE.getName());
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandResult result = command.execute(model);

        String expectedMessage = target.getAssignSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetSingleStudent_success() throws CommandException {
        ModelStub model = new ModelStub();
        TuitionClass tuitionClass = new TuitionClass(VALID_CLASS_AMY);
        AssignTarget target = new ClassAssignTarget(tuitionClass);
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandResult result = command.execute(model);

        String expectedMessage = target.getAssignSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetMultipleStudents_successAndSkip() throws CommandException {
        ModelStubMultipleStudents model = new ModelStubMultipleStudents();
        TuitionClass tuitionClass = new TuitionClass("W14");
        AssignTarget target = new ClassAssignTarget(tuitionClass);
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandResult result = command.execute(model);

        // 3 students: 2 skipped (already have assignment), 1 success
        String expectedMessage = target.getAssignSuccessMessage(VALID_ASSIGNMENT_HOMEWORK, 1, 2);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_classTargetAllStudentsSkipped_throwsAssignmentAlreadyExistsException() {
        ModelStubAllStudentsSkipped model = new ModelStubAllStudentsSkipped();
        TuitionClass tuitionClass = new TuitionClass("FullClass");
        AssignTarget target = new ClassAssignTarget(tuitionClass);
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        AssignmentAlreadyExistsException e = assertThrows(
                AssignmentAlreadyExistsException.class, () -> command.execute(model));

        assertEquals(
                AssignmentAlreadyExistsException
                        .forClass(tuitionClass.toString(), ASSIGNMENT_HOMEWORK.assignmentName.toString())
                        .getMessage(),
                e.getMessage());
    }

    @Test
    public void execute_duplicateAssignment_throwsAssignmentAlreadyExistsException() {
        ModelStub model = new ModelStub();
        Assignment duplicateAssignment = new Assignment(new AssignmentName("Duplicate"));
        AssignTarget target = new NameAssignTarget(ALICE.getName());
        AssignCommand command = new AssignCommand(duplicateAssignment, target);

        AssignmentAlreadyExistsException e = assertThrows(
                AssignmentAlreadyExistsException.class, () -> command.execute(model));
        assertEquals(AssignmentAlreadyExistsException.forStudent().getMessage(), e.getMessage());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        PersonName assignee = new PersonName("Nonexistent");
        AssignTarget target = new NameAssignTarget(assignee);
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameAssignTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void execute_emptyClass_throwsCommandException() {
        ModelStub model = new ModelStubEmptyClass();
        TuitionClass emptyClass = new TuitionClass("EmptyClass");
        AssignTarget target = new ClassAssignTarget(emptyClass);
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(ClassAssignTarget.MESSAGE_NO_STUDENTS_FOUND, emptyClass), e.getMessage());
    }

    @Test
    public void equals_nameAssignTarget() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AssignCommand assignHomeworkToAliceCommand = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new NameAssignTarget(alice.getName()));
        AssignCommand assignHomeworkToBobCommand = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new NameAssignTarget(bob.getName()));
        AssignCommand assignLabToAliceCommand = new AssignCommand(ASSIGNMENT_LAB,
                new NameAssignTarget(alice.getName()));

        // same object -> true
        assertEquals(assignHomeworkToAliceCommand, assignHomeworkToAliceCommand);

        // same assignment and same person name -> true
        AssignCommand assignHomeworkToAliceCommandCopy = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new NameAssignTarget(alice.getName()));
        assertEquals(assignHomeworkToAliceCommand, assignHomeworkToAliceCommandCopy);

        // different types -> false
        assertNotEquals(1, assignHomeworkToAliceCommand);

        // null -> false
        assertNotEquals(null, assignHomeworkToAliceCommand);

        // same assignment but different person name -> false
        assertNotEquals(assignHomeworkToAliceCommand, assignHomeworkToBobCommand);

        // different assignment but same person name -> false
        assertNotEquals(assignHomeworkToAliceCommand, assignLabToAliceCommand);
    }

    @Test
    public void equals_classAssignTarget() {
        TuitionClass classA = new TuitionClass("A");
        TuitionClass classB = new TuitionClass("B");

        AssignCommand assignHomeworkToClassA = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new ClassAssignTarget(classA));
        AssignCommand assignHomeworkToClassACopy = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new ClassAssignTarget(classA));
        AssignCommand assignHomeworkToClassB = new AssignCommand(ASSIGNMENT_HOMEWORK,
                new ClassAssignTarget(classB));
        AssignCommand assignLabToClassA = new AssignCommand(ASSIGNMENT_LAB,
                new ClassAssignTarget(classA));

        // same object -> true
        assertEquals(assignHomeworkToClassA, assignHomeworkToClassA);

        // same values -> true
        assertEquals(assignHomeworkToClassA, assignHomeworkToClassACopy);

        // different class target -> false
        assertNotEquals(assignHomeworkToClassA, assignHomeworkToClassB);

        // different assignment but same class -> false
        assertNotEquals(assignHomeworkToClassA, assignLabToClassA);

        // different type -> false
        assertNotEquals(1, assignHomeworkToClassA);

        // null -> false
        assertNotEquals(null, assignHomeworkToClassA);
    }

    @Test
    public void toString_nameAssignTarget() {
        AssignCommand assignCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, new NameAssignTarget(HOON.getName()));

        String str = assignCommand.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Hoon"));
    }

    @Test
    public void toString_classAssignTarget() {
        TuitionClass tuitionClass = new TuitionClass("Class A");
        AssignCommand assignCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, new ClassAssignTarget(tuitionClass));

        String str = assignCommand.toString();

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

    static class ModelStubMultipleStudents extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("98765432");
            Email email = new Email("dumb@gmail.com");

            PersonStub alice = new PersonStub(ALICE.getName(), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());
            PersonStub tom = new PersonStub(new PersonName("Tom"), phone, email, tuitionClass, new HashSet<>());

            // Mark bob and tom as already having the assignment
            bob.hasDuplicateAssignment = true;
            tom.hasDuplicateAssignment = true;

            return List.of(alice, bob, tom);
        }
    }

    static class ModelStubAllStudentsSkipped extends ModelStub {
        @Override
        public List<Person> findPersonsByClass(TuitionClass tuitionClass) {
            Phone phone = new Phone("99999999");
            Email email = new Email("dummy@edu.com");

            PersonStub alice = new PersonStub(new PersonName("Alice"), phone, email, tuitionClass, new HashSet<>());
            PersonStub bob = new PersonStub(new PersonName("Bob"), phone, email, tuitionClass, new HashSet<>());

            // Mark both as already having the assignment
            alice.hasDuplicateAssignment = true;
            bob.hasDuplicateAssignment = true;

            return List.of(alice, bob);
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;
        private boolean hasDuplicateAssignment = false;

        PersonStub(PersonName name, Phone phone, Email email, TuitionClass tuitionClass, Set<Tag> tags) {
            super(name, phone, email, tuitionClass, tags);
            this.name = name;
        }

        @Override
        public PersonName getName() {
            return name;
        }

        @Override
        public PersonStub withAddedAssignment(Assignment assignment) throws AssignmentAlreadyExistsException {
            if (hasDuplicateAssignment || assignment.assignmentName.fullName.equals("Duplicate")) {
                throw AssignmentAlreadyExistsException.forStudent();
            }
            return this;
        }
    }
}

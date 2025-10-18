package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.HOON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentAlreadyExistsException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assign.AssignTarget;
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
    public void execute_success() throws CommandException {
        ModelStub model = new ModelStub();
        AssignTarget target = new NameAssignTarget(ALICE.getName());
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandResult result = command.execute(model);

        String expectedMessage = target.getAssignmentSuccessMessage(
                ASSIGNMENT_HOMEWORK.assignmentName.toString(), 1, 0);

        assertEquals(expectedMessage, result.getFeedbackToUser());
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
        assertEquals(NameAssignTarget.MESSAGE_PERSON_NOT_FOUND, e.getMessage());
    }

    @Test
    public void equals() {
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
    public void toStringMethod() {
        AssignCommand assignCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, new NameAssignTarget(HOON.getName()));

        String str = assignCommand.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Hoon"));
    }

    class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name) throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }

            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dumb@gmail.com");
            TuitionClass dummyTuitionClass = new TuitionClass("W14");
            return new PersonStub(name, dummyPhone, dummyEmail, dummyTuitionClass, new HashSet<>());
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;

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
            if (assignment.assignmentName.fullName.equals("Duplicate")) {
                throw AssignmentAlreadyExistsException.forStudent();
            }
            return this;
        }
    }
}

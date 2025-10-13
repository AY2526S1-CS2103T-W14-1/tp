package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.HOON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
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

public class UnassignCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(null, HOON.getName()));
    }

    @Test
    public void constructor_nullPersonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(ASSIGNMENT_HOMEWORK, null));
    }

    @Test
    public void execute_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK, ALICE.getName());

        CommandResult result = command.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_SUCCESS,
                        ASSIGNMENT_HOMEWORK.assignmentName, ALICE.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_missingAssignment_throwsAssignmentNotFoundException() {
        ModelStub model = new ModelStub();
        Assignment missingAssignment = new Assignment(new AssignmentName("Missing"));
        UnassignCommand command = new UnassignCommand(missingAssignment, ALICE.getName());

        AssignmentNotFoundException e = assertThrows(AssignmentNotFoundException.class, () -> command.execute(model));
        assertEquals(AssignmentNotFoundException.MESSAGE_ASSIGNMENT_NOT_FOUND, e.getMessage());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        PersonName unassignee = new PersonName("Nonexistent");
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK, unassignee);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(UnassignCommand.MESSAGE_STUDENT_NOT_FOUND, e.getMessage());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        UnassignCommand unassignHomeworkFromAliceCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, alice.getName());
        UnassignCommand unassignHomeworkFromBobCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, bob.getName());
        UnassignCommand unassignTutorialFromAliceCommand = new UnassignCommand(ASSIGNMENT_TUTORIAL, alice.getName());

        // same object -> true
        assertEquals(unassignHomeworkFromAliceCommand, unassignHomeworkFromAliceCommand);

        // same assignment and same person name -> true
        UnassignCommand unassignHomeworkFromAliceCommandCopy = new UnassignCommand(ASSIGNMENT_HOMEWORK,
                alice.getName());
        assertEquals(unassignHomeworkFromAliceCommand, unassignHomeworkFromAliceCommandCopy);

        // different types -> false
        assertNotEquals(1, unassignHomeworkFromAliceCommand);

        // null -> false
        assertNotEquals(null, unassignHomeworkFromAliceCommand);

        // same assignment but different person name -> false
        assertNotEquals(unassignHomeworkFromAliceCommand, unassignHomeworkFromBobCommand);

        // different assignment but same person name -> false
        assertNotEquals(unassignHomeworkFromAliceCommand, unassignTutorialFromAliceCommand);
    }

    @Test
    public void toStringMethod() {
        UnassignCommand unassignCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, HOON.getName());

        String str = unassignCommand.toString();

        assertTrue(str.contains("toUnassign"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("unassignee"));
        assertTrue(str.contains("Hoon"));
    }

    class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name, String errorMessage) throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }

            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dummy@gmail.com");
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
        public PersonStub withRemovedAssignment(Assignment assignment) throws AssignmentNotFoundException {
            if (assignment.assignmentName.fullName.equals("Missing")) {
                throw new AssignmentNotFoundException();
            }
            return this;
        }
    }
}

package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.HOON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.ModelManager;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.assignment.exceptions.DuplicateAssignmentException;
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
        assertThrows(NullPointerException.class, () -> new AssignCommand(null, HOON.getName()));
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(ASSIGNMENT_HOMEWORK, null));
    }

    @Test
    public void execute_success() throws Exception {
        ModelStub model = new ModelStub();
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, ALICE.getName());

        CommandResult result = command.execute(model);

        assertEquals(
                String.format(AssignCommand.MESSAGE_SUCCESS, ASSIGNMENT_HOMEWORK.assignmentName, ALICE.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        ModelStub model = new ModelStub();
        Assignment duplicateAssignment = new Assignment(new AssignmentName("Duplicate"));
        AssignCommand command = new AssignCommand(duplicateAssignment, ALICE.getName());

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AssignCommand.MESSAGE_ASSIGNMENT_ALREADY_ASSIGNED, thrown.getMessage());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        ModelStub model = new ModelStub();
        PersonName assignee = new PersonName("Nonexistent");
        AssignCommand command = new AssignCommand(ASSIGNMENT_HOMEWORK, assignee);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AssignCommand.MESSAGE_STUDENT_NOT_FOUND, thrown.getMessage());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AssignCommand addHomeworkToAliceCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, alice.getName());
        AssignCommand addHomeworkToBobCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, bob.getName());

        // same object -> returns true
        assertEquals(addHomeworkToAliceCommand, addHomeworkToAliceCommand);

        // same values -> returns true
        AssignCommand addHomeworkToAliceCommandCopy = new AssignCommand(ASSIGNMENT_HOMEWORK, alice.getName());
        assertEquals(addHomeworkToAliceCommand, addHomeworkToAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addHomeworkToAliceCommand);

        // null -> returns false
        assertNotEquals(null, addHomeworkToAliceCommand);

        // different objects -> returns false
        assertNotEquals(addHomeworkToAliceCommand, addHomeworkToBobCommand);
    }

    public void toStringMethod() {
        AssignCommand assignCommand = new AssignCommand(ASSIGNMENT_HOMEWORK, HOON.getName());

        String str = assignCommand.toString();

        assertTrue(str.contains("toAssign"));
        assertTrue(str.contains("Homework 1"));
        assertTrue(str.contains("assigneeName"));
        assertTrue(str.contains("Hoon"));
    }

    class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name, String errorMessage) throws PersonNotFoundException {
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

        public PersonName getName() {
            return name;
        }

        public PersonStub withAddedAssignment(Assignment assignment) throws DuplicateAssignmentException {
            if (assignment.assignmentName.fullName.equals("Duplicate")) {
                throw new DuplicateAssignmentException();
            }
            return this;
        }
    }

}

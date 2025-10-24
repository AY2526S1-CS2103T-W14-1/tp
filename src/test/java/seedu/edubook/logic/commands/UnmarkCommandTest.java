package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK_TO_UNMARK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB_TO_UNMARK;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.HOON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
import seedu.edubook.testutil.PersonBuilder;

public class UnmarkCommandTest {

    @Test
    public void constructor_nullAssignmentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnmarkCommand(null, HOON.getName()));
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new UnmarkCommand(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, null));
    }

    @Test
    public void execute_success() throws CommandException {
        ModelStub modelStub = new ModelStub();
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, ALICE.getName());

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(UnmarkCommand.MESSAGE_SUCCESS,
                        ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, ALICE.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub modelStub = new ModelStub();
        PersonName student = new PersonName("Nonexistent");
        UnmarkCommand command = new UnmarkCommand(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, student);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(UnmarkCommand.MESSAGE_STUDENT_NOT_FOUND, e.getMessage());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice")
                .withAssignments(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName.fullName).build();
        Person bob = new PersonBuilder().withName("Bob")
                .withAssignments(ASSIGNMENT_LAB_TO_UNMARK.assignmentName.fullName).build();
        UnmarkCommand unmarkHomeworkToAliceCommand =
                new UnmarkCommand(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, alice.getName());
        UnmarkCommand unmarkLabToBobCommand =
                new UnmarkCommand(ASSIGNMENT_LAB_TO_UNMARK.assignmentName, bob.getName());
        UnmarkCommand unmarkLabToAliceCommand =
                new UnmarkCommand(ASSIGNMENT_LAB_TO_UNMARK.assignmentName, ALICE.getName());

        // same object -> true
        assertEquals(unmarkHomeworkToAliceCommand, unmarkHomeworkToAliceCommand);

        // same assignment and same person name -> true
        UnmarkCommand unmarkHomeworkToAliceCommandCopy =
                new UnmarkCommand(ASSIGNMENT_HOMEWORK_TO_UNMARK.assignmentName, alice.getName());
        assertEquals(unmarkHomeworkToAliceCommand, unmarkHomeworkToAliceCommandCopy);

        // different types -> false
        assertNotEquals(1, unmarkLabToBobCommand);

        // null -> false
        assertNotEquals(null, unmarkHomeworkToAliceCommand);

        // same assignment but different person name -> false
        assertNotEquals(unmarkLabToAliceCommand, unmarkLabToBobCommand);

        // different assignment but same person name -> false
        assertNotEquals(unmarkHomeworkToAliceCommand, unmarkLabToAliceCommand);
    }

    class ModelStub extends ModelManager {
        @Override
        public UnmarkCommandTest.PersonStub findPersonByName(PersonName name)
                throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }

            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dumb@gmail.com");
            TuitionClass dummyTuitionClass = new TuitionClass("W14");
            Set<Assignment> dummyAssignments = new HashSet<>();
            dummyAssignments.add(ASSIGNMENT_HOMEWORK_TO_UNMARK);
            return new PersonStub(name, dummyPhone, dummyEmail, dummyTuitionClass, new HashSet<>(), dummyAssignments);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }
    }

    static class PersonStub extends Person {
        private final PersonName name;

        PersonStub(PersonName name, Phone phone, Email email, TuitionClass tuitionClass,
                   Set<Tag> tags, Set<Assignment> assignments) {
            super(name, phone, email, tuitionClass, tags, assignments);
            this.name = name;
        }

        @Override
        public PersonName getName() {
            return name;
        }
    }
}
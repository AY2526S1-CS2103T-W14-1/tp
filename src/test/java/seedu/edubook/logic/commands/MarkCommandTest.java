package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK_TO_MARK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB_TO_MARK;
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

public class MarkCommandTest {
    private ModelStub modelStub = new ModelStub();

    @Test
    public void constructor_nullAssignmentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkCommand(null, HOON.getName()));
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, null));
    }

    @Test
    public void execute_success() throws CommandException {
        MarkCommand command = new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, ALICE.getName());

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MarkCommand.MESSAGE_SUCCESS,
                        ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, ALICE.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        PersonName student = new PersonName("Nonexistent");
        MarkCommand command = new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, student);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(MarkCommand.MESSAGE_STUDENT_NOT_FOUND, e.getMessage());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice")
                .withAssignments(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName.fullName).build();
        Person bob = new PersonBuilder().withName("Bob")
                .withAssignments(ASSIGNMENT_LAB_TO_MARK.assignmentName.fullName).build();
        MarkCommand markHomeworkToAliceCommand =
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, alice.getName());
        MarkCommand markLabToBobCommand =
                new MarkCommand(ASSIGNMENT_LAB_TO_MARK.assignmentName, bob.getName());
        MarkCommand markLabToAliceCommand =
                new MarkCommand(ASSIGNMENT_LAB_TO_MARK.assignmentName, ALICE.getName());

        // same object -> true
        assertEquals(markHomeworkToAliceCommand, markHomeworkToAliceCommand);

        // same assignment and same person name -> true
        MarkCommand markHomeworkToAliceCommandCopy =
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, alice.getName());
        assertEquals(markHomeworkToAliceCommand, markHomeworkToAliceCommandCopy);

        // different types -> false
        assertNotEquals(1, markLabToBobCommand);

        // null -> false
        assertNotEquals(null, markHomeworkToAliceCommand);

        // same assignment but different person name -> false
        assertNotEquals(markLabToAliceCommand, markLabToBobCommand);

        // different assignment but same person name -> false
        assertNotEquals(markHomeworkToAliceCommand, markLabToAliceCommand);
    }

    class ModelStub extends ModelManager {
        @Override
        public MarkCommandTest.PersonStub findPersonByName(PersonName name)
                throws PersonNotFoundException {
            if (name.fullName.equals("Nonexistent")) {
                throw new PersonNotFoundException();
            }

            Phone dummyPhone = new Phone("98765432");
            Email dummyEmail = new Email("dumb@gmail.com");
            TuitionClass dummyTuitionClass = new TuitionClass("W14");
            Set<Assignment> dummyAssignments = new HashSet<>();
            dummyAssignments.add(ASSIGNMENT_HOMEWORK_TO_MARK);
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

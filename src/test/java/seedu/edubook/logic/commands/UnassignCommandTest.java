package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_CARL;
import static seedu.edubook.testutil.TypicalPersons.BENSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
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

public class UnassignCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(null, NAME_TARGET_AMY));
    }

    @Test
    public void constructor_nullPersonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(ASSIGNMENT_HOMEWORK, null));
    }

    @Test
    public void execute_success() throws CommandException {
        ModelStub model = new ModelStub();
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK, NAME_TARGET_BENSON);

        CommandResult result = command.execute(model);

        assertEquals(String.format(NameAssignTarget.MESSAGE_UNASSIGN_SUCCESS,
                        ASSIGNMENT_HOMEWORK.assignmentName, BENSON.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_missingAssignment_throwsAssignmentNotFoundException() {
        ModelStub model = new ModelStub();
        Assignment missingAssignment = new Assignment(new AssignmentName("Missing"));
        UnassignCommand command = new UnassignCommand(missingAssignment, NAME_TARGET_AMY);

        AssignmentNotFoundException e = assertThrows(AssignmentNotFoundException.class, () -> command.execute(model));
        assertEquals(AssignmentNotFoundException.MESSAGE_ASSIGNMENT_NOT_FOUND, e.getMessage());
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        ModelStub model = new ModelStub();
        PersonName unassignee = new PersonName("Nonexistent");
        AssignTarget target = new NameAssignTarget(unassignee);
        UnassignCommand command = new UnassignCommand(ASSIGNMENT_HOMEWORK, target);

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(NameAssignTarget.MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        UnassignCommand unassignHomeworkFromAliceCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, NAME_TARGET_AMY);
        UnassignCommand unassignHomeworkFromBobCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, NAME_TARGET_BENSON);
        UnassignCommand unassignTutorialFromAliceCommand = new UnassignCommand(ASSIGNMENT_TUTORIAL, NAME_TARGET_CARL);

        // same object -> true
        assertEquals(unassignHomeworkFromAliceCommand, unassignHomeworkFromAliceCommand);

        // same assignment and same person name -> true
        UnassignCommand unassignHomeworkFromAliceCommandCopy = new UnassignCommand(ASSIGNMENT_HOMEWORK,
                NAME_TARGET_AMY);
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
        UnassignCommand unassignCommand = new UnassignCommand(ASSIGNMENT_HOMEWORK, NAME_TARGET_AMY);

        String str = unassignCommand.toString();

        assertTrue(str.contains("assignment"));
        assertTrue(str.contains("Homework 2"));
        assertTrue(str.contains("target"));
        assertTrue(str.contains("Amy"));
    }

    class ModelStub extends ModelManager {
        @Override
        public PersonStub findPersonByName(PersonName name) throws PersonNotFoundException {
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
                throw AssignmentNotFoundException.forStudent();
            }
            return this;
        }
    }
}

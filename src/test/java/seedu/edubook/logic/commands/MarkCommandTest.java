package seedu.edubook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.edubook.model.target.NameTarget.MESSAGE_MARK_SUCCESS;
import static seedu.edubook.model.target.NameTarget.MESSAGE_PERSON_NOT_FOUND;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK_TO_MARK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_LAB_TO_MARK;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_AMY;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_BENSON;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_CARL;
import static seedu.edubook.testutil.TypicalNameTargets.NAME_TARGET_NONEXISTENT;
import static seedu.edubook.testutil.TypicalPersons.CARL;

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

public class MarkCommandTest {
    private ModelStub modelStub = new ModelStub();

    @Test
    public void constructor_nullAssignmentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkCommand(null, NAME_TARGET_AMY));
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, null));
    }

    @Test
    public void execute_success() throws CommandException {
        MarkCommand command = new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, NAME_TARGET_CARL);
        CommandResult result = command.execute(modelStub);
        assertEquals(String.format(MESSAGE_MARK_SUCCESS,
                        ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, CARL.getName()),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_nonExistentPerson_throwsCommandException() {
        MarkCommand command = new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, NAME_TARGET_NONEXISTENT);
        CommandException e = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(String.format(MESSAGE_PERSON_NOT_FOUND, "Nonexistent"), e.getMessage());
    }

    @Test
    public void equals() {
        MarkCommand markHomeworkToAmyCommand =
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, NAME_TARGET_AMY);
        MarkCommand markLabToBensonCommand =
                new MarkCommand(ASSIGNMENT_LAB_TO_MARK.assignmentName, NAME_TARGET_BENSON);
        MarkCommand markLabToAmyCommand =
                new MarkCommand(ASSIGNMENT_LAB_TO_MARK.assignmentName, NAME_TARGET_AMY);

        // same object -> true
        assertEquals(markHomeworkToAmyCommand, markHomeworkToAmyCommand);

        // same assignment and same person name -> true
        MarkCommand markHomeworkToAmyCommandCopy =
                new MarkCommand(ASSIGNMENT_HOMEWORK_TO_MARK.assignmentName, NAME_TARGET_AMY);
        assertEquals(markHomeworkToAmyCommand, markHomeworkToAmyCommandCopy);

        // different types -> false
        assertNotEquals(1, markLabToBensonCommand);

        // null -> false
        assertNotEquals(null, markHomeworkToAmyCommand);

        // same assignment but different person name -> false
        assertNotEquals(markLabToAmyCommand, markLabToBensonCommand);

        // different assignment but same person name -> false
        assertNotEquals(markHomeworkToAmyCommand, markLabToAmyCommand);
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

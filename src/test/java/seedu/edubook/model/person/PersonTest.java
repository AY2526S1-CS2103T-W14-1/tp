package seedu.edubook.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalPersons.ALICE;
import static seedu.edubook.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentAlreadyExistsException;
import seedu.edubook.logic.commands.exceptions.AssignmentNotFoundException;
import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.testutil.PersonBuilder;

public class PersonTest {

    private Assignment test;

    @BeforeEach
    public void setUp() {
        test = new Assignment(new AssignmentName("CA1"));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void withAddedAssignment_newAssignment_success() throws AssignmentAlreadyExistsException {
        Person person = new PersonBuilder().build();
        Person updatedPerson = person.withAddedAssignment(test).withAddedAssignment(ASSIGNMENT_HOMEWORK);

        // should contain both assignments
        assertTrue(updatedPerson.getAssignments().contains(test));
        assertTrue(updatedPerson.getAssignments().contains(ASSIGNMENT_HOMEWORK));

        // check for immutability - original person should not be changed.
        assertFalse(person.getAssignments().contains(ASSIGNMENT_HOMEWORK));
    }

    @Test
    public void withAddedAssignment_duplicateAssignment_throwsException() throws AssignmentAlreadyExistsException {
        Person person = new PersonBuilder().build();
        Person updatedPerson = person.withAddedAssignment(test);
        assertThrows(AssignmentAlreadyExistsException.class, () -> updatedPerson.withAddedAssignment(test));
    }

    @Test
    public void withRemovedAssignment_existingAssignment_success() throws CommandException {
        Person person = new PersonBuilder().build().withAddedAssignment(test);
        Person updatedPerson = person.withRemovedAssignment(test);

        // updated person should no longer contain it
        assertFalse(updatedPerson.getAssignments().contains(test));

        // check for immutability - original person should not be changed.
        assertTrue(person.getAssignments().contains(test));
    }

    @Test
    public void withRemovedAssignment_nonExistingAssignment_throwsException() {
        Person person = new PersonBuilder().build();
        Assignment notAdded = new Assignment(new AssignmentName("CA2"));
        assertThrows(AssignmentNotFoundException.class, () -> person.withRemovedAssignment(notAdded));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withClass(VALID_CLASS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        Person editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different class -> returns false
        editedAlice = new PersonBuilder(ALICE).withClass(VALID_CLASS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", class=" + ALICE.getTuitionClass() + ", tags=" + ALICE.getTags()
                + ", assignments=" + ALICE.getAssignments() + ", label=[EMPTY]}";
        assertEquals(expected, ALICE.toString());
    }
}

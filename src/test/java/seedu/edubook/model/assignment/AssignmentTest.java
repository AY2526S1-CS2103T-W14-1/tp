package seedu.edubook.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_TUTORIAL;
import static seedu.edubook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.model.tag.Tag;

public class AssignmentTest {

    private Assignment assignment1;
    private Assignment assignment2;

    @BeforeEach
    public void setUp() {
        assignment1 = new Assignment(new AssignmentName(VALID_ASSIGNMENT_HOMEWORK));
        assignment2 = new Assignment(new AssignmentName(VALID_ASSIGNMENT_TUTORIAL));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Assignment(null));
    }

    @Test
    public void constructor_invalidAssignmentName_throwsIllegalArgumentException() {
        String invalidAssignmentName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidAssignmentName));
    }

    @Test
    public void constructor_validAssignmentName_storesName() {
        AssignmentName name = new AssignmentName(VALID_ASSIGNMENT_HOMEWORK);
        assertEquals(name, assignment1.assignmentName);
    }

    @Test
    public void equals_sameAssignment_returnsTrue() {
        assertEquals(assignment1, assignment1);
        assertEquals(assignment1.hashCode(), assignment1.hashCode());
    }

    @Test
    public void equals_differentAssignmentAndOthers_returnsFalse() {
        // different object -> returns false
        assertNotEquals(assignment1, assignment2);

        // null -> returns false
        assertNotEquals(null, assignment1);

        // different type -> returns false
        assertNotEquals(5, assignment1);
    }

    @Test
    public void toStringMethod() {
        Assignment assignment = new Assignment(new AssignmentName("Homework 1"));
        assertEquals("[Homework 1]", assignment.toString());
    }
}

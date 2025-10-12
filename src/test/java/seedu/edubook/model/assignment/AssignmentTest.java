package seedu.edubook.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_TUTORIAL;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TASK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edubook.model.tag.Tag;

public class AssignmentTest {

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
        AssignmentName name = new AssignmentName("Task 1");
        assertEquals(name, ASSIGNMENT_TASK.assignmentName);
    }

    @Test
    public void equals_sameAssignment_returnsTrue() {
        assertEquals(ASSIGNMENT_TASK, ASSIGNMENT_TASK);
        assertEquals(ASSIGNMENT_TASK.hashCode(), ASSIGNMENT_TASK.hashCode());
    }

    @Test
    public void equals_differentAssignmentAndOthers_returnsFalse() {
        // different object -> returns false
        assertNotEquals(ASSIGNMENT_TASK, ASSIGNMENT_HOMEWORK);

        // null -> returns false
        assertNotEquals(null, ASSIGNMENT_TASK);

        // different type -> returns false
        assertNotEquals(5, ASSIGNMENT_TASK);
    }

    @Test
    public void toStringMethod() {
        assertEquals("[Homework 2]", ASSIGNMENT_HOMEWORK.toString());
    }
}

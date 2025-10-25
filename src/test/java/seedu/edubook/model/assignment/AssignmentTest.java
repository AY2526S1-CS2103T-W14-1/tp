package seedu.edubook.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_HOMEWORK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TASK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL_ONE_TO_MARK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL_ONE_TO_UNMARK;
import static seedu.edubook.testutil.TypicalAssignments.ASSIGNMENT_TUTORIAL_TWO_TO_MARK;
import static seedu.edubook.testutil.TypicalAssignments.UNMARKED_ASSIGNMENT_TEST_TO_UNMARK;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.commands.exceptions.AssignmentMarkedException;
import seedu.edubook.logic.commands.exceptions.AssignmentUnmarkedException;
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
    public void unmarkedAssignment_markedSuccessfully() throws AssignmentMarkedException {
        ASSIGNMENT_TUTORIAL_ONE_TO_MARK.mark();
        assertTrue(ASSIGNMENT_TUTORIAL_ONE_TO_MARK.isDone());
    }

    @Test
    public void markedAssignment_throwsExceptionWhenMarked() throws AssignmentMarkedException {
        ASSIGNMENT_TUTORIAL_TWO_TO_MARK.mark();
        assertThrows(AssignmentMarkedException.class, ASSIGNMENT_TUTORIAL_TWO_TO_MARK::mark);
    }

    @Test
    public void unmarkedAssignment_throwsExceptionWhenUnmarked() {
        assertThrows(AssignmentUnmarkedException.class, UNMARKED_ASSIGNMENT_TEST_TO_UNMARK::unmark);
    }

    @Test
    public void markedAssignment_unmarkedSuccessfully() throws AssignmentUnmarkedException {
        ASSIGNMENT_TUTORIAL_ONE_TO_UNMARK.unmark();
        assertTrue(!ASSIGNMENT_TUTORIAL_ONE_TO_UNMARK.isDone());
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

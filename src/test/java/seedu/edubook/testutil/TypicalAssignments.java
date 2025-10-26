package seedu.edubook.testutil;

import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;


/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {
    public static final Assignment ASSIGNMENT_TUTORIAL = new Assignment(new AssignmentName("Tutorial 2"));
    public static final Assignment ASSIGNMENT_HOMEWORK = new Assignment(new AssignmentName("Homework 2"));
    public static final Assignment ASSIGNMENT_LAB = new Assignment(new AssignmentName("Lab 2"));
    public static final Assignment ASSIGNMENT_TASK = new Assignment(new AssignmentName("Task 1"));

    public static final Assignment ASSIGNMENT_HOMEWORK_TO_MARK = new Assignment(new AssignmentName("Homework 2"));
    public static final Assignment ASSIGNMENT_LAB_TO_MARK = new Assignment(new AssignmentName("Lab 2"));
    public static final Assignment ASSIGNMENT_TUTORIAL_ONE_TO_MARK = new Assignment(new AssignmentName("Tutorial 1"));
    public static final Assignment ASSIGNMENT_TUTORIAL_TWO_TO_MARK = new Assignment(new AssignmentName("Tutorial 2"));

    public static final Assignment ASSIGNMENT_HOMEWORK_TO_UNMARK =
            new Assignment(new AssignmentName("Homework 2"), true);
    public static final Assignment ASSIGNMENT_LAB_TO_UNMARK =
            new Assignment(new AssignmentName("Lab 2"), true);
    public static final Assignment ASSIGNMENT_TUTORIAL_ONE_TO_UNMARK =
            new Assignment(new AssignmentName("Tutorial 1"), true);
    public static final Assignment UNMARKED_ASSIGNMENT_TEST_TO_UNMARK =
            new Assignment(new AssignmentName("Test"), false);
}

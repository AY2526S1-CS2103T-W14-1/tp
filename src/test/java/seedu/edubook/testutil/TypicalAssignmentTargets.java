package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.BENSON;
import static seedu.edubook.testutil.TypicalPersons.JANE;

import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.target.AssignmentTarget;
import seedu.edubook.model.target.Target;

/**
 * A utility class containing a list of AssignmentTarget objects to be used in tests.
 */
public class TypicalAssignmentTargets {
    public static final Target ASSIGNMENT_TARGET_JANE = new AssignmentTarget(
            JANE.getAssignments().iterator().next().assignmentName);
    public static final Target ASSIGNMENT_TARGET_BENSON = new AssignmentTarget(
            BENSON.getAssignments().iterator().next().assignmentName);
    public static final Target ASSIGNMENT_TARGET_NONEXISTENT = new AssignmentTarget(
            new AssignmentName("Nonexistent"));
}

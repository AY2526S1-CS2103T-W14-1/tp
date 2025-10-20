package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;

import seedu.edubook.model.assign.AssignTarget;
import seedu.edubook.model.assign.ClassAssignTarget;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.TuitionClass;

/**
 * A utility class containing a list of ClassAssignTarget objects to be used in tests.
 */
public class TypicalClassTargets {
    public static final AssignTarget CLASS_TARGET_AMY = new ClassAssignTarget(AMY.getTuitionClass());
    public static final AssignTarget CLASS_TARGET_BENSON = new ClassAssignTarget(BENSON.getTuitionClass());
}

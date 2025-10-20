package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;

import seedu.edubook.model.assign.ClassTarget;
import seedu.edubook.model.assign.Target;

/**
 * A utility class containing a list of ClassAssignTarget objects to be used in tests.
 */
public class TypicalClassTargets {
    public static final Target CLASS_TARGET_AMY = new ClassTarget(AMY.getTuitionClass());
    public static final Target CLASS_TARGET_BENSON = new ClassTarget(BENSON.getTuitionClass());
}

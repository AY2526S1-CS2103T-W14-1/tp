package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;
import static seedu.edubook.testutil.TypicalPersons.CARL;

import seedu.edubook.model.assign.AssignTarget;
import seedu.edubook.model.assign.NameAssignTarget;

/**
 * A utility class containing a list of NameAssignTarget objects to be used in tests.
 */
public class TypicalNameTargets {
    public static final AssignTarget NAME_TARGET_AMY = new NameAssignTarget(AMY.getName());
    public static final AssignTarget NAME_TARGET_BENSON = new NameAssignTarget(BENSON.getName());
    public static final AssignTarget NAME_TARGET_CARL = new NameAssignTarget(CARL.getName());
}

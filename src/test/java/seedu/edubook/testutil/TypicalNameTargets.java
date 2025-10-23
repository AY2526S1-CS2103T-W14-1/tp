package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;
import static seedu.edubook.testutil.TypicalPersons.CARL;

import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

/**
 * A utility class containing a list of NameAssignTarget objects to be used in tests.
 */
public class TypicalNameTargets {
    public static final Target NAME_TARGET_AMY = new NameTarget(AMY.getName());
    public static final Target NAME_TARGET_BENSON = new NameTarget(BENSON.getName());
    public static final Target NAME_TARGET_CARL = new NameTarget(CARL.getName());
    public static final Target NAME_TARGET_NONEXISTENT = new NameTarget(new PersonName("Nonexistent"));
}

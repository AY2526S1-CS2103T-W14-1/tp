package seedu.edubook.testutil;

import static seedu.edubook.testutil.TypicalPersons.AMY;
import static seedu.edubook.testutil.TypicalPersons.BENSON;
import static seedu.edubook.testutil.TypicalPersons.CARL;

import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.NameTargetForLabel;
import seedu.edubook.model.target.Target;

/**
 * A utility class containing a list of NameLabelTarget objects to be used in tests.
 */
public class TypicalNameTargetsForLabel {
    public static final Target NAME_TARGET_FOR_LABEL_AMY = new NameTargetForLabel(AMY.getName());
    public static final Target NAME_TARGET_FOR_LABEL_BENSON = new NameTargetForLabel(BENSON.getName());
    public static final Target NAME_TARGET_FOR_LABEL_CARL = new NameTargetForLabel(CARL.getName());
    public static final Target NAME_TARGET_FOR_LABEL_NONEXISTENT =
            new NameTargetForLabel(new PersonName("Nonexistent"));
}

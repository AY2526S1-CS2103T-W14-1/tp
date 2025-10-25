package seedu.edubook.testutil;

import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.NameTargetForLabel;
import seedu.edubook.model.target.Target;

import static seedu.edubook.testutil.TypicalPersons.*;

public class TypicalNameTargetsForLabel {
    public static final Target NAME_TARGET_FOR_LABEL_AMY = new NameTargetForLabel(AMY.getName());
    public static final Target NAME_TARGET_FOR_LABEL_BENSON = new NameTargetForLabel(BENSON.getName());
    public static final Target NAME_TARGET_FOR_LABEL_CARL = new NameTargetForLabel(CARL.getName());
    public static final Target NAME_TARGET_FOR_LABEL_NONEXISTENT = new NameTargetForLabel(new PersonName("Nonexistent"));
}

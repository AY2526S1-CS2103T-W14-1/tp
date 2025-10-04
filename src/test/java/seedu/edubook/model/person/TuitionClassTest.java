package seedu.edubook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TuitionClassTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TuitionClass(null));
    }

    @Test
    public void constructor_invalidClass_throwsIllegalArgumentException() {
        String invalidClass = "";
        assertThrows(IllegalArgumentException.class, () -> new TuitionClass(invalidClass));
    }

    @Test
    public void isValidClass() {
        // null class
        assertThrows(NullPointerException.class, () -> TuitionClass.isValidClass(null));

        // invalid classes
        assertFalse(TuitionClass.isValidClass("")); // empty string
        assertFalse(TuitionClass.isValidClass(" ")); // spaces only

        // valid classes
        assertTrue(TuitionClass.isValidClass("W-14"));
        assertTrue(TuitionClass.isValidClass("-")); // one character
        assertTrue(TuitionClass.isValidClass("Singapore tution centre T-01 9am class")); // long class name
    }

    @Test
    public void isValidLength_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> TuitionClass.isValidLength(null));
    }

    @Test
    public void isValidLength_invalidInput_returnsFalse() {
        String invalidLength = String.join("", java.util.Collections.nCopies(10, "test"));
        assertFalse(TuitionClass.isValidLength(invalidLength));
    }

    @Test
    public void isValidLength_validInput_returnsTrue() {
        assertTrue(TuitionClass.isValidLength("W-14"));
    }

    @Test
    public void equals() {
        TuitionClass tuitionClass = new TuitionClass("Valid Class");

        // same values -> returns true
        assertTrue(tuitionClass.equals(new TuitionClass("Valid Class")));

        // same object -> returns true
        assertTrue(tuitionClass.equals(tuitionClass));

        // null -> returns false
        assertFalse(tuitionClass.equals(null));

        // different types -> returns false
        assertFalse(tuitionClass.equals(5.0f));

        // different values -> returns false
        assertFalse(tuitionClass.equals(new TuitionClass("Other Valid Class")));
    }
}

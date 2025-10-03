package seedu.edubook.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in EduBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidClass(String)}
 */
public class TuitionClass {

    public static final String MESSAGE_CONSTRAINTS = "Classes can take any values, and it should not be blank";

    /*
     * The first character of the class must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code TuitionClass}.
     *
     * @param tuitionClass A valid class.
     */
    public TuitionClass(String tuitionClass) {
        requireNonNull(tuitionClass);
        checkArgument(isValidClass(tuitionClass), MESSAGE_CONSTRAINTS);
        value = tuitionClass;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidClass(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TuitionClass)) {
            return false;
        }

        TuitionClass otherClass = (TuitionClass) other;
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

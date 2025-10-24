package seedu.edubook.model.label;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;
import static seedu.edubook.model.commons.Name.MAX_NAME_LENGTH;

import seedu.edubook.logic.commands.exceptions.AssignmentMarkedException;

/**
 * Represents an assignment in EduBook
 */
public class Label {

    public static final String MESSAGE_CONSTRAINTS = "Label names should only contain alphanumeric characters "
            + "and spaces, and it should not be blank";

    public static final int MAX_LABEL_LENGTH = MAX_NAME_LENGTH;

    public static final String MESSAGE_LENGTH_CONSTRAINTS = "Label names should only contain a maximum of "
            + MAX_LABEL_LENGTH + " characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String labelContent;

    /**
     * Constructs an {@code Label}.
     *
     * @param labelContent String content for Label.
     */
    public Label(String labelContent) {
        requireNonNull(labelContent);
        checkArgument(isValidLabel(labelContent), MESSAGE_CONSTRAINTS);
        this.labelContent = labelContent;
    }

    /**
     * Returns true if a given string is a valid label.
     */
    public static boolean isValidLabel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Label)) {
            return false;
        }

        Label otherLabel = (Label) other;
        return labelContent.equals(otherLabel.labelContent);
    }

    @Override
    public int hashCode() {
        return labelContent.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + labelContent + ']';
    }

}

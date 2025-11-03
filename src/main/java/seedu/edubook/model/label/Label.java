package seedu.edubook.model.label;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;
import static seedu.edubook.model.commons.Name.MAX_NAME_LENGTH;

import seedu.edubook.commons.util.StringUtil;

/**
 * Represents a Label in EduBook
 */
public class Label {

    public static final String MESSAGE_CONSTRAINTS = "Label names should only contain alphanumeric characters "
            + "and spaces, and it should not be blank";

    public static final int MAX_LABEL_LENGTH = MAX_NAME_LENGTH;

    public static final String MESSAGE_LENGTH_CONSTRAINTS = "Label names should only contain a maximum of "
            + MAX_LABEL_LENGTH + " characters";

    public static final Label EMPTY = new Label("EMPTY", true);

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String labelContent;
    public final boolean isEmpty;

    /**
     * Private constructor for empty label.
     *
     * @param labelContent
     * @param isEmpty
     */
    private Label(String labelContent, boolean isEmpty) {
        this.labelContent = labelContent;
        this.isEmpty = isEmpty;
    }

    /**
     * Constructs an {@code Label}.
     *
     * @param labelContent String content for Label.
     */
    public Label(String labelContent) {
        requireNonNull(labelContent);
        checkArgument(isValidLabel(labelContent), MESSAGE_CONSTRAINTS);
        this.labelContent = StringUtil.normalizeSpaces(labelContent);
        this.isEmpty = false;
    }

    /**
     * Returns true if a given string is a valid label.
     */
    public static boolean isValidLabel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *  Returns label content without the brackets.
     */
    public String getLabelContent() {
        return this.labelContent;
    }

    /**
     * Return whether label is empty.
     */
    public boolean isEmpty() {
        return isEmpty;
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
        return labelContent.equals(otherLabel.labelContent) && this.isEmpty == otherLabel.isEmpty();
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

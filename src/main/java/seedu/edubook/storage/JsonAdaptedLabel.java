package seedu.edubook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.model.label.Label;

/**
 * Jackson-friendly version of {@link Label}.
 */
class JsonAdaptedLabel {
    private final String labelContent;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code labelContent}.
     */
    @JsonCreator
    public JsonAdaptedLabel(@JsonProperty("labelContent") String labelContent) {
        this.labelContent = labelContent;
    }

    /**
     * Converts a given {@code Label} into this class for Jackson use.
     */
    public JsonAdaptedLabel(Label source) {
        this.labelContent = source.labelContent;
    }

    public String getLabelContent() {
        return labelContent;
    }

    /**
     * Converts this Jackson-friendly adapted Label object into the model's {@code Label} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Label toModelType() throws IllegalValueException {
        if (!StringUtil.isValidLength(labelContent, Label.MAX_LABEL_LENGTH)) {
            throw new IllegalValueException(Label.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Label.isValidLabel(labelContent)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(new AssignmentName(assignmentName), isDone);
    }

}
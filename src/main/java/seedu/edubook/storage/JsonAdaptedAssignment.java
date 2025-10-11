package seedu.edubook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.commons.Name;
import seedu.edubook.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedAssignment {
    private final String assignmentName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.assignmentName.fullName;
    }

    @JsonValue
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (!Assignment.isValidLength(assignmentName)) {
            throw new IllegalValueException(Assignment.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Assignment.isValidAssignment(assignmentName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(new Name(assignmentName));
    }

}

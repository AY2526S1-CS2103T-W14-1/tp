package seedu.edubook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedAssignment {
    private final String assignmentName;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentName") String assignmentName,
                                 @JsonProperty("isDone") boolean isDone) {
        this.assignmentName = assignmentName;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this.assignmentName = source.assignmentName.fullName;
        this.isDone = source.isDone();
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (!StringUtil.isValidLength(assignmentName, Assignment.MAX_ASSIGNMENT_LENGTH)) {
            throw new IllegalValueException(Assignment.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Assignment.isValidAssignment(assignmentName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(new AssignmentName(assignmentName), isDone);
    }

}

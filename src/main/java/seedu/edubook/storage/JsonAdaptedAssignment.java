package seedu.edubook.storage;

import static seedu.edubook.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.tag.Tag;



/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {
    private final String assignmentName;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentName") String assignmentName,
                                 @JsonProperty("isDone") boolean isDone) {
        this.assignmentName = assignmentName;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
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
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AssignmentName.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(assignmentName, Assignment.MAX_ASSIGNMENT_LENGTH)) {
            throw new IllegalValueException(Assignment.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Assignment.isValidAssignment(assignmentName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(new AssignmentName(assignmentName), isDone);
    }

}

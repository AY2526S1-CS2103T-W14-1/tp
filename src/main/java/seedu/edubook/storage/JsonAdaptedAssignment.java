package seedu.edubook.storage;

import static seedu.edubook.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.logic.commands.MarkCommand;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);

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

    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            logger.info(() -> "File corrupted: Null assignment name");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AssignmentName.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(assignmentName, Assignment.MAX_ASSIGNMENT_LENGTH)) {
            logger.info(() -> "File corrupted: Assignment name too long - " + assignmentName);
            throw new IllegalValueException(Assignment.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Assignment.isValidAssignment(assignmentName)) {
            logger.info(() -> "File corrupted: Invalid assignment name - " + assignmentName);
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(new AssignmentName(assignmentName), isDone);
    }

}

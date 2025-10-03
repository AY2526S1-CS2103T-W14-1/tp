package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;

public class AssignmentName {
    
    public static final String MESSAGE_CONSTRAINTS =
            "Assignment names should only contain alphanumeric characters and spaces, and it should not be blank";
    
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    
    public final String fullAssignmentName;
    
    /**
     * Constructs a {@code Name}.
     *
     * @param assignmentName A valid name.
     */
    public AssignmentName(String assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignmentName(assignmentName), MESSAGE_CONSTRAINTS);
        fullAssignmentName = assignmentName;
    }
    
    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidAssignmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}

package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;

import seedu.edubook.model.person.Name;

public class Assignment {
    
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    
    public final Name assignmentName;
    
    public Assignment(Name assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignmentInput(assignmentName.fullName), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignmentName;
    }
    
    public static boolean isValidAssignmentInput(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        
        // instanceof handles nulls
        if (!(other instanceof Assignment)) {
            return false;
        }
        
        Assignment otherTag = (Assignment) other;
        return assignmentName.equals(otherTag.assignmentName);
    }
    
    @Override
    public int hashCode() {
        return assignmentName.hashCode();
    }
    
    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + assignmentName.fullName + ']';
    }
    
}

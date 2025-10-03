package seedu.edubook.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.AppUtil.checkArgument;

import seedu.edubook.model.person.Name;
import seedu.edubook.model.tag.Tag;

public class Assignment {
    
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    
    public final AssignmentName assignmentName;
    public final Name assignmentAssignee;
    public boolean isAssignmentDone = false;
    
    public Assignment(AssignmentName assignmentName, Name assignmentAssignee) {
        requireNonNull(assignmentName);
        requireNonNull(assignmentAssignee);
        
        this.assignmentName = assignmentName;
        this.assignmentAssignee = assignmentAssignee;
    }
    
    public static boolean isValidAssignmentInput(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    
    public void setAssignmentDone() {
        this.isAssignmentDone = true;
    }
    
    /*
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        
        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }
        
        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }
    
    @Override
    public int hashCode() {
        return tagName.hashCode();
    }
    
    /**
     * Format state as text for viewing.
    public String toString() {
        return '[' + tagName + ']';
    }
    */
    
}

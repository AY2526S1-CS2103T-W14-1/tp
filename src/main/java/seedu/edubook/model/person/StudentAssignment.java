package seedu.edubook.model.person;

import seedu.edubook.model.assignment.Assignment;

public class StudentAssignment {
    
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    
    public final Assignment assignmentTemplate;
    
    public StudentAssignment(Assignment assignment) {
        this.assignmentTemplate = assignment;
    }
}
